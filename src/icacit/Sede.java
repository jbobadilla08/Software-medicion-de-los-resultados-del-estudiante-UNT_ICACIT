package icacit;

import java.util.HashSet;

/**
 * @author alfie
 */
public class Sede {
    private int sedeId;
    private String descripcion;
    private Escuela escuela;
    private HashSet<Profesor> profesores = new HashSet<Profesor>();
    private HashSet<PlanCurricular> planCurriculars = new HashSet<PlanCurricular>();
    
    public Sede(int sedeId, String dewscripcion){
        this.sedeId = sedeId;
        this.descripcion = descripcion;
    }
    
    // METODOS DE INSTANCIA
    public int getSedeId() {
        return sedeId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Escuela getEscuela() {
        return escuela;
    }

    public HashSet<Profesor> getProfesores() {
        return profesores;
    }

    public HashSet<PlanCurricular> getPlanCurriculars() {
        return planCurriculars;
    }
        
    public void setSedeId(int sedeId) {
        this.sedeId = sedeId;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Establece la escuela a la cual pertenece la sede
     * @param escuela 
     */
    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    /**
     * Agrega un objeto de tipo profesor a la lista de profesores
     * @param profesor
     */
    public void setProfesor(Profesor profesor) {
        profesores.add(profesor);
        profesor.setSede(this);
    }

    /**
     * Agrega el objeto planCurricular a la lista de PlanCurriculars
     * @param planCurricular Guarda la referencia de la sede a la que pertenece
     */
    public void setPlanCurricular(PlanCurricular planCurricular) {
        planCurriculars.add(planCurricular);
        planCurricular.setSede(this);
    }
    
    
    
}
