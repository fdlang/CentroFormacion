package CentroFormacion;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    
    /**
     * Metodo para comprobar los intentos permitidos cuando se introduce mal los datos pedidos por pantalla.
     * @param  se pide un numero entero que sera las vueltas que lleva el bucle en el que se encuentre.
     * @return devuelve true si no se sobrepasa los intentos permitidos y false en caso contrario.
     */
    public static boolean intentos(int i){
        if(i > 5) {
            System.out.println("\nCancelado, Debido a sobrepasar el limite de 5 intentos.\n");
            return false;
        }else return true;    
    }
    
    /**
     * Metodo para comprobar si hay campos vacios.
     * @param variable de tipo String cadena
     * @return devuelve true si hay campos vacios y false en caso contrario.
     */
    public static boolean camposVacios(String cadena) {
        return cadena.isEmpty();                   
    }
        
    
    /**
     * Metodo para validar que el campo introducido solo contenga letras, se comprueba primeramente que el campo no este vacio.
     * si se introdujo el campo mal 5 veces se escible en la variable campo "error"
     * @param variable de tipo string que se quiera validar
     * @return devuelve el campo corregido o "error" si se a superado el limite de intentos.
     */
    public static String validarString(String campo) {
        Scanner sc = new Scanner(System.in);
        boolean estado = false;
        int vueltas = 1;
        
        // bucle para comprobar que los campos no esten vacios, si estan vacios el metodo caposVacios() devuelve true, mientras es true y pedira que se introduzca el campo
        // pero si a superado el limite de intentos entra el el if y se le da el valor de "error" a la variable campo. por lo tanto saldra del bucle.
        while(camposVacios(campo)) {
            System.out.print("\nNo puede haber campos vacios, pruebe otra vez: ");
            campo = sc.nextLine().trim();
            vueltas++;
            if (!intentos(vueltas)) {
                campo = "error";
                estado = true;   
            }
        }              
        Pattern pat = Pattern.compile("[a-z A-Z]{4,18}"); // expresion regular, no permite introduccir simbolos ni numeros, min 4, max 15 caracteres
        Matcher mat = pat.matcher(campo);

        while(!estado) {           
                if(mat.matches()){
                    estado = true;
                }else{                   
                    System.out.println("\nCadena Invalida");
                    estado = false;
                    System.out.print("Introduzca min 4, max 18 caracteres, no puede contener numeros ni simbolos:  ");
                    campo = sc.nextLine().trim();
                    vueltas++;

                    if (!intentos(vueltas)) {
                        campo = "error";
                        estado = true;   
                    }
                    // bucle para comprobar que los campos no esten vacios, si estan vacios el metodo caposVacios() devuelve true, mientras es true y pedira que se introduzca el campo
                    // pero si a superado el limite de intentos entra el el if y se le da el valor de "error" a la variable campo. por lo tanto saldra del bucle.
                    while(camposVacios(campo)) {
                        System.out.print("\nNo puede haber campos vacios, pruebe otra vez: ");
                        campo = sc.nextLine().trim();
                        vueltas++;                      
                        if (!intentos(vueltas)) {
                            campo = "error";
                            estado = true;   
                        }
                    }                
                    pat = Pattern.compile("[a-z A-Z]{4,18}"); // expresion regular, no permite introduccir simbolos ni numeros, min 4, max 15 caracteres
                    mat = pat.matcher(campo);   
                }               
        }     
        return campo;             
    }
    
    /**
     * Metodo boolean para validar que el campo introducido es un numero entero
     * @param cadena
     * @return devuelve true si es un numero y false en caso contrario
     */
    public static boolean validarInt(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException n){
    		return false;
    	}
    }
    
    /**
     * Metodo para validar que el campo introducido solo contenga S o N (minisculas o mayusculas), se comprueba primeramente que el campo no este vacio.
     * @param s
     * @return devuelve el campo validado
     */
    public static String validarConfirmacion(String s) {
        Scanner sc = new Scanner(System.in);
        boolean estado = false;
        
        // bucle para comprobar que los campos no esten vacios, si estan vacios el metodo caposVacios() devuelve true.
        while(camposVacios(s)) {
            System.out.print("\nNo puede haber campos vacios, pruebe otra vez: ");
            s = sc.nextLine().trim();
        }              
        Pattern pat = Pattern.compile("[sSnN]{1}"); // expresion regular, no permite introduccir simbolos ni numeros,max 1 caracter
        Matcher mat = pat.matcher(s);
        while(!estado) {
            if(mat.matches()){
                estado = true;
            }else{
                System.out.println("\nEntrada Invalida");
                estado = false;
                System.out.print("Introduzca 'S' para aceptar 'N' para cancelar:  ");
                s = sc.nextLine().trim();
                
                while(camposVacios(s)) {
                    System.out.print("\nNo puede haber campos vacios, pruebe otra vez: ");
                    s = sc.nextLine().trim();
                }                
                pat = Pattern.compile("[sSnN]{1}"); // expresion regular, no permite introduccir simbolos ni numeros,max 1 caracter
                mat = pat.matcher(s);   
            }         
        }     
        return s;             
    }
    
    /**
     * Metodo para validar que telefono introducido solo contenga numros del 0 al 9 con un maximo de 8 caracteres, se comprueba primeramente que el campo no este vacio.
     * @param variable de tipo string que se quiera validar
     * @return devuelve el telefono valido
     */
    public static String validarTelefono(String numeros) {
        Scanner sc = new Scanner(System.in);
        boolean estado = false;
        
        // bucle para comprobar que los campos no esten vacios, se llama al metodo campos vacios.
        while(camposVacios(numeros)) {
            System.out.print("No puede haber campos vacios, pruebe otra vez: ");
            numeros = sc.nextLine().trim();
        }                
        Pattern pat = Pattern.compile("[0-9]{9}"); 
        Matcher mat = pat.matcher(numeros);
        int vueltas = 1;
        while(!estado) {
            if(mat.matches()){
                estado = true;
            }
            else{                
                System.out.println("\nTelefono Invalido");
                estado = false;
                System.out.print("Introduzca 9 caracteres del 0-9: ");
                numeros = sc.nextLine().trim();
                vueltas++;
                
                if(!intentos(vueltas)) {
                    numeros = "error";
                    estado = true;
                }
                
                while(camposVacios(numeros)) {
                    System.out.print("No puede haber campos vacios, pruebe otra vez: ");
                    numeros = sc.nextLine().trim();
                    vueltas++;
                    
                    if(!intentos(vueltas)) {
                        numeros = "error";
                        estado = true;
                    }
                }                
                pat = Pattern.compile("[0-9]{9}");
                mat = pat.matcher(numeros);   
            }         
        }     
        return numeros;             
    }
    
    
    /**
     * METODO PARA CREAR EL DIRECTORIO DONDE SE GUARDARA LOS FICHEROS DE CONTACTO, PROFESORES Y CURSO.
     * 
     * @param DE TIPO STRING QUE RECOGE LA DIRECCION DEL DIRECTORIO.
     */
    public static void crearDirectorio(String dir) {   
    	Path path = Paths.get(dir);
        // Controla si existe o no el directorio, SI NO EXISTE LO CREA."
        if (!Files.exists(path)) {
            try {
                // CREA EL DIRECTORIO CON LA DIRECION INTRODUCIDA POR PARAMETROS.
                Files.createDirectory(path);                
            } catch (Exception e) {
                System.out.println("Error al crear el directorio");
            }
        } 
    }
}
