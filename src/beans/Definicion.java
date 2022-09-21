package beans;

/**
 *
 * @author alfie
 */
public class Definicion {
    private int definicionId;
    private String descripcion;
    private int criterioId;

    public Definicion() {
    }

    public Definicion(int definicionId, String descripcion, int criterioId) {
        this.definicionId = definicionId;
        this.descripcion = descripcion;
        this.criterioId = criterioId;
    }

    public Definicion(int definicionId) {
        this.definicionId = definicionId;
    }

    public Definicion(String descripcion, int criterioId) {
        this.descripcion = descripcion;
        this.criterioId = criterioId;
    }

    public int getDefinicionId() {
        return definicionId;
    }

    public void setDefinicionId(int definicionId) {
        this.definicionId = definicionId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCriterioId() {
        return criterioId;
    }

    public void setCriterioId(int criterioId) {
        this.criterioId = criterioId;
    }

    @Override
    public String toString() {
        return "Definicion{" + "definicionId=" + definicionId + ", descripcion=" + descripcion + ", criterioId=" + criterioId + '}';
    }
    
    
    
    
}
