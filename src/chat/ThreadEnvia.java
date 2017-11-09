package chat;

import cipher.RailFence;
import cipher.TwistedPathCipher;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
        
public class ThreadEnvia implements Runnable {
    private final PrincipalChat main; 
    private ObjectOutputStream salida;
    private String mensaje;
    private Socket conexion; 
   
    public ThreadEnvia(Socket conexion, final PrincipalChat main){
        this.conexion = conexion;
        this.main = main;
        
        //Evento que ocurre al escribir en el campo de texto
        main.campoTexto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mensaje = event.getActionCommand();
                enviarDatos(mensaje); //se envia el mensaje
                main.campoTexto.setText(""); //borra el texto del enterfield
            } //Fin metodo actionPerformed
        } 
        );//Fin llamada a addActionListener
    } 
    
   //enviar objeto a cliente 
   private void enviarDatos(String mensaje){
      try {
            
          mensaje = "Cliente>>> " + mensaje;
          String original = mensaje;
          
          int metodo = main.metodoCifrado;
          
          if(metodo == 0){
              RailFence r = new RailFence();
              int key = 3;
              
              for(int i = mensaje.length();i%key!=0;i++){
                  mensaje += " ";
              }
              mensaje = r.cifrado(mensaje, key);
          }else if(metodo == 1){
              TwistedPathCipher tps = new TwistedPathCipher();
              mensaje = new String(tps.encrypt(mensaje.toCharArray()));
          }
         salida.writeObject(mensaje);
         salida.flush(); //flush salida a cliente
         main.mostrarMensaje(original);
      } //Fin try
      catch (IOException ioException){ 
         main.mostrarMensaje("Error escribiendo Mensaje");
      } //Fin catch  
      
   } //Fin metodo enviarDatos

   //manipula areaPantalla en el hilo despachador de eventos
    public void mostrarMensaje(String mensaje) {
        main.areaTexto.append(mensaje);
    } 
   
    public void run() {
         try {
            salida = new ObjectOutputStream(conexion.getOutputStream());
            salida.flush(); 
        } catch (SocketException ex) {
        } catch (IOException ioException) {
          ioException.printStackTrace();
        } catch (NullPointerException ex) {
        }
    }   
   
} 
