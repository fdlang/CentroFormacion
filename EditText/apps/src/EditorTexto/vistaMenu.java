package EditorTexto;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class vistaMenu extends JMenuBar{   
  
        
        public vistaMenu() {
            JMenu menuInicio = new JMenu("Inicio");
            JMenuItem[] itemInicio = new JMenuItem[5];
            String[] arrInicio = {"Nuevo","Nueva ventana","Abrir", "Guardar", "Guardar como"};
             
            for (int i=0;i<itemInicio.length;i++)
                itemInicio[i]= new JMenuItem(arrInicio[i]);                
            for(int i=0;i<itemInicio.length;i++)
                menuInicio.add(itemInicio[i]);
            
            itemInicio[0].addActionListener(new MyListener(1)); 
            itemInicio[1].addActionListener(new MyListener(2)); 
            itemInicio[2].addActionListener(new MyListener(3)); 
            itemInicio[3].addActionListener(new MyListener(4));
            itemInicio[3].addActionListener(new MyListener(5));
            
            JMenu menuEditar = new JMenu("Editar");
            JMenuItem[] itemEditar =new JMenuItem[5];
            String[] arrEditar = {"Deshacer","Cortar","Copiar","Pegar","Buscar"};
            
            for (int i = 0; i < arrEditar.length; i++)
                itemEditar[i] = new JMenuItem(arrEditar[i]);
            for (int i = 0; i < itemEditar.length; i++)
                menuEditar.add(itemEditar[i]);
            
            JMenu menuTema = new JMenu("Temas");
            JMenuItem[] itemTema = new JMenuItem[2];
            String[] arrTema = {"Black", "White"};

            
            for (int i=0;i<arrTema.length;i++) 
                itemTema[i] = new JMenuItem(arrTema[i]);
            for (int i=0;i<itemTema.length;i++)
                menuTema.add(itemTema[i]); 
            
            JMenu menuTalla = new JMenu("Letra");   
            String[] arrTalla = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"}; 
            JComboBox tbox = new JComboBox(arrTalla);
            
            add(menuInicio);
            add(menuEditar);
            add(menuTema);
            add(menuTalla);     
            menuTalla.add(tbox);
        }
}
