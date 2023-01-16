package CentroFormacion;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class ControladorCurso {
    // Rutas de los ficheros "alumnos.bin", "profesores.bin" y "cursos.csv"
    private static File fA = new File("./datos/alumnos.bin");
    private static File fP = new File("./datos/profesores.bin");
    private static File fC = new File("./datos/cursos.csv");

    /**
     * Método que despliega el submenú perteneciente a la clase ControlCurso, el
     * cuál implementa los métodos de "Alta", "Baja", "Modificar" y "Mostrar".
     * 
     * No pide argumentos de entrada, ni retorna ningún tipo de variable u objeto.
     * 
     */
    public void menu() {
        Scanner sc = new Scanner(System.in);
        Menu m = new Menu();
        boolean exit = false;
        while (!exit) {
            System.out.println("\n_____ MENU CURSOS _____");
            System.out.println("\n1. Alta\n2. Baja\n3. Modificar\n4. Mostrar Cursos\n\n0. Atras\n");
            System.out.print("Elige una opcion: ");
            String opcion = sc.nextLine().trim();
            
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
                            System.out.println("Ha seleccionado: MOSTRAR CURSOS\n");
                            mostrarCursos();
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
     * Metodo para dar de alta los cursos, no pide parametros de entrada y no devulve nada, para el alta
     * sera necesario introducir todos los campos que se piden por pantalla.
     * 
     */
    public void alta() {
        Scanner sc = new Scanner(System.in);
        BufferedWriter bw = null;
        String cod = newCodigo();
        
        System.out.print("¿Ha accedido a dar de alta Curso, desea continuar? S/N: ");
        String si = Utils.validarConfirmacion(sc.nextLine().trim());
        // si confirma que que desea continuar y no a entrado por equivocacion, se procede a pedir los datos necesarios para el alta.
        if(si.equalsIgnoreCase("s")) {         	
        	ControladorProfesor cp = new ControladorProfesor();   
        	ArrayList<Profesor> tmp = cp.leerProfesores(fP);
        	// condicion para comprobar que existan profesores para impatir el curso, si el fichero existe y no esta vacio se podra dar de alta
        	// el curso y pedira los datos pertientes por pantalla, en caso contrario, con menajes avisara que es obligatorio tener profesores 
        	// para inpartir el curso y dara opcion a ser redirigido al menu profesores: alta profesor , para crearlo.
        	if (fP.exists() &&  tmp.size() > 0) {
        		System.out.println("\n-- Introduce datos -- \n"); // Pedimos los datos del nuevo curso        		
        		System.out.print("Nombre del curso: ");
        		String nombre = Utils.validarString(sc.nextLine().trim()); // valida que no se introduzca campos vacios, simbolos o numeros, si se introdice el campo 5 veces mal devulve errror.
        		
        		if(nombre.equals("error")) // si a devuelto error, se redirige al menu curso.
        		    return;
        		
        		System.out.print("Descripcion del curso: "); 
        		String descrip = Utils.validarString(sc.nextLine().trim()); // valida que no se introduzca campos vacios, simbolos o numeros, si se introdice el campo 5 veces mal devulve errror.
        		
        		if(descrip.equals("error")) // si a devuelto error, se redirige al menu curso.
        		    return;
        		// condicion para comprobar si el curso exite, si no existe se crea nuevo objeto de tipo curso y se escribe como ficehro de texto.
        		// en caso contrario imprime mensaje avisando que se ha cancelado por que el curso ya existe.
        		if (!comprobarCurso(nombre, descrip)) {  
        			Curso c = new Curso(cod, nombre, descrip, matricularProfesor(), matricularAlumnos());  
        			try {
        				bw = new BufferedWriter(new FileWriter(fC, true));
        				bw.write(c.toCSV()+"\n");               			
        				System.out.println("Curso introducido con exito");
        			} catch (Exception e) {
        				e.printStackTrace();
        			} finally {
        				try {
        					bw.close();
        				} catch (IOException e) {
        					e.printStackTrace();
        				}
        			}
        		} else System.out.println("\nNo ha sido posible dar de alta \nEl Curso: " + nombre + ", descripcion: " + descrip +", ya existe.");
        	} else {
        		System.out.println("\nNo existen profesores dados de alta, es obligatorio para crear el curso.");
        		System.out.print("¿Quiere dar de alta a un nuevo Profesor? S/N: ");
        		String alta = Utils.validarConfirmacion(sc.nextLine().trim());
        		
        		if (alta.equalsIgnoreCase("s")) {
        			ControladorProfesor cP = new ControladorProfesor();
        			System.out.println("Ha sido redirigido: ALTA PROFESOR\n");
        			cP.alta();	
        			alta();
        		}else return;
        	}
        }else System.out.println("\nAlta curso, cancelado.\n");
    }    
    
    
    /**
     * Metodo para dar de baja cursos, se comprueba si existe el fichero, se comprueba si existe el curso, se pide confirmacion
     * para borrarlo.
     * No hay entrada por parametros y no devuelve nada.
     */
    public void baja(){
        Curso c;
        Scanner sc = new Scanner(System.in);
        String entrada = ""; // recoge datos pedidos por teclado.
        BufferedWriter bw = null;
        boolean encontrado = false; // variable que se utilizara para mensaje de error si no se encontro el curso.
        
        System.out.print("¿Ha accedido a borrar curso, desea continuar? S/N: ");
        String s = Utils.validarConfirmacion(sc.nextLine());
        if(s.equalsIgnoreCase("s")) {
        	System.out.println(s);
        	ArrayList<Curso> tmp = leerCursos(fC);        			
        	/* Si el fichero curso existe y es distinto de null, se guarda en arraylist tmp todos los objetos de tipo curso, para poder comparar los datos introducidos por teclado
        	mientras la varibale IT(creada como objeto iterator) contenga datos sera recorrido y guardado posteriormente en el objeto de tipo alumno(A)
        	con iterator se podra recorrer el arraylist y eliminar el contacto selecionado.*/
        	if (fC.exists() && !tmp.isEmpty()) { 
        			try {               
        				System.out.print("Introduce codigo del curso a borrar: ");
        				String datos = sc.nextLine().trim();                                                                             
        				Iterator<Curso> it = tmp.iterator(); // se crea un objeto de la clase iterator de tipo curso que contendrael arraylist tmp
        				
        				/* Mientras hay contactos los recorre y los guarda en el objeto de la clase curso para posteriormente ser comparado
                 		con los datos introducidos por teclado. Si ha encontrado coincidencia y el usuario confirma su eliminacion el contacto sera borrado */
        				while (it.hasNext()) {
        					c = it.next();   
        					// Si los datos a comparar introducidos por el usuario son iguales se procede a confirmar su eliminacion.
        					if (c.getCod().equalsIgnoreCase(datos)) {                        
        					    encontrado = true;
        						System.out.println(c);                                                 						
        						System.out.print("\n¿Estas seguro de borrar el curso? S/N : ");
        						entrada = Utils.validarConfirmacion(sc.nextLine().trim());
        						
        						// si ha selecionado "s el curso sera eliminado, de lo contrario se cancela la operacion.
        						if (entrada.equalsIgnoreCase("s")) {
        							it.remove();                            
        							System.out.println("\nEl curso fue eliminado.");   
        							
        							bw = new BufferedWriter(new FileWriter(fC));
        							// Se recorre el arraylist y se escriben uno a uno los objeto de tipo curso en el fichero.
        							for (Curso w : tmp)
        								bw.write(w.toCSV()+"\n");                   
        							bw.close();                                     
        						}else System.out.println("\nBaja cancelado.");
        					}  
        				}
        				if(!encontrado)
        				    System.out.println("\nEl curso con codigo: "+ datos +", no existe.\n" );
        			} catch (Exception e) {
        				e.printStackTrace();
        			}               
        	}else System.out.println("Cancelado, no hay cursos para dar de baja.");     
        }else System.out.println("\nBaja curso, cancelado.\n");
    }
    
    /**
     * Metodo para poder modificar todos los atributos del objeto curso, si el fichero no existe no se podra realizar ninguna operacion
     * y saldra un mensaje por pantalla indicado que no se ha encontrado el fichero.
     * Este metodo no pide nada por parametros y no devulve nada.
     */
    public void modificar() {
        Scanner sc = new Scanner(System.in);
        boolean validar = false;  // Variable para confirmar el modificado del contacto selecioando.
        boolean coincide = false; // Variable para imprimir mensaje si no encuentra conincidencias.
        String inMod; // Variable para recoger datos por teclado para validar el modificado.  
        BufferedWriter bw = null;
        ArrayList<Curso> tmp = null;
        
        System.out.print("¿Ha accedido a modificar curso, desea continuar? S/N: ");
        String si = Utils.validarConfirmacion(sc.nextLine().trim());
        if(si.equalsIgnoreCase("s")) {      	
            tmp = leerCursos(fC);
        	if (fC.exists() && (!tmp.isEmpty())) {  // comprueba si el fichero existe y no esta vacio.        		
        			System.out.print("Introduce el codigo del curso que desea modificar: ");
        			String entradaDatos = sc.nextLine().trim();
        			
        			/* Bucle para recorrer el arraylist, si el codigo del objeto curso es igual que los datos pedidos porpantalla,
            		se pedira confirmacion para modificar. */
        			for (Curso c:tmp) 
        				if (c.getCod().contentEquals(entradaDatos)) {
        					coincide = true;
        					System.out.print(c);             
        					
        					System.out.print("\n¿Estas seguro de modificar el curso? S/N :  ");
        					inMod = Utils.validarConfirmacion(sc.nextLine().trim());                       
        					
        					// Si la respuesta es S, se procede a preguntar que campos desea modificar con un menu interactivo para su eleccion.
        					// Al salir del menu comprueba que el alumno no exista y se modifican los campos que se han cambiado, si se intenta
        					// modificar el curso a otro curso que ya existe se cancela la operacion.
        					if (inMod.equalsIgnoreCase("s")) {                                                      
        						String nombre = "";
        						String descripcion = "";
        						Profesor profesor = null;
        						ArrayList<Alumno> alumnos = null;
        						
        						validar = false;
        						// bucle para no salir del menu hasta que se presiona el numero 5.
        						do {
        						    System.out.println("\n¿Que campo desea modificar?\n");
        							System.out.println("1. Nombre\t2. Descripcion\t3. Profesor \t4. Alumno\t5. Salir");
        							System.out.print("\nElige una opcion: "); 
        							String optt = sc.nextLine().trim();
        							
        							if(Utils.validarInt(optt)) {// condicion para validar si se a introducido un numero entero.
        								if(Integer.parseInt(optt) <= 5) { // condicion para comprobar que la opcion introducida es la correcta.
        									switch(Integer.parseInt(optt)) {
        									case 1:
        									    System.out.println("Ha seleccionado: Modificar nombre\n");
        										System.out.print("Introduzca nuevo Nombre: ");
        										nombre = Utils.validarString(sc.nextLine().trim()); // se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 18.)         
        										
        										if (!nombre.equalsIgnoreCase("error"))
        										    System.out.println("Nombre modificado correctamente.\n");                        		
        										break;
        									case 2:
        									    System.out.println("Ha seleccionado: Modificar descripcion\n");
        										System.out.print("Introduzca nueva descripcion: ");
        										descripcion = Utils.validarString(sc.nextLine().trim()); // se valida que el campo introducido cumpla con los requisitos(sin campos vacios, no permite numeros ni simbolos, min 5caracteres max 18.)        
        										
        										if (!nombre.equalsIgnoreCase("error"))
        										    System.out.println("Descripcion modificada correctamente.\n");                            	
        										break;
        									case 3:     
        									    System.out.println("Ha seleccionado: Modificar Profesor\n");
        										System.out.print("Introduzca nuevo Profesor: ");   
        										profesor = matricularProfesor(); 
        										
        										if (profesor != null)
        										    System.out.println("Profesor modificado correctamente.\n");
        										break;
        									case 4: 
        									    System.out.println("\n¿Que desea realizar?\n");
        									    System.out.println("1. Anadir Alumno\t2. Baja Alumno\t3. Atras");
        	                                    System.out.print("\nElige una opcion: "); 
        										String opt3 = sc.nextLine();
        										
        										if(Utils.validarInt(opt3)) {
        										    if(Integer.parseInt(opt3) <= 3) {
        										        if(Integer.parseInt(opt3) == 1) {
        										            System.out.println("Ha seleccionado: Anadir alumno\n");
        										            System.out.print("Introduzca nuevo alumno: ");
        										            alumnos = anadirAlumnos(c); // premite añadir alumnos al curso y devulve un arrayList de alumno s que se guarda en la variable alumnos.        										                
        										        }else if (Integer.parseInt(opt3) == 2) {
        										            System.out.println("Ha seleccionado: Baja alumno\n");
        										            removeAlumnoCurso(c); // da de baja en el curso al alumno seleccionado.
        										           
        										            if (alumnos != null)                        
        										                System.out.println("El alumno a sido dado de baja correctamente.\n");      										            
        										        }else if(Integer.parseInt(opt3) == 2) 
        										            return;     										        							        
        										    } else System.out.println("\nError, opcion no valida.\n");       										    
        										}else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n"); 
        										break;
        									case 5:
        										validar = true;
        										/* Al salir se comprueba que el curso modificado no exista de ser asi modifica cada campo que se introdujo, de 
        										 * forma contraria se imprime mensaje para informar que el usuario ya existe y no asido posible modificarlo*/
        										if (!comprobarCurso(nombre, descripcion)) { 
        											if(nombre != "") 
        											    c.setNombre(nombre);                         			    
        											if(descripcion != "") 
        												c.setDescrip(descripcion);       											
        											if(profesor != null) 
        												c.setProfesor(profesor);      											
        											if(alumnos != null) 
        												c.setAlumnos(alumnos); 
        											 try {                               
        						                         bw = new BufferedWriter(new FileWriter(fC));   
        						                         for(Curso w:tmp)
        						                             bw.write(w.toCSV() + "\n"); 

        						                             bw.close();
        						                     } catch (IOException e) {
        						                         e.printStackTrace();
        						                     }
        										}else System.out.println("No ha sido posible modificar, el curso con codigo: " + entradaDatos + ", ya existe."); 
        										break;             
        									}
        								} else System.out.println("\nDebe introducir una opcion valida, pruebe otra vez.");                     	
        							}else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n"); 
        						}while (!validar); 
        					}else System.out.println("\nModificar curso, cancelado."); // SI NO SE CONFIRMA MODIFICAR SE CANCELA LA OPERACION.           
        				}       			
        			// si no se han encontrado coincidencias anteriormente, imprime mensaje de error.
        			if (!coincide)
        				System.out.println("\nNo se ha encontrado coincidencias");       			
        	}else System.out.println("\nNo se han encontrado cursos, fichero vacio o no existe.");  
        }else System.out.println("\nModificar curso, cAncelado.\n");
    }     
        
    /**
     * Metodo para buscar cursos en el fichero, se pedira por pantala los el nombre del curso para su busqueda.
     * Este metodo no devuelve nada y no pide datos por parametro
     */
    public void consulta() {
        Scanner sc = new Scanner(System.in);
        String datos = ""; // recoge datos de la entrada por teclado, seran el nombre del curso a buscar.
        boolean encontrado = false; 
        ArrayList<Curso> tmp = leerCursos(fC); // guarda en tmp todos los objetos de tipo curso del fichero cursos.
        
        if (fC.exists() && !tmp.isEmpty()){   
            System.out.print("Introduce el nombre del curso a consultar: ");
            datos = Utils.validarString(sc.nextLine().trim());               
                
            /* Bucle para recorrer arraylist y comparar los datos introducidos por pantalla,
               si encuentra coincidencia imprime el curso y se le dar el valor true a la variable boolean encontrado. */
            for (Curso c:tmp)  {   
                if (c.getNombre().toLowerCase().contains(datos.toLowerCase())){ //se convierten todos los campos a minusculas para su comparacion.
                    System.out.println(c);
                    encontrado = true;
                }
            }
            if (encontrado == false)
                System.out.println("\nEl Curso " + datos + " no existe.");          
        }else System.out.println("Busqueda cancelada, no existen cursos guardados.");
    }
    
    /**
     * Metodo ArrayList de tipo curso, lee el fichero y añade todos los cursos al array para luego retornarlos. 
     * @param se pasa por parametros el fichero
     * @return arrayList de tipo curso.
     */
    public ArrayList<Curso> leerCursos(File fichero) {
        BufferedReader br = null;  
        ArrayList<Alumno> alumnos = null;    
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ControladorProfesor cp = new ControladorProfesor();
        ControladorAlumno ca = new ControladorAlumno();
        String[] tmp = null;

        if(fC.exists()) {
        	try {
        		br = new BufferedReader(new FileReader(fC));        		
        		String line = "";
        		while((line = br.readLine()) != null) {
        			alumnos = new ArrayList<Alumno>(); // crea un new arrayList de tipo alumno para cada curso.
        			tmp = line.split(";"); 
        			
        			if(tmp.length == 5) {
        				String[] tmp2 = tmp[4].split("/");   		       				
        				for(String s: tmp2) 
        					alumnos.add(ca.buscarAlumnoEX(s)); // retorna el objeto alumno que pretenezca al numero de expediente y lo añade al arraylist.       				     				       				      					       				
        			}
        			cursos.add(new Curso (tmp[0],tmp[1],tmp[2],cp.buscarProfesorDni(tmp[3]),alumnos));        		
        		}       		  
        	} catch (Exception e) {
        		e.printStackTrace();
        	}        	
        }
        return cursos;
    }

    /**
     * Metodo para mostrar por consola todos los curos del fichero cursos
     * este metodo no pide entrada por parametros ni devuelve nada.
     */
    public void mostrarCursos() {
    	ArrayList<Curso> cursos = leerCursos(fC); 
    	if(fC.exists() && !cursos.isEmpty()) {
    			for (Curso c:cursos)
    				System.out.println(c);   			
    	}else System.out.println("\nNo existen cursos para mostrar.\n");
    }

	/**
     * Método mediante el cuál, seleccionamos y guardamos en el Objeto Curso el
     * Objeto de tipo
     * Profesor deseado para impartir dicho curso.
     * 
     * @return Objeto de tipo Profesor
     */
    public Profesor matricularProfesor() {
        // Instanciamos un Objeto de tipo ControladorProfesor, para acceder a los
        // métodos pertenecientes a dicha clase.
        ControladorProfesor CP = new ControladorProfesor();

        // Instanciamos un ArrayList de tipo profesores, y lo cargamos con los
        // profesores ya existentes gracias al método "leerProfesores(Fichero)" de la
        // clase ControladorProfesor
        ArrayList<Profesor> profesores = CP.leerProfesores(fP);
        Scanner sc = new Scanner(System.in);

        boolean flag = false; // Boolean dedicado al control del bucle While
        Profesor pObtenido = null; // Instanciamos un Objeto de tipo Profesor con valor null, para posteriormente asignarle un valor adecuado.

        int i = 0;
        System.out.println("\n--- Profesores disponibles ---");
        for (Profesor p : profesores) 
            System.out.println("\nCodigo: " + i++ + p);            
            
        if (profesores.size() == 0) {
            System.out.println("\nNo hay Profesores disponibles\n");
            flag = true;
        } else {
          	// Bucle para repetir la impresión de profesores y su elección hasta que se
           	// determine que se han introducido valores correctos/esperados.
           	while (!flag) {
	           	System.out.print("\nSeleccione el codigo del Profesor: ");
	            String opt = sc.nextLine().trim();
	                
	            // si los datos introducidos no corresponde a ningun numero entero
	          	//imprime por pantalla un mensaje de Error, en caso contrario compara si el numero introducido corresponde al codigo asignado al profesor.
	            if(Utils.validarInt(opt)) {         	
	               	if (Integer.parseInt(opt) < profesores.size()) {
	               		pObtenido = profesores.get(Integer.parseInt(opt));
	               		flag = true;
	               	} else System.out.print("\nProfesor inexistente, pruebe de nuevo: ");               	             		                	
	            }else System.out.println("Codigo erroneo, pruebe de nuevo.");
           	}
        }
        return pObtenido;
    }

    /**
     * Método que nos permite añadir al Curso Alumnos siempre y cuando el alumno no este ingresado ya en el curso, para ello el metodo 
     * compara los alumnos existentes con los alumnos inscritos en el curso.
     * 
     * @param c Se introduce por parametros el curso que se desea añadir alumnos.
     * @return un ArrayList de tipo Alumno, con los alumnos que asistirán al Curso.
     */
    public ArrayList<Alumno> anadirAlumnos(Curso c) { 
        Scanner sc = new Scanner(System.in);
        ArrayList<Alumno> alumnado = c.getAlumnos(); // ArrayList para recoger los alumnos alumnos exsistentes en el curso.
        ControladorAlumno cA = new ControladorAlumno();
        ArrayList<Alumno> tmp = cA.leerAlumnos(fA);                     
        
        if (fA.exists() && !tmp.isEmpty()) {
            System.out.print("\n¿Desea inscribir alumnos al curso? S/N: ");
            String confirmar = Utils.validarConfirmacion(sc.nextLine().trim());
            
            if(confirmar.equalsIgnoreCase("s")) {
                boolean flag = false;               
                // bucle para recorrer y comparar los alumnos inscritos en el curso con los alumnos existentes, si encuentra coincidencia
                // se borra el alumno de tmp para que ese alumno no pueda ser selecionado y de este modo no se duplique el alumno.
                for(Alumno a:alumnado) {
                    Iterator<Alumno> itTmp = tmp.iterator();
                    while (itTmp.hasNext()) {
                        Alumno aa = itTmp.next();
                        if(a.getExpediente().equals(aa.getExpediente()))
                            itTmp.remove();
                    }                          
                }                
                // si tmp no esta vacio imprime la lista de alumnos disponibles y se podran añadir al curso, si esta vacio imprime mensaje informando de ello.
                if(!tmp.isEmpty()) {
                    while(!flag) {
                        int posicion = 0;
                        System.out.println("\nListado alumnos disponibles:");
                        for(Alumno a:tmp) {
                            System.out.println("ID: "+ posicion++ + a);
                        }                   
                        System.out.print("\nIntroduzca N. ID del alumno a matricular: ");
                        String opt = sc.nextLine().trim();
                        
                        if(Utils.validarInt(opt)){
                            if (Integer.parseInt(opt) < tmp.size()) {                           
                                alumnado.add(tmp.get(Integer.parseInt(opt)));                           
                                tmp.remove(Integer.parseInt(opt));  
                                System.out.println("Alumno anadido correctamente.\n");
                                System.out.println("\n¿Desea matricular otro alumno? S/N: ");
                                String otro = Utils.validarConfirmacion(sc.nextLine().trim()); // valida que se introduzco correctamnte s o n, y guarda la consonante.
                                
                                if (otro.equalsIgnoreCase("s")) {                                       
                                    flag = false;
                                    if (tmp.size() == 0) {
                                        System.out.println("No quedan alumnos disponibles para matricular");
                                        flag = true;
                                    }                       
                                } else flag = true;                         
                            } else System.out.println("\nError, Introduzca un ID valido.\n");      
                        } else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n");                       
                    }                                                                     
                }else System.out.println("\nNo hay alumnos disponibles.\n");                
            }                       
        } 
        return alumnado;
    }
    
    /**
     * Metodo para dar de baja alumnos del curso seleccionado.
     * 
     * @param entrada por parametros objeto curso 
     * @return devuelve ArrayList de alumnos.
     */
    public ArrayList<Alumno> removeAlumnoCurso(Curso c) { 
        ArrayList<Alumno> alumnado = c.getAlumnos();
        Scanner sc = new Scanner(System.in);
        
        // se comprueba que el fichero exista y no este vacio, de ser asi se confirma que se quiere borrar alumno del curso
        // si se valida como si, muestra listado de alumnos inscritos en el curso junto con un id identificando cada alumno
        // y se pide que seleccione el id del alumno que se quiere borrar del curso.
        if (fA.exists() && !alumnado.isEmpty()) {
            System.out.print("\n¿Desea dar de baja alumnos del curso? S/N: ");
            String confirmar = Utils.validarConfirmacion(sc.nextLine().trim());
            
            if(confirmar.equalsIgnoreCase("s")) {
                boolean flag = false;
                
                while(!flag) {
                    int posicion = 0;
                    System.out.println("\nListado alumnos disponibles:");
                    
                    for (Alumno a:alumnado)
                        System.out.println("ID: " + posicion++ + a);
                    
                    System.out.print("\nIntroduzca N. ID del alumno a borrar del curso: ");
                    String opt = sc.nextLine().trim();
                    
                    if(Utils.validarInt(opt)){
                        if (Integer.parseInt(opt) < alumnado.size() && !alumnado.isEmpty()) {                                                      
                            alumnado.remove(Integer.parseInt(opt));                                      
                            System.out.println("\n¿Desea borrar otro alumno? S/N: ");
                            String otro = Utils.validarConfirmacion(sc.nextLine().trim()); // valida que se introduzco correctamnte s o n, y guarda la consonante.
                            
                            if (otro.equalsIgnoreCase("s")) {                                       
                                flag = false;
                                if (alumnado.size() == 0) {
                                    System.out.println("Se eliminaron todos los alumnos del curso: " + c.getCod());
                                    flag = true;
                                }                       
                            } else flag = true;                         
                        } else System.out.println("\nError, Introduzca un ID valido.\n");      
                    } else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n");                       
                }                                                   
            } else System.out.println("\nCancelado anadir alumnos.\n");                      
        }       
        return alumnado;
    }
    
    /**
     * Método que nos permite matricular en el Objeto Curso una serie de Alumnos,
     * los cuales recibirán el Curso impartido, si no existen ningun alumno dado de alta, podra ser redirigido si asi se seleciona, al menu alumnos 
     * dentro de alta alumnos y darlo de alta por primera vez, y se podra de este modo inscribir a ese alumno creado en el curso a dar de alta.
     * 
     * @return un ArrayList de tipo Alumno, con los alumnos que asistirán al Curso.
     */
    public ArrayList<Alumno> matricularAlumnos() { 
    	Scanner sc = new Scanner(System.in);
    	ArrayList<Alumno> alumnado = new ArrayList<Alumno>(); // ArrayList para guardar en el los alumnos seleccionados al curso.
        ControladorAlumno cA = new ControladorAlumno();
        ArrayList<Alumno> tmp = cA.leerAlumnos(fA);      				
        
        if (fA.exists() && !tmp.isEmpty()) {
        	System.out.print("\n¿Desea inscribir alumnos al curso? S/N: ");
        	String confirmar = Utils.validarConfirmacion(sc.nextLine().trim());
        	
        	if(confirmar.equalsIgnoreCase("s")) {
        		boolean flag = false;        		
        		        			
        		while(!flag) { 
        			int posicion = -1;
        			System.out.println("\nListado alumnos disponibles:");
        			for(Alumno a:tmp) {
        				posicion ++;
        				System.out.println("ID: "+ posicion+ a);
        			}       			
        			System.out.print("\nIntroduzca N. ID del alumno a matricular: ");
	        		String opt = sc.nextLine().trim();
	        		
	        		if(Utils.validarInt(opt)){
	        			if (Integer.parseInt(opt) < tmp.size()) {        					
	        				alumnado.add(tmp.get(Integer.parseInt(opt)));	        				
	        		        tmp.remove(Integer.parseInt(opt));						 				
	        				System.out.println("\n¿Desea matricular otro alumno? S/N: ");
	        				String otro = Utils.validarConfirmacion(sc.nextLine().trim()); // valida que se introduzco correctamnte s o n, y guarda la consonante.
	        				
	        				if (otro.equalsIgnoreCase("s")) {   	        	        			
	        					flag = false;
	        					if (tmp.size() == 0) {
	        						System.out.println("No quedan alumnos disponibles para matricular");
	        						flag = true;
	        					}      					
	        				} else flag = true;      					
	        			} else System.out.println("\nError, Introduzca un ID valido.\n");      
	        		} else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n");	        			
        		}       			       		       				
        	}			      		
        } else {
        	System.out.println("No existen alumnos para inscribir en el curso.");
        	System.out.print("¿Desea ser redirigido al menu alumnos: Alta alumno, para crearlo? S/N: ");	        
    		String alta = Utils.validarConfirmacion(sc.nextLine().trim());
        	if (alta.equalsIgnoreCase("s")) {
    			System.out.println("Ha sido redirigido: ALTA AlUMNOS\n");
    			cA.alta();
    			return matricularAlumnos(); // retorna este metodo para inscribir el alumno creado si a si se desea.
    		}else menu();
        }
        return alumnado;
    }
    
    /**
     * Genera un Codigo identificatorio aleatorio no modificable
     * 
     * @param
     * @return Un Codigo de 6 elementos alfanuméricos
     */
    public String newCodigo() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // Caracteres a utilizar para                                                                                        // conformar el Codigo
        Random rnd = new Random(); // Instanciamos un objeto de tipo Random
        StringBuilder sb = new StringBuilder(5); // Otorgamos una longitud al Codigo

        for (int i = 0; i < 6; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length()))); // Asignación de los caracteres anteriores
                                                                  // aleatoriamente (hasta tener 6)
        String codigo = sb.toString();
        return codigo; // retornamos el codigo en un String
    }

    /**
     * Metodo para comprobar que no se repita un curso con el mismo nombre y descripcion
     * @param nombre
     * @param descrip
     * @return devuelve true si hay coincidencia o false en caso contrario
     */
    public boolean comprobarCurso(String nombre, String descrip) {
    	ArrayList<Curso> cursos = leerCursos(fC);
        boolean existe = false;
        
        for (Curso c: cursos)
            if(c.getNombre().equalsIgnoreCase(nombre) && c.getDescrip().equalsIgnoreCase(descrip))
                existe = true;
            else existe = false;         
        return existe;
    }
    
    /**
     * Metodo para comprobar que no se pueda escribir un alumno al mismo curso.
     * @param refAlumno, se pasa por parametros el expediente del alumno.
     * @return devulve true si encontro coincidencia y false en caso contrario.
     */
    public boolean comprobarMatriculaAlumno(String refAlumno) {
    	boolean existe = false;
    	ControladorAlumno ca = new ControladorAlumno();
    	ArrayList<Curso> cursos = leerCursos(fC);
    	
    	for (Curso c:cursos) 
    		for (Alumno a:c.getAlumnos()) {
    			if (a.getExpediente().equalsIgnoreCase(ca.validarExp(refAlumno)))
    				existe = true;    		
    		}
    	return existe;
    }
    
    /**
     * Metodo para actualizar los cursos y borrar los alumnos que ya no existen por que se dieron de baja.
     * 
     * @param ex, se introduce por parametros el objeto alumno.
     */
    public void actualizarCurso(Alumno alumno) {
        ArrayList<Curso> cursos = leerCursos(fC); // ArrayList de tipo curso carga todos los cursos existentes.
        BufferedWriter bw = null;
        Iterator<Alumno> it = null; // se crea iterator de tipo alumno y se instancia como nulo.
        Alumno a; // se crea objeto alumno, sin instanciarlo.
        
        // para cada curso de cursos se compara los alumnos que existen en curso con el alumno pasado por parametro
        // si coincide es que el alumno fue eliminado y por lo tanto se elimina del curso o cursos que estiviera inscrito.
        for (Curso c: cursos) {
            it = c.getAlumnos().iterator();
            while(it.hasNext()) {
                a = it.next(); 
                if(a.getExpediente().equals(alumno.getExpediente()))
                    it.remove();
            }
        }                                             
        try {
            bw = new BufferedWriter(new FileWriter(fC));              
            for(Curso w:cursos)
               bw.write(w.toCSV() + "\n");
            
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }      
    }
    
    /**
     * Metodo para comprobar antes de eliminar un profesor si el profesor esta inscrito en un curso.
     * @param dni
     * @return true si esta inscrito o false en caso contrario.
     */
    public boolean inscripcionProfesor(String dni) {
        ArrayList<Curso> cursos = leerCursos(fC); // ArrayList de tipo curso carga todos los cursos existentes.
        boolean existe = false;
        for (Curso c:cursos) 
           if(c.getProfesor().getDni().equalsIgnoreCase(dni))
               existe = true;
           else existe = false;    
        return existe;   
    }
    
    
}
