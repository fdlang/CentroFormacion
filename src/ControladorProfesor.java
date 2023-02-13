package CentroFormacion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ControladorProfesor {
	// variable privada de tipo File con la direccion donde se grabara el fichero que contiene los datos de profesores. 
	private static File fP = new File("./datos/profesores.bin"); 
      
	 /**
     * Metodo para selecionar las opciones del menu profesores: alta, baja, modificar y mostar profesores
     * este metodo no tiene entrada de datos por parametro ni devuelve nada.
     */
    public void menu() {
        Scanner sc = new Scanner(System.in);
        Menu m = new Menu(); // se crea objeto de tipo menu, para llamar al menu principal cuando se desee regresar atras.
        boolean exit = false;
        while (!exit) {
            System.out.println("\n_____MENU PROFESORES _____\n");
            System.out.println("1. Alta\n2. Baja\n3. Modificar\n4. Mostrar Profesores\n\n0. Atras\n");
            System.out.print("Elige una opcion: ");
            String opcion = sc.nextLine();        
            
            if(Utils.validarInt(opcion)) {
                if(Integer.parseInt(opcion) < 5) {
                    switch(Integer.parseInt(opcion)) {
                        case 1:
                            System.out.println("Ha seleccionado: ALTA");
                            alta();
                            break;
                        case 2: 
                            System.out.println("Ha seleccionado: BAJA");
                            baja();
                            break;
                        case 3: 
                            System.out.println("Ha seleccionado: MODIFICAR");
                            modificar();
                            break;
                        case 4: 
                            System.out.println("Ha seleccionado: MOSTRAR PROFESORES\n");
                            mostrarProfesores();
                            break;
                        case 0:     
                            exit = true;
                            m.menuPrincipal();
                            break;
                    }      		        	
                }else System.out.println("Opcion no disponible, debe introducir una opcion valida.");
            }else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n"); 
        }
    }
    
    /**
     * Metodo para crear profesores nuevos  que seran escritos en serializado.
     * No se le pasan datos por parametro y no devuelve nada.
     */
    public void alta() {
        Scanner sc = new Scanner(System.in);         
        ArrayList<Profesor> profesores = leerProfesores(fP);
        String dir = "./datos"; // direccion del directorio para alojar fichero serializado.
        int vueltas = 0; // variable para controlar los intentos fallidos al introdicir datos.
        
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream out = null;
         
        System.out.print("¿Ha accedido a dar de alta Profesor, desea continuar? S/N: ");
        String si = Utils.validarConfirmacion(sc.nextLine());
        // si se desea continuar e introdujo s, se procede a pedir los datos necesarios para el alta, se comprueba que todos los datas se han correctos 
        // antes de pasar al siguiente, si no hay errores, con los datos se crea el objeto profesor y se guarda en el fichero.
        if(si.equalsIgnoreCase("s")) {      	
        	System.out.println("\n-- Introduce datos -- \n");
        	System.out.print("Dni: ");
        	String dni = validarDni(sc.nextLine().trim()); 
        	
        	System.out.print("Nombre: ");
        	String nombre = Utils.validarString(sc.nextLine().trim());// se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 15.)         
        	
        	if(nombre.equals("error")) // si validarString devulve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        	    return;                   	
        	System.out.print("Apellidos: ");
        	String apellidos = Utils.validarString(sc.nextLine().trim());// se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 15.)         
        	
        	if(apellidos.equals("error")) // si validarString devulve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        	    return;          
        	System.out.print("Direccion: ");
        	String direccion = sc.nextLine().trim();
        	while(Utils.camposVacios(direccion)) {
        		System.out.println("\nNo puede haber campos vacios\n");
        		System.out.print("Introduce direccion: ");
        		direccion = sc.nextLine().trim();
        		vueltas ++; // se suma 1 en cada vuelta del bucle.
        		if(!Utils.intentos(vueltas)) // si comete 5 errores al introdocir los datos sera retornado al menu.
        		    return;
        	}        
        	System.out.print("Telefono: ");
        	String telefono = Utils.validarTelefono(sc.nextLine().trim());         
        	        	
        	if(telefono.equals("error")) // si validarString devulve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        	    return; 
        	
        	Profesor profesor = new Profesor(nombre,apellidos,direccion,telefono,dni);         
        	profesores.add(profesor);
        	
        	// Condicio para comprobar si el fichero profesor existe, de existir lee el fichero y guarda su contenido en un arraylist, se añade el nuevo profesor y se procede a grabar todo su contenido en el fichero.
            // si el fichero no existe, se creara el directorio donde se alojara el fichero y se procede a guardar el primer profesor.
        	if (fP.exists()) {     	
        		if(!comprobarProfesores(dni, nombre, apellidos)) {
        			try{                   
        				fos = new FileOutputStream(fP);
        				bos = new BufferedOutputStream(fos);
        				out = new ObjectOutputStream(bos);                   
        				
        				for(Profesor a : profesores)
        					out.writeObject(a); // ESCRIBE LOS CONTACTOS EN EL FICHERO.
        				
        				System.out.println("\nContacto introducido con exito");                   
        			}catch (Exception e) {
        				e.printStackTrace();
        			}finally {
        				try{
        					out.close();
        					bos.close();
        					fos.close();                        
        				}catch (IOException e) {
        					e.printStackTrace();
        				}                 
        			}                          		
        		}else System.out.println("\nNo ha sido posible dar de alta \nEl Profesor: " + nombre + ", " + apellidos + ", ya existe.");
        		// SI EL FICHERO NO EXISTE SE ESCRIBE EN ÈL, PRIMER CONTACTO profesor.
        	}else   {             
        		Utils.crearDirectorio(dir); // CREA EL DIRECTORIO DONDE SE GUARDARA EL FICHERO profesor
        		try {
        			fos = new FileOutputStream(fP);
        			bos = new BufferedOutputStream(fos);
        			out = new ObjectOutputStream(bos);   
        			
        			out.writeObject(profesor);                 
        			System.out.println("\nContacto introducido con exito");  
        		}catch  (Exception e) {
        			e.printStackTrace();
        		}finally {
        			try {
        				out.close();
        				bos.close();
        				fos.close();
        			}catch (IOException e) {
        				e.printStackTrace();
        			}
        		}
        	}
        }else System.out.println("Alta profesor, cancelado.");

    }
    
    
    /*
     * METODO QUE NO DEVULVE NADA, PERMITE DAR DE BAJA AL profesor SELECCIONADO.  
     * 
     */
    public void baja(){
        Profesor p;
        Scanner sc = new Scanner(System.in);
        String entrada = ""; // RECOGE DATOS DE LA ENTRADA POR TECLADO.
        boolean encontrado = false; // variable que se utilizara para mensaje de error si no se encontro el profesor.
         
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream out = null;
        
        System.out.print("¿Ha accedido a dar de baja Profesor, desea continuar? S/N: ");
        String si = Utils.validarConfirmacion(sc.nextLine().trim());
        if(si.equalsIgnoreCase("s")) {     	
        	ArrayList<Profesor> tmp = leerProfesores(fP);
        	/* SI EL FICHERO profesor EXISTE y tmp no esta vacio, SE PROCEDE A SU LECTURA Y SE GUARDARAN TODOS LOS DATOS EN TMP PARA PODER COMPARAR LOS DATOS INTRODUCIDOS POR TECLADO
        	MIENTRAS LA VARIBLE IT (CREADA COMO OBJETO ITERATOR) CONTENGA DATOS SERA RECORRIDO Y GUARDADO POSTERIORMENTE EN EL OBJETO DE TIPO profesor
        	CON ITERATOR SE PODRA RECORRER EL ARRAYLIST Y ELIMINAR EL CONTACTO SELECCIONADO.       */
        	if (fP.exists() && !tmp.isEmpty()) {
        		try {               
        			System.out.print("Introduce DNI del profesor a borrar: ");
        			String datos = validarDni(sc.nextLine().trim());                                                
        			
        			ControladorCurso cc = new ControladorCurso();
        			if(!cc.comprobarInscripcionProfesor(datos)) { // si el profesor esta inscrito en un curso no se podra dar de baja y mosytrara un mensaje en pantalla.
        			    Iterator<Profesor> it = tmp.iterator(); // SE CREA UN OBJETO DE LA CLASE ITERATOR DE TIPO profesor QUE CONTENDRA EL ARRAYLIST TMP
                        
                        /* MIENTRAS HAY CONTACTOS LOS RECORRE Y LOS GUARDA EN EL OBJETO DE LA CLASE profesor PARA POSTERIORMENTE SER COMPARADO
                         CON LOS DATOS INTRODUCIDOS POR TECLADO. SI HA ENCONTRADO COINCIDENCIA Y EL USUARIO CONFIRMA SU ELIMINACION EL 
                         CONTACTO SERA BORRADO */
                        while (it.hasNext()) {
                            p = it.next();  // p = OBJETO DE TIPO profesor.                   
                            // SI LOS DATOS A COMPARAR INTRODUDIDOS POR EL USUARIO SON IGUALES SE PROCEDE A CONFIRMAR SU ELIMINACION.
                            if (p.getDni().equalsIgnoreCase(datos)){ 
                                encontrado = true;
                                System.out.println(p);                                                                  
                                System.out.print("\n¿Estas seguro de borrar el profesor? S/N :  ");
                                entrada = Utils.validarConfirmacion(sc.nextLine().trim());
                            
                                // SI HA SELECIONADO "s", EL CONTACTO SERA ELIMINADO, DE LO CONTRARIO SE CANCELA LA OPERACION.
                                if (entrada.equalsIgnoreCase("S")) {
                                    cc = new ControladorCurso();                              
                                    it.remove();                           
                                    
                                    System.out.println("\nEl profesor fue eliminado.");                  
                                }else System.out.println("\nBaja cancelado.");
                            }
                        }                         
                        if(!encontrado)
                            System.out.println("\nEl profesor con dni: "+ datos +", no existe.\n" );
                        
                        fos = new FileOutputStream(fP);
                        bos = new BufferedOutputStream(fos);
                        out = new ObjectOutputStream(bos);
                        
                        // SE RECORRE EL ARRYLIST AL MISMO TIEMPO QUE SE ESCRIBEN LOS DATOS EN EL FICHERO profesor
                        for (Profesor w : tmp)
                            out.writeObject(w);                   
                        out.close(); 
        			}else System.out.println("\nUps.. !! El profesor con dni: " + datos +", esta inscrito en un curso.\nDebe asignar otro profesor distinto al curso antes de borrarlo.\n");     		                                    
        		} catch (Exception e) {
        			e.printStackTrace();
        		}               
        	}else System.out.println("Cancelado, no hay profesores para dar de baja.");     
        }else System.out.println("\nBaja profesor, cancelado.\n");     
    }
    
    
    /**
     * Metodo para poder modificar todos los atributos del objeto profesor, si el fichero no existe no se podra realizar ninguna operacion
     * y saldra un mensaje por pantalla indicado que no se ha encontrado el fichero.
     * Este metodo no pide nada por parametros y no devulve nada.
     */
    public void modificar() {
        Scanner sc = new Scanner(System.in);
        boolean validar = false;  // VARIABLE PARA CONFIRMAR EL MODIFICADO DEL CONTACTO SELECCIONADO.
        boolean coincide = false; // VARIABLE PARA IMPRIMIR MENSAJE SI NO ENCUENTRA COINCIDENCIAS.
        String inMod; // VARIABLE PARA RECOGER DATOS POR TECLADO PARA VALIDAR EL MODIFICADO.  
        int vueltas = 0;  // variable para controlar el numero de intentos que se permite fallar.
        ControladorCurso cc = new ControladorCurso(); // se crea objeto de tipo ControladorCurso para llamar al metodo que comprueba 
        // si el profesor esta inscrito en un curso.
        
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream out = null;
        
        System.out.print("¿Ha accedido a modificar profesor, desea continuar? S/N: ");
        String si = Utils.validarConfirmacion(sc.nextLine().trim());
        if(si.equalsIgnoreCase("s")) {        	
        	ArrayList<Profesor> tmp = leerProfesores(fP); 
        	if (fP.exists() && !tmp.isEmpty()) {          
        		System.out.print("Introduce DNI del profesor que desea modificar sus datos: ");
        		String entradaDatos = validarDni(sc.nextLine().trim());      
        		
        		/* BUCLE FOR PARA RECORRER EL ARRAYlIST, SI EL dni DEL OBJETO ES IGUAL QUE LOS DATOS PEDIDOS POR PANTALLA,
            	SE PEDIRA CONFIRMACION PARA MODIFICAR. */
        		for (Profesor p:tmp) 
        			if (p.getDni().equalsIgnoreCase(entradaDatos)) {
        				coincide = true;
        				System.out.print(p);             
        				do {
        					System.out.print("\n¿Estas seguro de modificar el contacto? S/N :  ");
        					inMod = Utils.validarConfirmacion(sc.nextLine().trim());                       
        					if(inMod.equalsIgnoreCase("S") || inMod.equalsIgnoreCase("n"))                          
        						validar = true;                       
        				}while (!validar);
        				
        				// Si la respuesta es S, se procede a preguntar que campos desea modificar con un menu interactivo para su eleccion.
        				// al salir del menu comprueba que el profesor no exista y se modifican los campos que se han cambiado, si se intenta
        				// modificar el profesor a otro profesor que ya existe se cancela la operacion.
        				if (inMod.equalsIgnoreCase("s")) {            	
        					String nombre = "";
        					String apellidos = "";
        					String direccion = "";
        					String telefono = "";
        					String dni = "";
        					
        					System.out.println("\n¿Que campo desea modificar?\n");
        					validar = false;
        					// bucle para no salir del menu hasta que se presiona el numero 7.
        					do {
        						System.out.println("1. Nombre\t2. Apellidos\t3.Direccion \t4. DNI\t5. Telefono\t6. Guardar y Salir \t7 Cancelar");
        						System.out.print("\nElige una opcion: "); 
        						String optt = sc.nextLine().trim();
        						
        						if(Utils.validarInt(optt)) {// condicion para validar si se a introducido un numero entero.
        							if(Integer.parseInt(optt) <= 7) { // condicion para comprobar que la opcion introducida es la correcta.
        								switch(Integer.parseInt(optt)) {                        		
        								case 1:
        									System.out.print("Introduzca nuevo Nombre: ");
        									nombre = Utils.validarString(sc.nextLine()); // se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 15.)         
        									if(nombre.equals("error")) // si validarString devuelve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        									    return;
        									System.out.println("Nombre modificado correctamente\n");
        									break;
        								case 2:
        									System.out.print("Introduzca nuevos Apellidos: ");
        									apellidos = Utils.validarString(sc.nextLine().trim()); // se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 15.)        
        									
        									if(apellidos.equals("error")) // si validarString devuelve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        									    return;
        									System.out.println("Apellidos modificados correctamente\n");                            	
        									break;
        								case 3: 
        									System.out.print("Introduzca nueva Direccion: ");   
        									direccion = sc.nextLine().trim();
        									vueltas = 0;
        									// mientras existan campos vacios pedira por pantalla la direccion, direccion puede contener cualquier caracter.
        									while(Utils.camposVacios(direccion)) {
        										System.out.println("No puede haber campos vacios: ");
        										System.out.print("Introduce direccion: ");
        										direccion = sc.nextLine().trim();
        										vueltas++;
        										if(!Utils.intentos(vueltas))
        										    return;
        									} 
        									System.out.println("Direccion modificada correctamente\n");                            	
        									break;
        								case 4: 
        								    if(!cc.comprobarInscripcionProfesor(entradaDatos)) {
        								        System.out.print("Introduzca nuevo DNI: ");
        								        dni = validarDni(sc.nextLine().trim()); 
        								        System.out.println("DNI modificado correctamente\n");                            	       								        
        								    }else System.out.println("\nUps.. !! El profesor con DNI "+entradaDatos+" esta inscrito en un curso."
                                                    + "\nNo es posible modificar su DNI. \nElija otro campo a modificar:\n" );
        									break;
        								case 5:
        									System.out.print("Introduzca nuevo Telefono: ");
        									telefono = Utils.validarTelefono(sc.nextLine().trim());
        									
        									if(telefono.equals("error")) // si validarString devuelve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        									    return;
        									System.out.println("Telefono modificado correctamente\n");                            	
        									break;
        								case 6:
        									validar = true;
        									/* Al salir se comprueba que el profesor modificado no exista de ser asi modifica cada campo que se introdujo, de 
        									 * forma contraria se imprime mensaje para informar que el usuario ya existe y no asido posible modificarlo*/
        									if (!comprobarProfesores(dni, nombre, apellidos)) { 
        										if(nombre != "") 
        											p.setNombre(nombre);                         
        										if(apellidos != "") 
        											p.setApellidos(apellidos); 	
        										if(direccion != "") 
        											p.setDireccion(direccion);                                 										   
        										if(telefono != "") 
        											p.setTelefono(telefono);  // se valida que el telefono introducido cumpla con los requisitos(no permite campos vacios, no permite letras ni simbolos, debe contener 9 caracteres numericos)                                                               										
        										if(dni != "") 
        											p.setDni(dni);      										
        									}else System.out.println("\nNo ha sido posible modificar, el Profesor con DNI: " + dni + ", ya existe.\n"); 
        									break; 
        								case 7:
        								    validar = true;
        								    System.out.println("\nCancelado los cambios no fueron guardados.\n"); 
        								    break;
        								}
        							} else System.out.println("\nDebe introducir una opcion valida, pruebe otra vez.");                     	
        						}else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n"); 
        					}while (!validar);                         
        					// SI NO SE CONFIRMA MODIFICAR SE CANCELA LA OPERACION.
        				}else System.out.println("\nModificar profesor, cancelado.");            
        			}           
        		// si no se han encontrado coincidencias anteriormente, imprime mensaje de error.
        		if (coincide == false)
        			System.out.println("\nNo se ha encontrado coincidencias");           
        		try {          
        			fos = new FileOutputStream(fP);
        			bos = new BufferedOutputStream(fos);
        			out = new ObjectOutputStream(bos);
        			
        			// BUCLE FOR PARA RECORRER EL ARRYLIST AL MISMO TIEMPO QUE SE ESCRIBEN LOS DATOS EN EL FICHERO. 
        			for (Profesor w:tmp)
        				out.writeObject(w);                   
        			out.close(); 
        			bos.close();
        			fos.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}                                     
        	}else System.out.println("\nNo se han encontrado profesores, fichero vacio o no existe."); 
        }else System.out.println("\nModificar profesor, cancelado.\n");
    } 
    
    
    
    /**
     * Metodo para buscar profesores en el fichero, se pedira por pantala los apellidos del contacto para su busqueda.
     * Este metodo no devuelve nada y no pide datos por parametro
     */
    public void consulta() {
        Scanner sc = new Scanner(System.in);
        String datos = ""; // RECOGE DATOS DE LA ENTRADA POR TECLADO
        boolean encontrado = false; // SE UTILIZARA PARA MENSAJE DE SALIDA POR COSOLA SI NO SE HAN ENCONTRADO CONTACTOS
        ArrayList<Profesor> tmp = leerProfesores(fP); // guarda en tmp todos los alumnos del fichero alumnos.
        
        if (fP.exists() && !tmp.isEmpty()){                 
            System.out.print("Introduce el apellido del profesor a consultar: ");
            datos = Utils.validarString(sc.nextLine().trim());                
                
            /* BLUCLE FOR PARA RECORER ARRAYLIST Y COMPARAR LOS DATOS INTRODUCIODS POR PANTALLA, 
            SI ENCUENTRA COINCIDENCIA, IMPRIME EL CONTACTO Y CONVERTIMOS LA VARIABLE BOOLEAN A TRUE. */
            for (Profesor p:tmp)  {   
            	if (p.getApellidos().toLowerCase().contains(datos.toLowerCase())){
                    System.out.println(p);
                    encontrado = true;
                }
            }
            if (encontrado == false)
                System.out.println("\nEl Profesor " + datos + " no existe.");                 
        }else System.out.println("Busqueda cancelada, no existen profesores guardados.");
    }
      
    /**
     * Metodo para mostrar por pantalla todos los profesores dados de alta que se encuntra en el fichero.
     * este metodo no devulve nada y no pide datos por parametro.
     */   
    public void mostrarProfesores() {
    	ArrayList<Profesor> profesores = leerProfesores(fP);     
    	if(fP.exists() && !profesores.isEmpty()) {
    		for (Profesor p:profesores)
    			System.out.println(p);
    	}else System.out.println("\nNo existen profesores dados de alta.\n");
    }
    
    /**
     * Metodo para buscar por String dni un profesor y que retorne el objeto profesor. 
     * @param dni
     * @return objeto profesor
     */
    public Profesor buscarProfesorDni(String dni) {
        ArrayList<Profesor> profesor = leerProfesores(fP); 
        Profesor pro = null;
        
        for (Profesor p : profesor) {
        	if (p.getDni().equalsIgnoreCase(dni))
        		pro = p;        	
        }        
        return pro;
    }
    
    
    /**
     * Metodo ArrayList de profesores para recoger todos los datos del fichero de cada objeto.
     * @param fichero
     * @return devuelve un arrayList de profesores
     */
    public ArrayList<Profesor> leerProfesores(File fichero) {
        ArrayList<Profesor> profesores = new ArrayList<Profesor>(); 
        FileInputStream fil = null;  
        BufferedInputStream bis = null;
        ObjectInputStream in = null;

        try {
            fil = new FileInputStream(fichero);
            bis = new BufferedInputStream(fil);
            in = new ObjectInputStream(bis);      
              
            while (true) 
            	profesores.add((Profesor)in.readObject()); // RECOGE LOS profesores DEL FICHERO Y LOS AÑADE AL ARRAYLIST                               
        }catch (Exception e) {
                
        }                   
        return profesores;
    }
    
  
    /**
     * Metodo String para validar que el dni introduico es correcto. 
     * @param variable de tipo string, dni.
     * @return devuelve el dni una vez terminado de validar.
     */
    public String validarDni(String dni){
        Scanner sc = new Scanner(System.in);
        Pattern pat = Pattern.compile("[0-9]{8}[A-Z]"); // SE CONTROLA QUE LOS NUMEROS INTRODUCIDOS SE HAN DEL 0 AL 9 Y MAXIMO 8 CARACTERES 
                                                        // Y LETRAS DE LA A-Z EN MAYUSCULA.
        Matcher mat = pat.matcher(dni);
        int vueltas = 0;
        // MIENTRAS EL dni INTRODUCIDO NO SEA CORRECTO PEDIRA QUE INTRODUZCA LOS DATOS CORRECTAMENTE.
        while(!mat.matches()){
            System.out.println("\nIntroduzca un DNI valido:");
            System.out.println("Sin espacios con la letra en mayuscula, Formato [12345678M]\n");
            System.out.print("Dni: ");
            dni = sc.nextLine().trim();
            vueltas++;
            if (vueltas >= 4) {
                System.out.println("\nCancelado, Debido a sobrepasar el limite de 5 intentos.\n");
                menu();   
            }
            mat = pat.matcher(dni);
        }          
        return dni;
    }
    
    /**
     * Metodo booleano para comprobar si existen profesores iguales.
     * @param se le pasa el nombre y apellidos del profesor.
     * @return devuelve true si encuentra coincidencia y false en caso contrario
     */
    public boolean comprobarProfesores(String dni, String nombre, String apellidos) {
           ArrayList<Profesor> profesores = leerProfesores(fP);
           boolean existe = false;
           
           for (Profesor a:profesores)
               if(a.getDni().equalsIgnoreCase(dni) || a.getNombre().equalsIgnoreCase(nombre) && a.getApellidos().equalsIgnoreCase(apellidos))
                   existe = true;
               else existe = false;          
          return existe; 
     }
    
}
