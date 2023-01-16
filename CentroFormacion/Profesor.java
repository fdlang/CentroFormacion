package CentroFormacion;

import java.io.Serializable;


public class Profesor implements Serializable {
    private String nombre;    // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA EL NOMBRE DEL PROFESOR.
    private String apellidos; // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA LOS APELLIDOS DEL PROFESOR.
    private String direccion; // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA LA DIRECCION DEL DOMICILIO.
    private String telefono;  // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA EL Nº TELEFONO DEL PROFESOR.
    private String dni;       // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA EL DNI DEL PROFESOR.
                                                                                      
    
    /**
     * CONSTRUCTOR DE LA CLASE PROFESOR.
     * 
     * @Param: de tipo string, pedira por parametro los datos a introducir 
     * (NOMBRE, APELLIDOS, DIRECCION, TELEFONO Y DNI DEL PROFESOR.)
     *  
     */
    public Profesor(String nombre, String apellidos, String direccion, String telefono, String dni) {
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setDni(dni);
    }
    
    @Override
    public String toString() {
    	return ("\nProfesor, DNI: " + dni + "\n\t  Nombre: " + nombre + "\n\t  Apellidos: " + apellidos + "\n\t  Direccion: " + direccion + "\n\t  Telefono: " + telefono);      
    }
    
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIBLE NOMBRE, EL NOMBRE DEL PROFESOR.)    
    public void setNombre(String nombre) {
        this.nombre = nombre;        
    }
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIBLE APELLIDO, EL APELLIDO DEL PROFESOR.)
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIBLE DIRECCIONM, LA DIRECCION DE DOMICILIO DEL PROFESOR.)
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIABLE TELEFONO, EL NUMERO DE TELEFNOE DEL PROFESOR.)
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    // METODO QUE NO DEVULVE NADA, CON ENTRADA POR PARAMETORS DE TIPO STRING (SERA GUARDADO EN LA VARIABLE DNI, EL NUMERO DE DNI DEL PROFESOR.
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    
    // METODO DE TIPO STRING QUE RETORNA EL VALOR DE LA VARIABLE PRIVADA NOMBRE.
    public String getNombre() {
        return nombre;
    }
    // METODO DE TIPO STRING QUE RETORNA EL VALOR DE LA VARIABLE PRIVADA APELLIDOS.
    public String getApellidos() {
        return apellidos;
    }
    // METODO DE TIPO STRING QUE RETORNA EL VALOR DE LA VARIABLE PRIVADA DIRECCION.
    public String getDireccion() {
        return direccion;
    }
    // METODO DE TIPO STRING QUE RETORNA EL VALOR DE LA VARIABLE PRIVADA TELEFONO.
    public String getTelefono() {
        return telefono;
    }
    // METODO DE TIPO STRING QUE RETORNA EL VALOR DE LA VARIABLE PRIVADA DNI.
    public String getDni() {
        return dni;
    }
   


    
}