package CentroFormacion;

import java.util.ArrayList;

public class Curso {
    private String cod; // Variable privada de tipo string que sera utilizada para el codigo del curso. 
    private String nombre; // Variable privada de tipo string que sera utilizada para el nombre del curso. 
    private String descrip; // Variable privada de tipo string que sera utilizada para la descripcion del curso. 
    private Profesor profesor; // Variable privada de tipo Objeto profesor que sera utilizada para el profesor del curso. 
    private ArrayList<Alumno> alumnos; // Variable privada de tipo arrayList<Alumno> que sera utilizada para los alumnos curso. 
     

    public Curso(String cod, String nombre, String descrip, Profesor profesor, ArrayList<Alumno> alumnos) {
        this.cod = cod;
        this.nombre = nombre;
        this.descrip = descrip;
        this.profesor = profesor;
        this.alumnos = alumnos;
    }
       
    @Override
    public String toString() { 
    	String toString = "";
    	toString += "\n----- CURSO Codigo: "+cod+" -----\n\n" + "Nombre: " + nombre + "\tDescripcion: " + descrip + "\n" + profesor;   	
    	if (alumnos.isEmpty()) // si el arrayList alumnos esta vacio imprime mensaje, en caso contrario muestra los alumnos asigandos.
    		toString += "\n\nNo hay alumnos signados a este curso";
    	else {
    	    toString += "\n\nAlumnos asigandos:" + "\n ";
    		for (Alumno a:alumnos)
    		    toString +=  a + "\n";	
    	}
    	return toString;   			
    }
    
    /**
     * Metodo para escribir en fichero de texto con separaciones de ; entre atributos, de profesor guardamos como referencia el dni
     * de alumno se guarda como referencia el numero de expedinte, cada numero de expediente correspondiente a cada alumno se separa con "/".
     * @return variable de tipo string (donde se añaden todos los atributos del objeto.) 
     */
    public String toCSV() {
    	String csv = "";  	
    	csv += cod + ";" + nombre + ";" + descrip + ";" + profesor.getDni() + ";";
    	for (Alumno a:alumnos)
    		if (a != null)
    			csv += a.getExpediente() + "/";    	   	
    	return csv;
    }  
    
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
    
    
    
    
}
