package EditorTexto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Caret;
import javax.swing.undo.UndoManager;


public class Listener {
    
    private Component contentPane;
    

    public void actionListener() {
               
        
        
        itemInicio[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textoArea.setText(null);              
            }
            
        });
        
        itemInicio[1].addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                
///// hacer nueva ventana       
            }
            
        });
        
        itemInicio[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

       itemInicio[4].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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
       });
       
       itemTema[0].addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            textoArea.setBackground(Color.BLACK);
            textoArea.setForeground(Color.WHITE);
        }           
       });
        
       itemTema[1].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textoArea.setBackground(Color.WHITE);
            textoArea.setForeground(Color.BLACK);           
        }          
       });
       
       itemEditar[0].addActionListener(new ActionListener() { ///// metodo deshacer
        @Override
        public void actionPerformed(ActionEvent e) {                      
            UndoManager undoManager = new UndoManager();
            textoArea.getDocument().addUndoableEditListener(undoManager);
///// falta por hacer            
            
        }
           
       });
       
       itemEditar[1].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textoArea.cut();
           }             
          });
       
       itemEditar[2].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textoArea.copy();         
        }           
       });
       
       itemEditar[3].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textoArea.paste();
            
        }          
       });   
       
       itemEditar[4].addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {            
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
          });
       
         
    }
}
    
    
