package beans;

import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class Alumno {
    private String codMatricula;
    private String nombre;
    private String apellido;

    public Alumno() {
    }

    public Alumno(String codMatricula) {
        this.codMatricula = codMatricula;
    }

    public Alumno(String codMatricula, String nombre, String apellido) {
        this.codMatricula = codMatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getCodMatricula() {
        return codMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setCodMatricula(String codMatricula) {
        this.codMatricula = codMatricula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Alumno{" + "codMatricula=" + codMatricula + ", nombre=" + nombre + ", apellido=" + apellido + '}';
    }
    
    
}
