package beans;

import java.util.Date;

/**
 *
 * @author alfie
 */
public class Resultado {
    private int resultadoId;
    private String codigo;
    private String titulo;
    private String descripcion;
    private String version;
    private boolean vigente;
    private Date fechaCreacion;

    public Resultado(int resultadoId, String codigo, String titulo, String descripcion, Date fechaCreacion, boolean vigente, String version) {
        this.resultadoId = resultadoId;
        this.codigo = codigo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.vigente = vigente;
        this.version = version;
    }

    public Resultado() {
    }

    public Resultado(String codigo, String titulo, String descripcion, String version, boolean vigente, Date fechaCreacion) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.version = version;
        this.vigente = vigente;
        this.fechaCreacion = fechaCreacion;
    }

    public Resultado(int resultadoId) {
        this.resultadoId = resultadoId;
    }
    

    public int getResultadoId() {
        return resultadoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public boolean isVigente() {
        return vigente;
    }

    public String getVersion() {
        return version;
    }

    public void setResultadoId(int resultadoId) {
        this.resultadoId = resultadoId;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    
    
}
