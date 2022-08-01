package icacit;
import java.util.HashSet;
/**
 * @author: Alfie
 * @version: 24/07/2022
 */
public class Escuela {
    private byte escuelaId;
    private String descripcion;
    private HashSet<Administrador> administradores = new HashSet<Administrador>();
    private HashSet<Sede> sedes = new HashSet<Sede>();
    
    //constructor

    /**
     *
     * @param escuelaId
     * @param descripcion
     */
    public Escuela(byte escuelaId, String descripcion){
        this.escuelaId = escuelaId;
        this.descripcion = descripcion;
    }
    
    // METODOS DE INSTANCIA
    public int getEscuelaId() {
        return escuelaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setEscuelaId(byte escuelaId) {
        this.escuelaId = escuelaId;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Agrega un un objeto administrador a la lista de administradores
     * @param administrador objeto que se asigna a la lista de administradores
     */
    public void setAdministrador(Administrador administrador) {
        administradores.add(administrador);
        administrador.setEscuela(this);
    }
    
    /**
     * Agrega un objeto sede a la lista de sedes
     * @param sede objeto que se asigna a la lista
    */
    public void setSede(Sede sede) {
        sedes.add(sede);
        sede.setEscuela(this);
    }
}
