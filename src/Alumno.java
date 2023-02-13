package CentroFormacion;

import java.io.Serializable;


public class Alumno implements Serializable{   
    private String expediente; // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDAR EL NUEMRO DE EXPEDINTE.
    private String nombre;     // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA EL NOMBRE DEL ALUMNO.
    private String apellidos;  // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA LOS APELLIDOS DEL ALUMNO.
    private String direccion;  // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA LA DIRECCION DEL DOMICILIO.
    private String telefono;   // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA EL Nº TELEFONO DEL ALUMNO.
    private String fecha;      // VARIBALE PRIVADA DE TIPO STRING DONDE SE GUARDARA LA FECHA DE NACIMIENTO DEL ALUMNO.
                                                                                      
    
    /*
     * Constructor Clase Alumno
     * 
     * @Param: de tipo string, pedira por parametro los datos a introducir 
     * (nº expediente, nombre, apellidos, direccion, telefono y fecha de nacimineto del alumno.)
     *  
     */
    public Alumno(String expediente, String nombre, String apellidos, String direccion, String telefono, String fecha) {
        this.setExpediente(expediente);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setFecha(fecha);
    }
 
    @Override
    public String toString() {      
        return ("\nAlumno, Expediente: " + expediente + "\n\tNombre: " + nombre + "\n\tApellidos: " + apellidos + 
                "\n\tDireccion: " + direccion + "\n\tTelefono: " + telefono + "\n\tFecha nacimiento: " + fecha);      
    }
    
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIABLE EXPEDINTE, EL NUMERO DE EXPEDIENTE DEL ALUMNO.)  
    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIBLE NOMBRE EL NOOMBRE DEL ALUMNO.) 
    public void setNombre(String nombre) {
        this.nombre = nombre;        
    }
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIBLE APELLIDO EL APELLIDO DEL ALUMNO.)
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIBLE DIRECCIONM, LA DIRECCION DE DOMICILIO DEL ALUMNO.)
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIABLE TELEFONO, EL NUMERO DE TELEFNOE DEL ALUMNO.)
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    // METODO QUE NO DEVUEVE NADA, CON ENTRADA POR PARAMETRO DE TIPO STRING (SERA GUARDADO EN LA VARIBLE FECHA, LA FECHA DE NACIMINETO DEL ALUMNO.)
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
        
    // METODO DE TIPO STRING QUE RETORNA EL VALOR DE LA VARIABLE PRIVADA EXPEDIENTE.
    public String getExpediente() {
        return expediente;
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
    // METODO DE TIPO STRING QUE RETORNA EL VALOR DE LA VARIABLE PRIVADA FECHA.
    public String getFecha() {
        return fecha;
    }
    
    

    

 
}