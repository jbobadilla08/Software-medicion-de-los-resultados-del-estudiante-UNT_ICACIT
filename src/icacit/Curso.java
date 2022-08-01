package icacit;

import java.util.Date;
import java.util.HashSet;

/**
 *
 * @author alfie
 */
public class Curso {
    private int cursoId;
    private String codigo;
    private String descripcion;
    private int creditos;
    private int planCurricular;
    private Ciclo ciclo;
    private HashSet<Profesor> profesors = new HashSet<Profesor>();
    private HashSet<Asignacion> asignacions = new HashSet<Asignacion>();


    public Curso(int cursoId, String codigo, String descripcion, int creditos, int planCurricular) {
        this.cursoId = cursoId;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.planCurricular = planCurricular;
    }

    public int getCursoId() {
        return cursoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getPlanCurricular() {
        return planCurricular;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public HashSet<Profesor> getProfesors() {
        return profesors;
    }

    public HashSet<Asignacion> getAsignacions() {
        return asignacions;
    }

    public void getAsignaciones(){
        for (Asignacion a: asignacions) {
            System.out.println("-> "+a.asignacionId+" "+a.profAsignado);
        }
    }
        
    
    
//    
//    
//    
//
//Recorrerlo usando Iterator.
//
//Se puede pensar en un iterator como un cursor ubicado entre los elementos de la colección que solo permite ir hacia adelante.
//
//String nomAlumno;
//Iterator<String> it = alumnos.iterator();
//while( it.hasNext()) {
//nomAlumno = it.next(); //devuelve el elemento
//System.out.println(nomAlumno);
//}
//    
//    
//    
//    
    
    
    
    
    
    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public void setPlanCurricular(int planCurricular) {
        this.planCurricular = planCurricular;
    }

    /**
     * Guarda la referencia al cilo q pertenece
     * @param ciclo 
     */
    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * Guarda la referencia de los profesores q enseñan este curso
     * @param profesor 
     */
    public void setProfesor(Profesor profesor) {
        profesors.add(profesor);
    }
    
    public void asociarAsignacion(Asignacion asignacion) {
        asignacions.add(asignacion);
        asignacion.setCurso(this);
    }
    
    
    /**
    * Esta clase contiene los atributos y metodos de Asignacion
    * simula la relacion de composicion
    * @author Alfie
    * @version 1.0
    * @see Curso
    */
    public class Asignacion {
        private int asignacionId;
        private String profAsignado;
        private Date fechAsignacion;
        private boolean activo;
        private Curso curso;
        
        //Constructor de Asignacion

        public Asignacion(int asignacionId, String profAsignado, Date fechAsignacion, boolean activo) {
            this.asignacionId = asignacionId;
            this.profAsignado = profAsignado;
            this.fechAsignacion = fechAsignacion;
            this.activo = activo;
        }

        public int getAsignacionId() {
            return asignacionId;
        }

        public String getProfAsignado() {
            return profAsignado;
        }

        public Date getFechAsignacion() {
            return fechAsignacion;
        }

        public boolean isActivo() {
            return activo;
        }

        public Curso getCurso() {
            return curso;
        }
        
        public void setAsignacionId(int asignacionId) {
            this.asignacionId = asignacionId;
        }

        public void setProfAsignado(String profAsignado) {
            this.profAsignado = profAsignado;
        }

        public void setFechAsignacion(Date fechAsignacion) {
            this.fechAsignacion = fechAsignacion;
        }

        public void setActivo(boolean activo) {
            this.activo = activo;
        }

        public void setCurso(Curso curso) {
            this.curso = curso;
        }
    }
    
}
