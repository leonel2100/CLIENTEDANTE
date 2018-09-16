/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;
import Negocio.Coordinador;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import Datos.XmlRead;
import Datos.Configuracion;
import javax.swing.JButton;
import javax.swing.JComboBox;
import Negocio.NetworkInterfaces;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 *
 * @author leone
 */
public class Panel extends javax.swing.JFrame {

    /**
     * Creates new form Panel
     */
   private PanelAjustes ajustes;
   private PanelMixer mixer;
   private PanelLogs logs;
   private Coordinador C;
   
   private ArrayList<String> canales;
   private ArrayList<Integer> ganancias;
   private String ReportDay;
   private DateFormat df;
   private Date today;
   private XmlRead X;
   private Configuracion Conf;
   private NetworkInterfaces interfaces;
   private ArrayList<String> listaInterfaces;
   private String Red=null;
   
    public Panel() {
       initComponents();
       this.setSize(new Dimension(1280,760));
       C=new Coordinador(this);
       C.SetPanel(this);
       mixer= new PanelMixer(this);
       ajustes = new PanelAjustes(C,this,mixer);
       
       this.setBackground(Color.yellow);
    //   this.Bconnect.setEnabled(false);
       
       //fecha
       df=new SimpleDateFormat("yyyy/dd/MM HH:mm:ss");
       today=Calendar.getInstance().getTime();
         
       X=new XmlRead();
         
         //apagar temporalmente logs (boton)
       this.Bping.setEnabled(false);
    //   this.Bstop.setEnabled(false);
        
        
       //obtener interfaces
       interfaces=new NetworkInterfaces();
       listaInterfaces=interfaces.GetInterfaces();
            networks.addItem("default");
       for(int i=0;i<listaInterfaces.size();i++){
           if(i%2==0){
            networks.addItem(listaInterfaces.get(i));
           }
           
       }
    
       jScrollPane2.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
        public void adjustmentValueChanged(AdjustmentEvent e) {  
            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
        }
    }); 
    
       
       
       
    }
    
    public void setpanel(Networks NT){
      scrollPane1.add(NT);
    }
    
    public void setpanel(PanelMixer mixer){
       
       scrollPane1.add(mixer);
    }
    
    public void setpanel(PanelAjustes ajustes){
        scrollPane1.add(ajustes);
        
    }
    
    public void setpanel(PanelLogs logs){
      scrollPane1.add(logs);
    }
    
    public void setEtiquetaStatus(String estado){
      this.EtiquetaStatus.setText(estado);
    }
    
    public void setEtiquetaCanales(String canales){
        this.Etiquetacanales.setText(canales);
    }
    
    public void setEtiquetaConect(String ping){
        this.EtiquetaConect.setText(ping);
    }
    
    public void SetCanales(ArrayList<String> canales){
      this.canales=canales;
    }
    
    public ArrayList<String> GetCanales(){
        return mixer.GetCanales();
    }
    
    public void SetCGanancias(ArrayList<Integer> ganancias){
      this.ganancias=ganancias;
    }
    
    public ArrayList<Integer> GetGanancias(){
        return mixer.GetGanacias();
    }
    
    public void SetLog(String Texto){
        today=Calendar.getInstance().getTime();
        ReportDay=df.format(today);
   this.jTextArea1.append(ReportDay+"  -  "+Texto+'\n');
       
    }
    
    public void SetMSG(String Texto,boolean status){
        this.MSG.setText(Texto);
       
      
    }
    
    public void TurnOffSend(){
    //  this.Bconnect.setEnabled(false);
    }
    
    public void TurnOnSend(){
    //  this.Bconnect.setEnabled(true);
    }
    
    public void TurnOffStop(){
    //  this.Bstop.setEnabled(false);
    }
    
    public void TurnOnStop(){
     // this.Bstop.setEnabled(true);
    }
            
    public void SetAlarma(int status){
        if(status==0){
            try{
               alarma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/green.png")));
            }
            catch(Exception e){
                System.out.println("error de imagen");
                this.SetLog("error de imagen");
            }
           
        }
        else if(status==1){
        
              try{
               alarma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/yellow.png")));
            }
            catch(Exception e){
                System.out.println("error de imagen");
                this.SetLog("error de imagen");
            }
        }
        
        else if(status==2){
        
              try{
               alarma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/red.png")));
            }
            catch(Exception e){
                System.out.println("error de imagen");
                this.SetLog("error de imagen");
            }
        }
    }
    
   
    
    public String GetNetwork(){
        for(int i=0;i<listaInterfaces.size();i++){
            if(Red.equals(listaInterfaces.get(i))){
                return listaInterfaces.get(i+1);
            }
        }
        
      return null;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    
    
    @SuppressWarnings("unchecked")
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane1 = new java.awt.ScrollPane();
        Bsettings = new javax.swing.JButton();
        Bmixer = new javax.swing.JButton();
        Bping = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        EtiquetaStatus = new javax.swing.JLabel();
        EtiquetaConect = new javax.swing.JLabel();
        Etiquetacanales = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        MSG = new javax.swing.JTextField();
        alarma = new javax.swing.JLabel();
        networks = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 51, 102));
        setForeground(new java.awt.Color(0, 51, 102));
        getContentPane().setLayout(null);

        scrollPane1.setBackground(new java.awt.Color(51, 51, 51));
        getContentPane().add(scrollPane1);
        scrollPane1.setBounds(10, 157, 1090, 415);

        Bsettings.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Bsettings.setText("SETTINGS");
        Bsettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsettingsActionPerformed(evt);
            }
        });
        getContentPane().add(Bsettings);
        Bsettings.setBounds(41, 98, 172, 40);

        Bmixer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Bmixer.setText("MIXER");
        Bmixer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BmixerActionPerformed(evt);
            }
        });
        getContentPane().add(Bmixer);
        Bmixer.setBounds(274, 98, 172, 40);

        Bping.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Bping.setText("LOGS");
        Bping.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BpingActionPerformed(evt);
            }
        });
        getContentPane().add(Bping);
        Bping.setBounds(1152, 97, 118, 42);

        jLabel1.setBackground(new java.awt.Color(51, 0, 204));
        jLabel1.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MONITOR");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(167, 24, 205, 46);

        EtiquetaStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        EtiquetaStatus.setForeground(new java.awt.Color(255, 255, 255));
        EtiquetaStatus.setText("CHECKING SERVICE");
        getContentPane().add(EtiquetaStatus);
        EtiquetaStatus.setBounds(950, 110, 125, 36);

        EtiquetaConect.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        EtiquetaConect.setForeground(new java.awt.Color(255, 255, 255));
        EtiquetaConect.setText("FINDING SERVER");
        getContentPane().add(EtiquetaConect);
        EtiquetaConect.setBounds(490, 100, 116, 36);

        Etiquetacanales.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Etiquetacanales.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(Etiquetacanales);
        Etiquetacanales.setBounds(816, 24, 38, 36);

        jTextArea1.setBackground(new java.awt.Color(51, 51, 51));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(507, 605, 567, 76);

        MSG.setBackground(new java.awt.Color(51, 0, 51));
        MSG.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        MSG.setForeground(new java.awt.Color(204, 204, 204));
        MSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MSGActionPerformed(evt);
            }
        });
        getContentPane().add(MSG);
        MSG.setBounds(620, 110, 275, 33);

        alarma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gray.png"))); // NOI18N
        alarma.setText("jLabel2");
        getContentPane().add(alarma);
        alarma.setBounds(41, 24, 52, 54);

        networks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                networksActionPerformed(evt);
            }
        });
        getContentPane().add(networks);
        networks.setBounds(1110, 189, 130, 20);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SELECT NETWORK");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(1110, 164, 127, 14);

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\leone\\Pictures\\unnamed.jpg")); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 0, 1440, 740);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BsettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsettingsActionPerformed
        // TODO add your handling code here:
        this.setpanel(ajustes);
      //  this.Bconnect.setEnabled(false);
      //  this.Bstop.setEnabled(false);
 
    }//GEN-LAST:event_BsettingsActionPerformed

    private void BmixerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BmixerActionPerformed
        // TODO add your handling code here:
        this.setpanel(mixer);
        //this.Bconnect.setEnabled(rootPaneCheckingEnabled);
        //this.Bstop.setEnabled(true);
        
    }//GEN-LAST:event_BmixerActionPerformed

    private void BpingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BpingActionPerformed
        // TODO add your handling code here:
        this.setpanel(logs);
      //  this.Bconnect.setEnabled(false);
    }//GEN-LAST:event_BpingActionPerformed

    private void networksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_networksActionPerformed
        // TODO add your handling code here:
        Red=networks.getSelectedItem().toString();
    }//GEN-LAST:event_networksActionPerformed

    private void MSGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MSGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MSGActionPerformed

    
    /**
     * @param args the command line arguments
     */
    
    /*
    
    /*
    
    /*
    */
    
    /*
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Panel().setVisible(true);
            }
        });
    }
    */
    
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bmixer;
    private javax.swing.JButton Bping;
    private javax.swing.JButton Bsettings;
    private javax.swing.JLabel EtiquetaConect;
    private javax.swing.JLabel EtiquetaStatus;
    private javax.swing.JLabel Etiquetacanales;
    private javax.swing.JTextField MSG;
    private javax.swing.JLabel alarma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> networks;
    private java.awt.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables
}
