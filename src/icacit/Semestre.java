package icacit;

import java.util.HashSet;

/**
 *
 * @author alfie
 */
public class Semestre {
    private int escuelaId;
    private String descripcion;
    private AnioMedicion anioMedicion;
    private HashSet<Ciclo> ciclos = new HashSet<Ciclo>();

    public Semestre(int escuelaId, String descripcion) {
        this.escuelaId = escuelaId;
        this.descripcion = descripcion;
    }

    public int getEscuelaId() {
        return escuelaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public AnioMedicion getAnioMedicion() {
        return anioMedicion;
    }

    public HashSet<Ciclo> getCiclos() {
        return ciclos;
    }
    
    public void setEscuelaId(int escuelaId) {
        this.escuelaId = escuelaId;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Guarda la referencia dl anioMedicion
     * @param anioMedicion 
     */
    public void setAnioMedicion(AnioMedicion anioMedicion) {
        this.anioMedicion = anioMedicion;
    }

    /**
     * Agrega un objeto ciclo a la lista de ciclos
     * @param ciclo 
     */
    public void setCiclo(Ciclo ciclo) {
        ciclos.add(ciclo);
        ciclo.setSemestre(this);
    }    
}
