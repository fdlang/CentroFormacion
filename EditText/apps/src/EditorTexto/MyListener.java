package EditorTexto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.Arrays;

import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class MyListener implements ActionListener {
    
    private int prueba;
    
        public MyListener(int a){
            prueba = a;
        }
    
        public void actionPerformed(JMenuItem itemInicio) {
            
            
        }
    
        
    
        @Override
        public void actionPerformed(ActionEvent e) { 
         
            switch (prueba) {             
                case 1: 
                    
                    break;
                case 2: 
                    
                    break;
                case 3:
                    Controlador.abrirChooser();
                    break;
                case 4:
                    
                    break;
                case 5:
                    Controlador.guardarChooser();
                    break;
            }
            
            
            
        }
    
    



    
    
}   
       
          

      
          
            

       

   
    
    
