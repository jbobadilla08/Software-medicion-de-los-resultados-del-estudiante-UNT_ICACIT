package icacit;

import com.sun.corba.se.impl.oa.toa.TOA;
import java.util.Date;

/**
 * @author alfie
 */
public abstract class Persona {

    private String dni;
    private String nombre;
    private String apellido;
    private int telefono;
    private byte activo;
    private Date lastUpdate;

    // constructor
    public Persona(String dni, String nombre, String apellido, int telefono, byte activo, Date lastUpdate) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.activo = activo;
        this.lastUpdate = lastUpdate;
    }
        
    //---------- METODOS DE INSTANCIA ----------//
    //devuelve el dni
    public String getDni() {
        return dni;
    }

    //devuleve nombre
    public String getNombre() {
        return nombre;
    }

    //devuelve el apellido
    public String getApellido() {
        return apellido;
    }

    //devuelve el telefono
    public int getTelefono() {
        return telefono;
    }

    //devuelve activo o inactivo
    public byte isActivo() {
        return activo;
    }

    //devuelve la ultima fecha de modificacion
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    // asigna el telefno a la persona
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    // asigna si esta activo  o no la persona
    public void setActivo(byte activo) {
        this.activo = activo;
    }

    //asigna la ultima fecha de modificacion
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    /**
     * Realiza la peticion a la base de datos para el inicio de sesión
     */
    public abstract boolean iniciarSesion();

    @Override
    public String toString() {
        return "Persona{" + "dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", activo=" + activo + ", lastUpdate=" + lastUpdate + '}';
    }   
 }
