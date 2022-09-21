package beans;




/**
 * @author alfie
 */
public class Sede {
    private byte sedeId;
    private String descripcion;
    
    public Sede(byte sedeId, String descripcion) {
        this.sedeId = sedeId;
        this.descripcion = descripcion;
    }
    
    public Sede() {
    }

    public Sede(byte sedeId) {
        this.sedeId = sedeId;
    } 

    public Sede(String descripcion) {
        this.descripcion = descripcion;
    }
    
    // METODOS DE INSTANCIA
    public byte getSedeId() {
        return sedeId;
    }

    public String getDescripcion() {
        return descripcion;
    }
        
    public void setSedeId(byte sedeId) {
        this.sedeId = sedeId;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Sede{" + "sedeId=" + sedeId + ", descripcion=" + descripcion + '}';
    }
    
}
