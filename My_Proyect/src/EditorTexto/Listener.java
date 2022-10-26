package EditorTexto;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener extends vistaMenu {

    public Listener() {
        
        itemInicio[1].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                VistaPrincipal vp = new VistaPrincipal();               
                vp.frame();                
            }
        });                 
        
        itemInicio[2].addActionListener(new ActionListener() {  //abrir        
            @Override
            public void actionPerformed(ActionEvent e) {
             Controlador.getCtl().abrirChooser();
                
            }
        });
        
        itemInicio[4].addActionListener(new ActionListener() {  //Guardar Como          
            @Override
            public void actionPerformed(ActionEvent e) {
                Controlador.getCtl().guardarChooser();
                
            }
        });
        
        itemTema[0].addActionListener(new ActionListener(){                   
            @Override
            public void actionPerformed(ActionEvent e) {
                textoArea.setBackground(Color.BLACK);             
            }
        });
        
        
        itemTema[1].addActionListener(new ActionListener() {           
            @Override
            public void actionPerformed(ActionEvent e) {
                textoArea.setForeground(Color.WHITE);              
            }
        });
        
        
        itemEditar[0].addActionListener(new ActionListener() {           
            @Override
            public void actionPerformed(ActionEvent e) {
               //METEDO DESAHACER//             
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
                Controlador.getCtl().buscarTexto();               
            }            
        });
                       
    }         
}   
       
          

      
          
            

       

   
    
    
