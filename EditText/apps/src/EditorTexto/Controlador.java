package EditorTexto;

import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Caret;

public class Controlador extends VistaPrincipal {     
   static Component contentPane;
 
       
       public static void abrirChooser() {          
           //Creo el objeto JFileChooser
           JFileChooser jcAbrir = new JFileChooser();          
           //Indicamos lo que podemos seleccionar
           jcAbrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
           //Creo el filtro
           FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");            
           //Aplico el filtro
           jcAbrir.setFileFilter(filtro);
           //Abro la ventana, guardo la opcion seleccionada por el usuario
           int seleccion = jcAbrir.showOpenDialog(contentPane);                 
           //Si el usuario, pincha en aceptar
           if(seleccion == JFileChooser.APPROVE_OPTION){
               //Selecciono el fichero
               File fichero = jcAbrir.getSelectedFile();                                                
               try(
                   FileReader fr = new FileReader(fichero)){
                   String cadena = "";
                   int valor = fr.read();
                   while (valor != -1){
                       cadena = cadena + (char)valor;
                       valor = fr.read();
                   }
                   textoArea.setText(cadena);
               } catch (IOException e1) {
                   e1.printStackTrace();
               }                  
           }                                      
       }
        
       public static void guardarChooser() {
           JFileChooser jcGuardar = new JFileChooser();
           
           jcGuardar.setFileSelectionMode(JFileChooser.FILES_ONLY);
           FileNameExtensionFilter filtro = new FileNameExtensionFilter(".TXT", ".txt");
           jcGuardar.setFileFilter(filtro);
           int seleccion = jcGuardar.showSaveDialog(contentPane); 
               if(seleccion == JFileChooser.APPROVE_OPTION) {
                   File fichero = jcGuardar.getSelectedFile();
                   try {
                       FileWriter fw = new FileWriter(fichero);
                       fw.write(textoArea.getText());
                       fw.close();
                   }catch (IOException e1) {
                       e1.printStackTrace();
                   }
               }
       }
       
       public static void buscarTexto() {
           String textoBuscar = JOptionPane.showInputDialog(textoArea, "Introduce texto a buscar");
           Caret selec = textoArea.getCaret();               
           // Dot es la primera posicion seleccionada y Mark la ultima posicion.   
           // Se compara si no hay nada selecionado devuelve -1
           if (selec.getDot() == selec.getMark()) {
               System.out.println("No hay texto seleccionado");
           }else {
               System.out.println("hay texto seleccionado");
               int posicionInical = selec.getDot();
               String textOk = textoArea.getText();
               // indexOf() que además de el texto a buscar admite la posición inicial de búsqueda.
               int posicion = textOk.indexOf(textoBuscar, posicionInical); 
               textoArea.setCaretPosition(posicion);
               textoArea.moveCaretPosition(posicion + textoBuscar.length());
           }
       }
       
}       
    

