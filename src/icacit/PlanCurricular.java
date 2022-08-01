package icacit;

import java.util.Date;
import java.util.HashSet;

/**
 * @author alfie
 */
public class PlanCurricular {
    private int planC;
    private String planEstudios;
    private Date fechaCreacion;
    private HashSet<Sede> sedes = new HashSet<Sede>();
    private HashSet<AnioMedicion> anioMedicions = new HashSet<AnioMedicion>();

    public PlanCurricular(int planC, String planEstudios, Date fechaCreacion) {
        this.planC = planC;
        this.planEstudios = planEstudios;
        this.fechaCreacion = fechaCreacion;
    }
    
    //METOS DE INSTANCIA
    public int getPlanC() {
        return planC;
    }

    public String getPlanEstudios() {
        return planEstudios;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public HashSet<Sede> getSedes() {
        return sedes;
    }

    public HashSet<AnioMedicion> getAnioMedicions() {
        return anioMedicions;
    }
    
    public void setPlanC(int planC) {
        this.planC = planC;
    }

    public void setPlanEstudios(String planEstudios) {
        this.planEstudios = planEstudios;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    /**
     * Agrega el objeto sede a la lista de sedes
     * @param sede Guarda la referencia de la sede a la que pertenece
     */
    public void setSede(Sede sede) {
        sedes.add(sede);
    }
    
    /**
     * Agrega el objeto aniomedicion a la lista de anioMedicions
     * @param anioMedicion es agregado a la lista y guarda la referencia del planCurricular a la que pertenece
     */
    public void setAnioMedicions(AnioMedicion anioMedicion) {
        anioMedicions.add(anioMedicion);
        anioMedicion.setPlanCurricular(this);
    }    
}
