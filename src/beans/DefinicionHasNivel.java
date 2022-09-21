package beans;

/**
 *
 * @author alfie
 */
public class DefinicionHasNivel {
    private int codigo;
    private int criterioId;
    private int nivelId;
    private String descripcion;
    private byte momentoId;

    public DefinicionHasNivel() {
    }

    public DefinicionHasNivel(int criterioId, int nivelId, String descripcion, byte momentoId) {
        this.criterioId = criterioId;
        this.nivelId = nivelId;
        this.descripcion = descripcion;
        this.momentoId = momentoId;
    }

    public DefinicionHasNivel(int codigo, int criterioId, int nivelId, String descripcion, byte momentoId) {
        this.codigo = codigo;
        this.criterioId = criterioId;
        this.nivelId = nivelId;
        this.descripcion = descripcion;
        this.momentoId = momentoId;
    }
    

    public DefinicionHasNivel(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCriterioId() {
        return criterioId;
    }

    public void setCriterioId(int criterioId) {
        this.criterioId = criterioId;
    }

    public int getNivelId() {
        return nivelId;
    }

    public void setNivelId(int nivelId) {
        this.nivelId = nivelId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte getMomentoId() {
        return momentoId;
    }

    public void setMomentoId(byte momentoId) {
        this.momentoId = momentoId;
    }
    
    
}
