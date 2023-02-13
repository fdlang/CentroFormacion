package CentroFormacion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ControladorAlumno {
    // variable privada estatica de tipo File con la direccion donde se grabara el fichero que contiene los datos de alumnos. 
    private static File fA = new File("./datos/alumnos.bin"); 
   
    
    /**
     * Metodo para selecionar las opciones del menu alumnos: alta, baja, modificar y mostar alumnos
     * este metodo no tiene entrada de datos por parametro ni devuelve nada.
     */
    public void menu() {
        Scanner sc = new Scanner(System.in);  
        Menu m = new Menu(); // se crea objeto de tipo menu, para llamar al menu principal cuando se desee regresar atras.
        boolean exit = false;
        while (!exit) {
        System.out.println("\n_____ MENU ALUMNOS _____\n");
        System.out.println("1. Alta\n2. Baja\n3. Modificar\n4. Mostrar Alumnos\n\n0. Atras\n");
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
                            System.out.println("Ha seleccionado: MOSTRAR ALUMNOS\n");
                            mostrarAlumnos();                    
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
     * Metodo para crear alumnos nuevos  que seran escritos en serializado.
     * No se le pasan datos por parametro y no devuelve nada.
     */
    public void alta() {
        Scanner sc = new Scanner(System.in);         
        String dir = "./datos"; // direccion del directorio para alojar fichero serializado.
        int vueltas = 0; // variable para controlar los intentos fallidos al introdicir datos.
        
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream out = null;
        
        System.out.print("¿Ha accedido a dar de alta Alumno, desea continuar? S/N: ");
        String si = Utils.validarConfirmacion(sc.nextLine().trim());
        // si se desea continuar e introdujo s, se procede a pedir los datos necesarios para el alta, se comprueba que todos los datas se han correctos 
        // antes de pasar al siguiente, si no hay errores, con los datos se crea el objeto alumno y se guarda en el fichero.
        if(si.equalsIgnoreCase("s")) {        	
        	System.out.println("\n-- Introduce datos -- \n");
        	System.out.print("N. Expediente: ");       
        	String ex = validarExp(sc.nextLine().trim());  // se valida que el expediente introducido cumpla con los requisitos(5 caracteres numericos, sin espacios, sin campos vacios)        
        	// mientras el expedinte introducido exista, pedira por pantalla que introduzca un expedinte nuevo y se validara que el nuevo expedinte se a introducido correctamente.
        	while(comprobarExpediente(ex)) {
        		System.out.println("\nEl N. Expediente " + ex + ", ya existe.");
        		System.out.print("Introduzca otro N. Expediente: ");
        		ex = validarExp(sc.nextLine().trim());
        	}        
        	System.out.print("Nombre: ");
        	String nombre = Utils.validarString(sc.nextLine().trim()); // se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 15.)         
        	
        	if(nombre.equals("error")) // si validarString devuelve "error" es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        	    return;
 	
        	System.out.print("Apellidos: ");
        	String apellidos = Utils.validarString(sc.nextLine().trim()); // se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 15.)         
        	
        	if(apellidos.equals("error"))  // si validarString devulve "error" es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        	    return;
        	
        	System.out.print("Direccion: ");
        	String direccion = sc.nextLine().trim();
        	// mientras existan campos vacios pedira por pantalla la direccion, direccion puede contener cualquier caracter.
        	while(Utils.camposVacios(direccion)) {
        	    vueltas++; // se suma 1 en cada vuelta del bucle.
        	    if (!Utils.intentos(vueltas)) // si comete 5 errores al introdocir los datos sera retornado al menu.
        	        return;
        		System.out.println("\nNo puede haber campos vacios");
        		System.out.print("Introduce direccion: ");
        		direccion = sc.nextLine().trim();

        	} 
        	System.out.print("Telefono: ");
        	String telefono = Utils.validarTelefono(sc.nextLine().trim()); // se valida que el telefono introducido cumpla con los requisitos(no permite campos vacios, no permite letras ni simbolos, debe contener 9 caracteres numericos)       
        	
        	if(telefono.equals("error")) // si validarString devuelve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        	    return;           
        	
        	System.out.print("Fecha de nacimiento formato [ DD/MM/YYYY ]: "); // se indica el formato obligatoria para introducir la fecha.
        	String fch = sc.nextLine().trim();  
        	vueltas = 0;
        	// mientras la fecha no se introduzca correctamente el validador devuelve false y se deben introducir los datos de nuevo para volver a comprobar.
        	while(validarFecha(fch) != true | anioValido(fch) != true) { // mientras no se intruzca la fecha con el formato correcto y el año se comprada entre el 1960 a 2090. pedira que se introzca correctamnte la fecha.
        	    vueltas++; // se suma 1 en cada vuelta del bucle.
        	    if (!Utils.intentos(vueltas))// si comete 5 errores al introdocir los datos sera retornado al menu.
        	        return; 
        		System.out.println("\nError al introducir la fecha de nacimiento");  
        		System.out.print("Introduzca el formato correcto: "); 
        		fch = sc.nextLine().trim();
        		
        	}          
        	Alumno alumno = new Alumno(ex,nombre,apellidos,direccion,telefono,fch); // se crea el objeto alumno con los datos pedidos por pantalla.       
        	
        	// Condicion para comprobar si el fichero alumno existe, de existir lee el fichero y guarda su contenido en un arraylist, se añade el nuevo alumno y se procede a grabar todo su contenido en el fichero.
        	// si el fichero no existe, se creara el directorio donde se alojara el fichero y se procede a guardar el primer alumno.
        	if (fA.exists()) {     	
        		ArrayList<Alumno> alumnos = leerAlumnos(fA);
        		alumnos.add(alumno);
        		
        		if(!comprobarAlumnos(nombre,apellidos)) { // condicion para comprobar que el alumno no exista de ser asi se graba en el fichero, por lo contrario si ya existe se cancela la operacion.
        			try{                   
        				fos = new FileOutputStream(fA);
        				bos = new BufferedOutputStream(fos);
        				out = new ObjectOutputStream(bos);                   
        				// recorre el array de alumnos y escribe el el fichero uno por uno.
        				for(Alumno a : alumnos)
        					out.writeObject(a);        			
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
        		}else System.out.println("\nNo ha sido posible dar de alta.\nEl Alumno: " + nombre + ", " + apellidos + ", ya existe.");
        		
        	}else { // SI EL FICHERO NO EXISTE SE ESCRIBE EN ÈL, PRIMER CONTACTO ALUMNO.            
        		Utils.crearDirectorio(dir); // crea el directorio si no existe, donde se guardar el fichero alumno.
        		try {
        			fos = new FileOutputStream(fA);
        			bos = new BufferedOutputStream(fos);
        			out = new ObjectOutputStream(bos);   
        			
        			out.writeObject(alumno);                 
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
        }else System.out.println("\nAlta alumno, cancelado.\n");

    }
  
    /**
     * Metodo para dar de baja alumno, se comprueba si existe el fichero, se comprueba si existe el alumno, se pide confirmacion
     * para borrarlo.
     * No hay entrada por parametros y no devulve nada.
     */
    public void baja(){
        Alumno a;
        Scanner sc = new Scanner(System.in);
        String entrada = ""; // recoge datos pedidos por teclado.
        boolean encontrado = false; // variable que se utilizara para mensaje de error si no se encontro el alummno.
         
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream out = null;
        
        System.out.print("¿Ha accedido a borrar Alumno, desea continuar? S/N: ");
        String si = Utils.validarConfirmacion(sc.nextLine());
        if(si.equalsIgnoreCase("s")) {          	
        	ArrayList<Alumno> tmp = leerAlumnos(fA); // se hace la lectura del fichero alumnos y se guardan todos los alumnos en el arraylist. 
        	
        	/* Si el fichero alumno existe y tmp no esta vacio, se guarda en arraylist tmp todos los objetos de tipo alumno, para poder comparar los datos introducidos por teclado
        	mientras la varibale IT(creada como objeto iterator) contenga datos sera recorrido y guardado posteriormente en el objeto de tipo alumno(A)
        	con iterator se podra recorrer el arraylist y eliminar el contacto selecionado.*/
        	if (fA.exists() && !tmp.isEmpty()) {
        		try {               
        			System.out.print("Introduce N. Expediente del alumno a borrar: ");
        			String datos = validarExp(sc.nextLine().trim());                                                                             
        			Iterator<Alumno> it = tmp.iterator(); // se crea un objeto de la clase iterator de tipo alumno que contendrael arraylist tmp
        			/* Mientras hay contactos los recorre y los guarda en el objeto de la clase alumno para posteriormente ser comparado
                     con los datos introducidos por teclado. Si ha encontrado coincidencia y el usuario confirma su eliminacion el contacto sera borrado */
        			while (it.hasNext()) {
        				a = it.next();   
        				// Si los datos a comparar introducidos por el usuario son iguales se procede a confirmar su eliminacion.
        				if (a.getExpediente().equalsIgnoreCase(datos)) {
        				    encontrado = true;
        					System.out.println(a);                                           
        					
        					System.out.print("\n¿Estas seguro de borrar el alumno? S/N :  ");
        					entrada = Utils.validarConfirmacion(sc.nextLine().trim());
        					
        					// si se confirma la eliminacion, se procede a borrar el alumno y actualizar los cursos donde este alumno estuviera matriculado
        					// para eliminarlo de todos los cursos.
	        				if (entrada.equalsIgnoreCase("s")) {
	        				    ControladorCurso cc = new ControladorCurso();	        				    
	        					it.remove();                            
	        					cc.actualizarCurso(a); // una vez borrado el alumno, se actualizan los cursos donde este alumno estuviera inscrito y sera borrado de todos los curos.
	        					System.out.println("\nEl alumno fue eliminado.");                  
	        				}else System.out.println("\nBaja cancelado.");
        				}
        			}
        			if(!encontrado)
        			    System.out.println("\nEl alumno con expediente: "+ datos +", no existe.\n" );
        			
        			fos = new FileOutputStream(fA);
        			bos = new BufferedOutputStream(fos);
        			out = new ObjectOutputStream(bos);
        			
        			// Se recorre el arraylist y se escriben uno a uno los objeto de tipo alumno en el fichero.
        			for (Alumno w : tmp)
        				out.writeObject(w);                   
        			out.close();                                     
        		} catch (Exception e) {
        			e.printStackTrace();
        		}               
        	}else System.out.println("Cancelado, no hay alumnos para dar de baja.");     
        }else System.out.println("\nBaja alumno, cancelado.\n");
     
    }
   
    /**
     * Metodo para poder modificar todos los atributos del objeto alumno, si el fichero no existe no se podra realizar ninguna operacion
     * y saldra un mensaje por pantalla indicado que no se ha encontrado el fichero.
     * Este metodo no pide nada por parametros y no devulve nada.
     */
    public void modificar() {
        Scanner sc = new Scanner(System.in);
        boolean validar = false;  // Variable para confirmar el modificado del contacto selecioando.
        boolean coincide = false; // Variable para imprimir mensaje si no encuentra conincidencias.
        String inMod; // Variable para recoger datos por teclado para validar el modificado.  
        int vueltas = 1; // variable para controlar el numero de intentos que se permite fallar.
        ControladorCurso cc = new ControladorCurso(); // se crea objeto de tipo ControladorCurso para llamar al metodo que comprueba 
                                                      // si el alumno esta inscrito en un curso.
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream out = null;
        
        System.out.print("¿Ha accedido a modificar Alumno, desea continuar? S/N: ");
        String si = Utils.validarConfirmacion(sc.nextLine().trim());
        if(si.equalsIgnoreCase("s")) {      	
        	ArrayList<Alumno> tmp = leerAlumnos(fA); 
        	if (fA.exists() && !tmp.isEmpty()) {          
        		System.out.print("Introduce N. Expediente del contacto alumno que desea modificar: ");
        		String entradaDatos = validarExp(sc.nextLine().trim());
        		
        		/* Bucle para recorrer el arraylist, si el expedinte del objeto alumno es igual que los datos pedidos porpantalla,
                se pedira confirmacion para modificar. */
        		for (Alumno a:tmp) 
        			if (a.getExpediente().contentEquals(entradaDatos)) {
        				coincide = true;
        				System.out.print(a);             
        				
        				System.out.print("\n¿Estas seguro de modificar el alumno? S/N :  ");
        				inMod = Utils.validarConfirmacion(sc.nextLine().trim());                       
        				
        				// Si la respuesta es S, se procede a preguntar que campos desea modificar con un menu interactivo para su eleccion.
        				// al salir del menu comprueba que el alumno no exista y se modifican los campos que se han cambiado, si se intenta
        				// modificar el alumno a otro alumno que ya existe se cancela la operacion.
        				if (inMod.equalsIgnoreCase("s")) {                                                      
        					String exp = "";
        					String nombre = "";
        					String apellidos = "";
        					String direccion = "";
        					String telefono = "";
        					String fch = "";
        					
        					System.out.println("\n¿Que campo desea modificar?\n");
        					validar = false;
        					// bucle para no salir del menu hasta que se presiona el numero 7.
        					do {
        						System.out.println("1. N. Expediente\t2. Nombre\t3. Apellidos\t4.Direccion \t5. Telefono\t6. Fecha Nacimiento\t7. Salir y Guardar\t8. Cancelar");
        						System.out.print("\nElige una opcion: "); 
        						String optt = sc.nextLine().trim();
        						
                                if(!Utils.intentos(vueltas))  // si se introdujo mal el campo 5 veces sera expulado del menu modificar.
                                    break;                                
        						                              
        						if(Utils.validarInt(optt)) {// condicion para validar si se a introducido un numero entero.
        							if(Integer.parseInt(optt) <= 8) { // condicion para comprobar que la opcion introducida es la correcta.
        								switch(Integer.parseInt(optt)) {
        								case 1: 
        								    // se comprueba si el alumno esta inscrito en un curso, de ser asi no permitira la modificacion del expediente
        								    // y mostrara un mensaje informativo por consola.
        								    if(!cc.comprobarInscripcionAlumno(entradaDatos)) {
        								        System.out.print("Introduzca nuevo N. Expediente: ");
        								        exp = (validarExp(sc.nextLine().trim())); // se valida que el expediente introducido cumpla con los requisitos(5 caracteres numericos, sin espacios, sin campos vacios)        
        								        // mientras el expedinte introducido exista, pedira por pantalla que introduzca un expedinte nuevo y se validara que el nuevo expedinte se a introducido correctamente.
        								        while(comprobarExpediente(exp)) {
        								            System.out.println("\nEl N. Expediente " + exp + ", ya existe.");
        								            System.out.print("Introduzca otro N. Expediente: ");
        								            exp = validarExp(sc.nextLine().trim());
        								        }
        								        System.out.println("Expediente modificado correctamente.\n");        
        								    }else System.out.println("\nUps.. !! El alumno con expediente "+entradaDatos+" esta inscrito en un curso."
        								            + "\nNo es posible modificar su expediente. \nElija otro campo a modificar:\n" );
        								        
        									break;
        								case 2:
        									System.out.print("Introduzca nuevo Nombre: ");
        									nombre = Utils.validarString(sc.nextLine().trim()); // se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 15.)         
        									
        									if(nombre.equals("error")) // si validarString devuelve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        									    return;
        									System.out.println("Nombre modificado correctamente.\n"); 
        									break;
        								case 3:
        									System.out.print("Introduzca nuevos Apellidos: ");
        									apellidos = Utils.validarString(sc.nextLine().trim()); // se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 15.)        
        									
        									if(apellidos.equals("error")) // si validarString devuelve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        									    return;
        									System.out.println("Apellidos modificados correctamente.\n"); 
        									break;
        								case 4: 
        								    vueltas = 0;
        									System.out.print("Introduzca nueva Direccion: ");   
        									direccion = sc.nextLine().trim();
        									// mientras existan campos vacios pedira por pantalla la direccion, direccion puede contener cualquier caracter.
        								    while(Utils.camposVacios(direccion)) {
        								       System.out.println("vueltas: " + vueltas);
        								        vueltas++; // se suma 1 en cada vuelta del bucle.
        								        if (!Utils.intentos(vueltas)) // si comete 5 errores al introdocir los datos sera retornado al menu.
        								            return;
        						                System.out.println("\nNo puede haber campos vacios\n");
        						                System.out.print("Introduce direccion: ");
        						                direccion = sc.nextLine().trim();
        						            } 
        								    System.out.println("Direccion modificada correctamente.\n");
        									break;
        								case 5: 
        									System.out.print("Introduzca nuevo Telefono: ");
        									telefono = Utils.validarTelefono(sc.nextLine().trim());
        									
        									if(telefono.equals("error")) // si validarString devuelve error es por que se cometiron 5 intentos fallidos y sera retornonado al menu.
        									    return;
        									System.out.println("Telefono modificado correctamente.\n");  
        									break;
        								case 6:
        									System.out.print("Introduzca nueva fecha de nacimiento formato [ DD/MM/YYYY ]: ");
        									fch = sc.nextLine().trim(); 
        									vueltas = 0;
        									// BUCLE QUE LLAMARA AL METODO validarFecha PASANDOLE POR PARAMETRO LA FECHA INTRODUCIDA POR EL USUARIO PARA VALIDAR QUE SE INTRODUCE CORRECTAMENTE.
        								    while(validarFecha(fch) != true | anioValido(fch) != true) { // mientras no se intruzca la fecha con el formato correcto y el año se comprada entre el 1960 a 2090. pedira que se introzca correctamnte la fecha.
        						                vueltas++; // se suma 1 en cada vuelta del bucle.
        						                if (!Utils.intentos(vueltas))// si comete 5 errores al introdocir los datos sera retornado al menu.
        						                    return; 
        						                System.out.println("\nError al introducir la fecha de nacimiento");  
        						                System.out.print("Introduzca el formato correcto: "); 
        						                fch = sc.nextLine().trim();
        						                
        						            }                            
        									System.out.println("Fecha nacimiento modificada correctamente.\n");
        									break;
        								case 7:
        									validar = true;
        									/* Al salir se comprueba que el alumno modificado no exista de ser asi modifica cada campo que se introdujo, de 
        									 * forma contraria se imprime mensaje para informar que el usuario ya existe y no asido posible modificarlo*/
        									if (!comprobarAlumnos(nombre, apellidos)) { 
        										if(exp != "") 
        											a.setExpediente(exp);        											
        										if(nombre != "") 
        											a.setNombre(nombre);                               											                       		
        										if(apellidos != "") 
        											a.setApellidos(apellidos); 	
        										if(direccion != "") 
        											a.setDireccion(direccion);                             	
        										if(telefono != "") 
        											a.setTelefono(telefono);  // se valida que el telefono introducido cumpla con los requisitos(no permite campos vacios, no permite letras ni simbolos, debe contener 9 caracteres numericos)                                                        
        										if(fch != "") 
        											a.setFecha(fch);  	
        									}else System.out.println("No ha sido posible modificar, el Alumno con N. Expediente: " + exp + ", ya existe."); 
        									break;  
        								case 8:
        								    validar = true;
                                            System.out.println("\nCancelado los cambios no fueron guardados.\n"); 
                                            break;
        								}
        							} else System.out.println("\nDebe introducir una opcion valida, pruebe otra vez.");                     	
        						}else vueltas++; // cada vez que se introuzca el campo mal la varibale vultas suma 1.		
        					}while (!validar);                                                                 
        				}else System.out.println("\nModificar alumno, cancelado."); // SI NO SE CONFIRMA MODIFICAR SE CANCELA LA OPERACION.           
        			}            
        		// si no se han encontrado coincidencias anteriormente, imprime mensaje de error.
        		if (coincide == false)
        			System.out.println("\nNo se ha encontrado coincidencias");            
        		try {          
        			fos = new FileOutputStream(fA);
        			bos = new BufferedOutputStream(fos);
        			out = new ObjectOutputStream(bos);
        			
        			// BUCLE FOR PARA RECORRER EL ARRYLIST AL MISMO TIEMPO QUE SE ESCRIBEN LOS DATOS EN EL FICHERO. 
        			for (Alumno w:tmp)
        				out.writeObject(w);                   
        			out.close();  
        			bos.close();
        			fos.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}                                     
        	}else System.out.println("\nNo se han encontrado alumnos, fichero vacio o no existe.");  
        }else System.out.println("\nModificar alumno, cancelado.\n");
    }     
   
    /**
     * Metodo para buscar alumnos en el fichero, se pedira por pantala los apellidos del contacto para su busqueda.
     * Este metodo no devuelve nada y no pide datos por parametro
     */
    public void consulta() {
        Scanner sc = new Scanner(System.in);
        String datos = ""; // recoge datos de la entrada por teclado, seran los apellidos del alumno a buscar.
        boolean encontrado = false; // SE UTILIZARA PARA MENSAJE DE SALIDA POR COSOLA SI NO SE HAN ENCONTRADO CONTACTOS
        ArrayList<Alumno> tmp = leerAlumnos(fA); // guarda en tmp todos los objetos de tipo alumno del fichero alumnos.
        
        if (fA.exists() && !tmp.isEmpty()){                 
            System.out.print("Introduce el apellido del Alumno a consultar: ");
            datos = Utils.validarString(sc.nextLine().trim());               
                
            /* Bucle para recorrer arraylist y comparar los datos introducidos por pantalla,
               si encuentra coincidencia imprime el contacto y se le dar el valor true a la variable boolean encontrado. */
            for (Alumno a:tmp)  {   
                if (a.getApellidos().toLowerCase().contains(datos.toLowerCase())){ //se convierten todos los campos a minusculas para su comparacion.
                    System.out.println(a);
                    encontrado = true;
                }
            }
            if (encontrado == false)
                System.out.println("\nEl Alumno " + datos + " no existe.");          
        }else System.out.println("Busqueda cancelada, no existen alumnos guardados.");
    }   
   
    /**
     * Metodo para mostrar por pantalla todos los alumnos dados de alta que se encuntra en el fichero.
     * este metodo no devulve nada y no pide datos por parametro.
     */
    public void mostrarAlumnos() {
    	ArrayList<Alumno> alumnos = leerAlumnos(fA);    
    	if(fA.exists() && !alumnos.isEmpty()) {
    		for (Alumno a:alumnos)
    			System.out.println(a);    		
    	}else System.out.println("\nNo existen alumnos dados de alta");
    }     
 
    /**
     * Metodo ArrayList de objeto alumno para leer en el fichero y añadir todos los objetos de tipo alumno al arraylist 
     * @param pide por parametro el fichero donde se quiere recoger los datos
     * @return devuelve un arraylist de objeto alumno.
     */
    public ArrayList<Alumno> leerAlumnos(File fichero) {
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>(); 
        FileInputStream fil = null;  
        BufferedInputStream bis = null;
        ObjectInputStream in = null;
        
        if(fichero.exists()) {
        	try {
        		fil = new FileInputStream(fichero);
        		bis = new BufferedInputStream(fil);
        		in = new ObjectInputStream(bis);      
        		
        		while (true) 
        			alumnos.add((Alumno)in.readObject()); // RECOGE LOS ALUMNOS DEL FICHERO Y LOS AÑADE AL ARRAYLIST                               
        	}catch (Exception e) {                
        	}finally {
        		try {
        			in.close();
        			bis.close();
        			fil.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}       	
        }
        return alumnos;
    }   
    
    /**
     * Metodo para buscar alumno por expediente
     * @param String expediente
     * @return devuelve Objeto alumno
     */
    public Alumno buscarAlumnoEX(String ex) {
        ArrayList<Alumno> alumnos = leerAlumnos(fA); 
        Alumno alumno = null;        
        for (Alumno a: alumnos) {
        	if (a.getExpediente().equalsIgnoreCase(ex))
        		alumno = a;        	
        }        
        return alumno;
    }
        
    /**
     * METODO PARA VALIDAR CORRECTAMENTE EL FORMATO DE LA FECHA DE NACIMIENTO, SE DEBERA INTRODUCIR UNICAMNTE MUNERO ENTERO,
     * EL DIA ESTARA ENTORNO AL 1-31, EL MES 1-12, EL AÑO DEBERA CONTENER 4 CARACTERES.
     * 
     * @param SE PASARA POR PARAMATROS LA FECHA DE TIPO STRING
     * @return DEVOLVERA TRUE SI LA FECHA SE INTRODUJO CORRECTAMENTE Y FALSE SI CONTINE ERRORES.
     */
    public boolean validarFecha(String fecha) {
        // SimpleDateFormat es una clase concreta para formatear y analizar fechas de manera sensible a la configuración regional. 
        // Permite formatear (fecha → texto), analizar (texto → fecha) y normalizar.
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); // 
            formatoFecha.setLenient(false); // HACE UM ANALISIS ESTRICTO, LAS ENTRADAS DEBEN COINCIDIR CON EL FORMATO.
            formatoFecha.parse(fecha); // ANALIZA LOS DATOS INTRODUCIDOS PARA PRODUCIR LA FECHA.
        } catch (ParseException e) {
            return false;
        }
        return true;
    }   
    
    /**
     * Metodo para validar que el año introducido no sobre pase los limites establecidos, 
     * @param se pasa vsaribale de tipo string(fecha)
     * @return devulve true si no sobre pasa los limites y false en caso contrario.
     */
    public boolean anioValido(String fecha) {
        int max = 2090;
        int min = 1960;
        boolean validar = false;
        String[] tmp = fecha.split("/");

        try {
            if (Integer.parseInt(tmp[2]) > max) {
                validar = false;
                System.out.println("El anio de nacimiento no puede ser superior a 2090");
            }else if(Integer.parseInt(tmp[2]) < min) {
                validar = false;
                System.out.println("El anio de nacimiento no puede ser inferior a 1960");
            }else validar = true;
            
        }catch(Exception e) {
            return false;
        }        
        return validar;
    }
    
    /**
     * METODO QUE SERA UTILIZADO PARA VALIDAR QUE EL N. EXPEDINTE INTRODUCIDO SE HA DE 5 CARACTERES COMO MAXIMO
     * Y NUMEROS DEL 0 AL 9. 
     * 
     * @param DE TIPO STRING ENTRADA DATOS DEL NUMERO EXPEDIENTE.
     * @return RETORNA EL NUMERO DE EXPEDIENTE UNA VEZ SE FINALIZO SU COMPROVACION.
     */
   public String validarExp(String exp){
      Scanner sc = new Scanner(System.in);
      Pattern pat = Pattern.compile("[0-9]{5}"); // SE CONTROLA QUE LOS NUMEROS INTRODUCIDOS SE HAN DEL 0 AL 9 con 5 CARACTERES.                                                        
      Matcher mat = pat.matcher(exp);
      int vueltas = 1;
      
      // MIENTRAS EL EXPEDINTE INTRODUCIDO NO SEA CORRECTO PEDIRA QUE INTRODUZCA LOS DATOS CORRECTAMENTE.
      while(!mat.matches()){
          vueltas++;
          if(!Utils.intentos(vueltas))
              menu();
          System.out.println("\nIntroduzca un N. Expediente valido");
          System.out.println("Debe contener 5 caracteres numericos sin espacios, Formato [XXXXX] \n");
          System.out.print("N. Expediente: ");
          exp = sc.nextLine().trim();
          mat = pat.matcher(exp);          
      }          
      return exp;
   }
 
   
    /**
     * Metodo booleano para comprobar si existen alumnos.
     * @param se le pasa el nombre y apellidos del alumno.
     * @return devuelve true si encuentra coincidencia y false en caso contrario
     */
    public boolean comprobarAlumnos(String nombre, String apellidos) {
           ArrayList<Alumno> alumnos = leerAlumnos(fA);
           boolean existe = false;
           
           for (Alumno a:alumnos)
               if(a.getNombre().equalsIgnoreCase(nombre) && a.getApellidos().equalsIgnoreCase(apellidos))
                   existe = true;
               else existe = false;          
          return existe; 
     }
    
    /**
     * Metodo booleano para comprobar si el numero de expediente existe
     * @param se le pase en expediente
     * @return devulve true si el expedite existe y false en caso contrario
     */
    public boolean comprobarExpediente(String ex) {
           ArrayList<Alumno> alumnos = leerAlumnos(fA);
           
           for (Alumno a:alumnos) 
        	   if(a.getExpediente().equalsIgnoreCase(ex)) 
        		   return true;     
          return false; 
    }
    
    

    

}
