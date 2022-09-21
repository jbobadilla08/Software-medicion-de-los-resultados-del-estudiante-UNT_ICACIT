package beans;

/**
 *
 * @author alfie
 */
public class Momento {
    private byte momentoId;
    private String nombre;
    private boolean vigente;

    public Momento(byte momentoId, String nombre, boolean vigente) {
        this.momentoId = momentoId;
        this.nombre = nombre;
        this.vigente = vigente;
    }

    public Momento(byte momentoId) {
        this.momentoId = momentoId;
    }

    public Momento(String nombre) {
        this.nombre = nombre;
    }

    public Momento() {
    }

    public Momento(byte momentoId, String nombre) {
        this.momentoId = momentoId;
        this.nombre = nombre;
    }
     

    public byte getMomentoId() {
        return momentoId;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isVigente() {
        return vigente;
    }
    
    public void setMomentoId(byte momentoId) {
        this.momentoId = momentoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
  
    
}
