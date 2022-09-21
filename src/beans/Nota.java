package beans;

import java.util.Date;


/**
 *
 * @author alfie
 */
public class Nota {
    private int notaId;
    private int valor;
    private Date fechaCreacion;
    private Date lastDate;
    private Alumno alumno;

    public Nota(int notaId, int valor, Date fechaCreacion, Date lastDate) {
        this.notaId = notaId;
        this.valor = valor;
        this.fechaCreacion = fechaCreacion;
        this.lastDate = lastDate;
    }

    public int getNotaId() {
        return notaId;
    }

    public int getValor() {
        return valor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public Alumno getAlumno() {
        return alumno;
    }
    
    public void setNotaId(int notaId) {
        this.notaId = notaId;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    /**
     * Referencia de alumno
     * @param alumno 
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }    
}
