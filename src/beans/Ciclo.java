package beans;

import java.util.LinkedList;



/**
 * @author alfie
 */
public class Ciclo {
    private byte cicloId;
    private String descripcion;
    private boolean parImpar;
    private byte momentoId;

    public Ciclo() {
    }

    public Ciclo(byte cicloId, String descripcion, boolean parImpar, byte momentoId) {
        this.cicloId = cicloId;
        this.descripcion = descripcion;
        this.parImpar = parImpar;
        this.momentoId = momentoId;
    }

    public Ciclo(String descripcion, boolean parImpar, byte momentoId) {
        this.descripcion = descripcion;
        this.parImpar = parImpar;
        this.momentoId = momentoId;
    }

    public Ciclo(byte cicloId) {
        this.cicloId = cicloId;
    }

    public byte getCicloId() {
        return cicloId;
    }

    public void setCicloId(byte cicloId) {
        this.cicloId = cicloId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isParImpar() {
        return parImpar;
    }

    public void setParImpar(boolean parImpar) {
        this.parImpar = parImpar;
    }

    public byte getMomentoId() {
        return momentoId;
    }

    public void setMomentoId(byte momentoId) {
        this.momentoId = momentoId;
    }

    @Override
    public String toString() {
        return "Ciclo{" + "cicloId=" + cicloId + ", descripcion=" + descripcion + ", parImpar=" + parImpar + ", momentoId=" + momentoId + '}';
    }
    
    

    
}
