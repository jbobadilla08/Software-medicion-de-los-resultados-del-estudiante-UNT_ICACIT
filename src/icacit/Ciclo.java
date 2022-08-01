package icacit;

import java.util.HashSet;

/**
 * @author alfie
 */
public class Ciclo {
    private int cicloId;
    private String descripcion;
    private boolean parImpar;
    private HashSet<Semestre> semestres = new HashSet<Semestre>();
    private HashSet<Curso> cursos = new HashSet<Curso>();
    private Momento momento;

    public Ciclo(int cicloId, String descripcion, boolean parImpar) {
        this.cicloId = cicloId;
        this.descripcion = descripcion;
        this.parImpar = parImpar;
    }

    public int getCicloId() {
        return cicloId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isParImpar() {
        return parImpar;
    }

    public HashSet<Semestre> getSemestres() {
        return semestres;
    }

    public HashSet<Curso> getCursos() {
        return cursos;
    }

    public Momento getMomento() {
        return momento;
    }
            
    public void setCicloId(int cicloId) {
        this.cicloId = cicloId;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setParImpar(boolean parImpar) {
        this.parImpar = parImpar;
    }

    /**
     * Guarda la referencia de Semestre
     * @param semestre 
     */
    public void setSemestre(Semestre semestre) {
        semestres.add(semestre);
    }

    /**
     * Agrega un objeto curso a la lista de cursos
     * @param curso 
     */
    public void setCurso(Curso curso) {
        cursos.add(curso);
        curso.setCiclo(this);
    }
    
    /**
     * Guarda la referencia del momento que indica a que momento pertenece
     * @param momento 
     */
    public void setMomento(Momento momento) {
        this.momento = momento;
    }
    
    
}
