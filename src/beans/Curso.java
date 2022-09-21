package beans;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class Curso {
    private int cursoId;
    private String codigo;
    private String descripcion;
    private byte creditos;
    private byte planCurricular;
    private byte cicloId;

    public Curso() {
    }

    public Curso(int cursoId, String codigo, String descripcion, byte creditos, byte planCurricular, byte cicloId) {
        this.cursoId = cursoId;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.planCurricular = planCurricular;
        this.cicloId = cicloId;
    }

    public Curso(int cursoId) {
        this.cursoId = cursoId;
    }

    public Curso(int cursoId, String codigo, String descripcion, byte creditos) {
        this.cursoId = cursoId;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.creditos = creditos;
    }
    

    public Curso(String codigo, String descripcion, byte creditos, byte planCurricular, byte cicloId) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.planCurricular = planCurricular;
        this.cicloId = cicloId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte getCreditos() {
        return creditos;
    }

    public void setCreditos(byte creditos) {
        this.creditos = creditos;
    }

    public byte getPlanCurricular() {
        return planCurricular;
    }

    public void setPlanCurricular(byte planCurricular) {
        this.planCurricular = planCurricular;
    }

    public byte getCicloId() {
        return cicloId;
    }

    public void setCicloId(byte cicloId) {
        this.cicloId = cicloId;
    }

    @Override
    public String toString() {
        return "Curso{" + "cursoId=" + cursoId + ", codigo=" + codigo + ", descripcion=" + descripcion + ", creditos=" + creditos + ", planCurricular=" + planCurricular + ", cicloId=" + cicloId + '}';
    }
    
}
