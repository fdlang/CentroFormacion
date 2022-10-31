package EditorTexto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class Listener implements ActionListener {
    

    public void actionPerformed(JMenuItem itemInicio) {
            Controlador.getCtl().abrirChooser();
            //Controlador.getCtl().guardarChooser();
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    
}   
       
          

      
          
            

       

   
    
    
