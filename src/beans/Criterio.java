package beans;

/**
 *
 * @author alfie
 */
public class Criterio {
    private int criterioId;
    private String codigo;
    private String descripcion;
    private boolean vigente;
    private int resultadoId;
    
    public Criterio(){
    }

    public Criterio(int criterioId) {
        this.criterioId = criterioId;
    }

    public Criterio(String codigo, String descripcion, int resultadoId) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.resultadoId = resultadoId;
    }

    public Criterio(int criterioId, String codigo, String descripcion, int resultadoId) {
        this.criterioId = criterioId;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.resultadoId = resultadoId;
    }

    public Criterio(int criterioId, String codigo, String descripcion, boolean vigente, int resultadoId) {
        this.criterioId = criterioId;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.vigente = vigente;
        this.resultadoId = resultadoId;
    }

    public Criterio(int criterioId, String codigo) {
        this.criterioId = criterioId;
        this.codigo = codigo;
    }

    public int getCriterioId() {
        return criterioId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isVigente() {
        return vigente;
    }

    public int getResultadoId() {
        return resultadoId;
    }

    public void setCriterioId(int criterioId) {
        this.criterioId = criterioId;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public void setResultadoId(int resultadoId) {
        this.resultadoId = resultadoId;
    }

    @Override
    public String toString() {
        return "Criterio{" + "criterioId=" + criterioId + ", codigo=" + codigo + ", descripcion=" + descripcion + ", vigente=" + vigente + ", resultadoId=" + resultadoId + '}';
    }
    
    
}
