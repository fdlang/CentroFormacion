package CentroFormacion;

import java.util.Scanner;

public class Menu {
	
	/**
	 * Metodo para mostrar el menu principal, no recibe datos por parametros y no devuelve nada.
	 */
	public void menuPrincipal() {
		Scanner sc = new Scanner(System.in);
        ControladorAlumno ca = new ControladorAlumno();
        ControladorProfesor cp = new ControladorProfesor();
        ControladorCurso cc = new ControladorCurso();
        boolean exit = false;
        while (!exit) {
            System.out.println("\n_____ MENU PRINCIPAL _____\n");
            System.out.println("1. Alumnos\n2. Profesores\n3. Cursos\n4. Consultas\n\n0. Salir");
            System.out.print("\nIntroduce una opcion: ");
            String opcion = sc.nextLine();

            if (Utils.validarInt(opcion)) {
                if (Integer.parseInt(opcion) < 5) {
                    switch (Integer.parseInt(opcion)) {
                        case 1: 
                            System.out.println("Ha seleccionado: MENU ALUMNOS\n");
                            ca.menu();
                            break;
                        case 2: 
                            System.out.println("Ha seleccionado: MENU PROFESORES\n");
                            cp.menu();
                            break;
                        case 3: 
                            System.out.println("Ha seleccionado: MENU CURSOS\n");
                            cc.menu();
                            break;
                        case 4: 
                            System.out.println("Ha seleccionado: MENU CONSULTAS\n");
                            menuConsulta();
                            break;
                        case 0:
                            exit = true;
                            System.out.println("\n-----------------------------");
                            System.out.println("|    Centro de formacion    |");
                            System.out.println("| cerrado, hasta la vista!  |");
                            System.out.println("-----------------------------\n");
                            break;
                    }
                } else System.out.println("Opcion no disponible, debe introducir una opcion valida.");               
            } else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n");            
        }
	}
	
	/**
	 * Metodo para mostrar el menu consulta, no recibe datos por parametros y no devuelve nada.
	 */
	public void menuConsulta() {
	   	Scanner sc = new Scanner(System.in);
        ControladorAlumno ca = new ControladorAlumno();
        ControladorProfesor cp = new ControladorProfesor();
        ControladorCurso cc = new ControladorCurso();
        boolean exit = false;
        while (!exit) {            
            System.out.println("\n_____ MENU CONSULTA _____\n");
            System.out.println("1. Buscar Alumno\n2. Buscar Profesor\n3. Buscar Curso\n\n0. Atras");
            System.out.print("\nIntroduce una opcion: ");
            String opcion = sc.nextLine();
            
            if(Utils.validarInt(opcion)) {
                if(Integer.parseInt(opcion) < 5) {
                    switch(Integer.parseInt(opcion)) {
                        case 1: 
                            System.out.println("Ha seleccionado: BUSCAR ALUMNO\n");
                            ca.consulta();
                            break;
                        case 2: 
                            System.out.println("Ha seleccionado: BUSCAR PROFESOR\n");
                            cp.consulta();
                            break;
                        case 3: 
                            System.out.println("Ha seleccionado: BUSCAR CURSO\n");
                            cc.consulta();
                            break;
                        case 0: 
                            exit = true;
                            menuPrincipal();
                            break;
                    }      		        	
                }else System.out.println("Opcion no disponible, debe introducir una opcion valida.");
            }else System.out.println("\nError, el campo no permite letras ni simbolos\nPrueba otra vez.\n");	    
        }
	}

    /**
     * @param args
     */
    public static void main(String[] args) {
    	Menu m = new Menu();
        
        System.out.println("\n-----------------------------");
        System.out.println("|    Centro de formacion   |");
        System.out.println("-----------------------------\n");
        m.menuPrincipal();

    }

}
