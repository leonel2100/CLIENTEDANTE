/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import Datos.Configuracion;
import Datos.XmlRead;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import Interfaz.Panel;
import java.awt.Color;
import javax.swing.JSlider;
import java.math.*;
import javax.swing.JButton;
import Datos.Logs;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;


/**
 *
 * @author leone
 */
public class ThreadAudio extends Thread{
    private AudioFormat format;
    private SourceDataLine sourceline;
    private Configuracion Conf;
    private XmlRead X;
    private String puerto;
    private String multicast;
    private InetAddress grupo;
    private MulticastSocket socket;
    private byte[] buffer=new byte[8192];
    private byte[] sonido;
    private DatagramPacket PaqueteCliente;
    private Panel P;
    private int Frecuencia;
    private int Muestra;
    private volatile boolean continuar=false;
    private int numerodecanal;
    private JSlider V;
    private JButton B;
    private String Network=null;
    FloatControl volCtrl;
    public ThreadAudio(String Network,String puerto,String multicast,Panel P,int Frecuencia,int muestra,int numerocanal,JSlider V,JButton B){
       
        format=new AudioFormat(Frecuencia, 16, 1, true, true);
        
        DataLine.Info info=new DataLine.Info(SourceDataLine.class,format);
        this.puerto=puerto;
        this.multicast=multicast;
        this.P=P;
        this.Frecuencia=Frecuencia;
        this.Muestra=muestra;
        this.numerodecanal=numerodecanal;
        this.V=V;
        this.numerodecanal=numerocanal;
        this.B=B;
        this.Network=Network;
        if (!AudioSystem.isLineSupported(info)){
                try {
                    System.out.println("Line matching " + info + " is not supported.");
                    P.SetLog("Error de tarjeta de audio ");
                    P.SetMSG("Error de Audio",true);
                    Logs.Write("Error de tarjeta de Audio");
                    throw new LineUnavailableException();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(ThreadAudio.class.getName()).log(Level.SEVERE, null, ex);
                     P.SetLog("Error de tarjeta de audio ");
                     P.SetMSG("Error de Audio",true);
                     Logs.Write("Error de tarjeta de audio");
                    //audio no soportado
                }
                B.setBackground(Color.red);
            }
         
         try {
             sourceline = (SourceDataLine)AudioSystem.getLine(info);
             sourceline.open(format);
             FloatControl control = (FloatControl)sourceline.getControl(FloatControl.Type.MASTER_GAIN);
             //control.setValue(100.0f);
             sourceline.start();
             
         } catch (LineUnavailableException ex) {
            try {
                Logger.getLogger(ThreadAudio.class.getName()).log(Level.SEVERE, null, ex);
                
                //error de audio
                B.setBackground(Color.red);
                Thread.sleep(1000);
                B.setBackground(Color.GRAY);
            } catch (InterruptedException ex1) {
                Logger.getLogger(ThreadAudio.class.getName()).log(Level.SEVERE, null, ex1);
                B.setBackground(Color.GRAY);
            }

         }
                 
    }
    
    //audio ya instanciado
    
    @Override
    public void run(){
        try {
           
            if(Network.equals("default")){
                socket=new MulticastSocket(Integer.parseInt(puerto));
                grupo=InetAddress.getByName(this.multicast);
          
                socket.joinGroup(grupo);
                socket.setSoTimeout(5000);
            }
            else{
                NetworkInterface nif = NetworkInterface.getByName(Network);
                socket = new MulticastSocket(Integer.parseInt(puerto));
                 socket.setSoTimeout(5000);
                socket.joinGroup(new InetSocketAddress(multicast,Integer.parseInt(puerto)), nif);
                
            }
            
         
            
            int canal=selectorAudio(numerodecanal);
            sonido=new byte[Muestra*2];
            
            //cliclo while de recepcion de audio
             PaqueteCliente=new DatagramPacket(buffer, 0, buffer.length,grupo,Integer.parseInt(puerto));
             System.out.println("asignado el puerto  "+Integer.parseInt(puerto));
             Logs.Write("Iniciando audio por canal  "+numerodecanal);
             P.SetLog("Asignado el puerto "+Integer.parseInt(puerto));          
             P.SetMSG("INICIO DE AUDIO", true);
             
             System.out.println(Muestra);
             System.out.println(canal);
             System.out.println(sonido.length);
             byte b1,b2;
             short sh1,sh2,s3;
             int y;
            while(!continuar){
              
                socket.receive(PaqueteCliente);          
               
                for(int x=0;x<(Muestra*2);x+=2){
                    sonido[x]= (PaqueteCliente.getData()[x+canal]);
                    sonido[x+1]= (PaqueteCliente.getData()[x+1+canal]);
                    
                    b1=sonido[x+1];
                    b2=sonido[x];
                    
                    sh1=(short)b1;
                    sh1<<=8;
                    sh2=(short)(sh1 | b2); 
                    sh2=(short) (((sh2>>8)&0xFF)|((sh2&0xFF)<<8));
                    y=(int)sh2;
                    y/=Math.pow(2,8-V.getValue());;
                    
                    s3=(short)y;
                    b2=(byte)s3;
                    s3>>=8;
                    b1=(byte)s3;
                    sonido[x]=b1;
                    sonido[x+1]=b2;
                    //a=(int)sonido[x]/2;
                    //sonido[x]=(byte)a;
                   // if(sonido[x]<0)
                   
                       //sonido[x]-=128;
                     
                }
                              
                sourceline.write(sonido,0,sonido.length);
               
            }
             System.out.println("hilo finalizado ");   
             
             P.SetLog("Audio Finalizado ");
             Logs.Write("Audio por canal  "+numerodecanal+" finalizado ");
             socket.disconnect();
             socket.close();
             P.SetMSG("FIN DE AUDIO", true);
        } catch (IOException ex) {
            Logger.getLogger(ThreadAudio.class.getName()).log(Level.SEVERE, null, ex);
            //error de RED
            Logs.Write("Tiempo de respuesta expirado, error de conexion  "+numerodecanal);
            System.out.println("tiempo de respuesta expirado");
            P.SetLog("Tiempo de respuesta expirado  ");
            P.SetMSG("FIN DE AUDIO, TIMEOUT alcanzado", true);
            P.TurnOnSend();
            P.TurnOffStop();
            B.setBackground(Color.red);

        }
          P.TurnOnSend();
          P.TurnOffStop();
        System.out.println("FINALIZADO");
    }
    
     public void detener(){
      System.out.println("hilo detenido ");
        P.SetLog("Audio detenido  ");
        P.SetMSG("FIN DE AUDIO", true);
        continuar=true;
        sourceline.flush();      
        socket.close();
      //    P.TurnOnSend();
      //    P.TurnOffStop();
 }
     
    public int selectorAudio(int canal){
        int cuenta=0;
        cuenta=canal*Muestra*2;
        return cuenta;
    }
    
    private static float limit(FloatControl control,float level)
    { 
        return Math.min(control.getMaximum(), Math.max(control.getMinimum(), level)); 
    }
    
}
