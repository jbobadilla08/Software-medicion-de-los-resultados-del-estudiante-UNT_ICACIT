package beans;

import java.util.Date;
import java.util.LinkedList;

/**
 * @author alfie
 */
public class PlanCurricular {
    private byte plancId;
    private String planEstudios;
    private Date fechaCreacion;
//    private LinkedList<Sede> sedes = new LinkedList<Sede>();
//    private LinkedList<AnioMedicion> anioMedicions = new LinkedList<AnioMedicion>();

    public PlanCurricular(byte plancId, String planEstudios, Date fechaCreacion) {
        this.plancId = plancId;
        this.planEstudios = planEstudios;
        this.fechaCreacion = fechaCreacion;
    }

    public PlanCurricular() {
    }

    public PlanCurricular(byte plancId) {
        this.plancId = plancId;
    }

    public PlanCurricular(byte plancId, String planEstudios) {
        this.plancId = plancId;
        this.planEstudios = planEstudios;
    }
        
    public PlanCurricular(String planEstudios, Date fechaCreacion) {
        this.planEstudios = planEstudios;
        this.fechaCreacion = fechaCreacion;
    }
    
    //METOS DE INSTANCIA
    public byte getPlancId() {
        return plancId;
    }

    public String getPlanEstudios() {
        return planEstudios;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

//    public LinkedList<Sede> getSedes() {
//        return sedes;
//    }
//
//    public LinkedList<AnioMedicion> getAnioMedicions() {
//        return anioMedicions;
//    }
    
    public void setPlancId(byte plancId) {
        this.plancId = plancId;
    }

    public void setPlanEstudios(String planEstudios) {
        this.planEstudios = planEstudios;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    
//    /**
//     * Agrega el objeto sede a la lista de sedes
//     * @param sede Guarda la referencia de la sede a la que pertenece
//     */
//    public void setSede(Sede sede) {
//        sedes.add(sede);
//    }
//    
//    /**
//     * Agrega el objeto aniomedicion a la lista de anioMedicions
//     * @param anioMedicion es agregado a la lista y guarda la referencia del planCurricular a la que pertenece
//     */
//    public void setAnioMedicions(AnioMedicion anioMedicion) {
//        anioMedicions.add(anioMedicion);
//        anioMedicion.setPlanCurricular(this);
//    }    

    @Override
    public String toString() {
        return "PlanCurricular{" + "planC=" + plancId + ", planEstudios=" + planEstudios + ", fechaCreacion=" + fechaCreacion + '}';
    }
}
