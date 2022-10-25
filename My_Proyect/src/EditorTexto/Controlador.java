package EditorTexto;


public class Controlador {
        
   private static Controlador ctl;
   
       private Controlador() {
           if (ctl == null) {
               ctl = new Controlador();
           }     
       }
      
       public static Controlador getCtl(){
           return ctl;
       }
       
       
} 

