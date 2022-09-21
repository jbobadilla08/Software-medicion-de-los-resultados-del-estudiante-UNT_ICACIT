package beans;

import java.util.LinkedList;


/**
 *
 * @author alfie
 */
public class Semestre {
    private int escuelaId;
    private String descripcion;

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

    
    public void setEscuelaId(int escuelaId) {
        this.escuelaId = escuelaId;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
