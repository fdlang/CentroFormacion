package EditorTexto;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VistaPrincipal extends JFrame{
    JTextArea textoArea = new JTextArea();
    
    public void frame() {
        setTitle("Editor de Texto");
        setSize(500,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel principal = new JPanel(); 
        
        principal.setLayout(new BorderLayout());
        principal.add(textoArea, BorderLayout.CENTER);                
        principal.add(new vistaMenu(), BorderLayout.NORTH);    
               
        setContentPane(principal);
        setVisible(true); 
    } 
}