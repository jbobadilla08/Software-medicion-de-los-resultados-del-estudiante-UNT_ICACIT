package beans;

import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class AnioMedicion {
    private int anioMedicionId;
    private String anioElectivo;
    private byte meta;
//    private LinkedList<PlanCurricular> planCurriculars = new LinkedList<PlanCurricular>();
//    private LinkedList<Semestre> semestres = new LinkedList<Semestre>();

    public AnioMedicion() {
    }

    public AnioMedicion(String anioElectivo, byte meta) {
        this.anioElectivo = anioElectivo;
        this.meta = meta;
    }

    public AnioMedicion(int anioMedicionId) {
        this.anioMedicionId = anioMedicionId;
    }
    
    public AnioMedicion(int anioMedicionId, String anioElectivo, byte meta) {
        this.anioMedicionId = anioMedicionId;
        this.anioElectivo = anioElectivo;
        this.meta = meta;
    }
    
    
    //METODOS DE INSTANCIA
    public int getAnioMedicionId() {
        return anioMedicionId;
    }

    public String getAnioElectivo() {
        return anioElectivo;
    }

    public byte getMeta() {
        return meta;
    }

//    public LinkedList<PlanCurricular> getPlanCurriculars() {
//        return planCurriculars;
//    }
//
//    public LinkedList<Semestre> getSemestres() {
//        return semestres;
//    }
        
    public void setAnioMedicionId(int anioMedicionId) {
        this.anioMedicionId = anioMedicionId;
    }

    public void setAnioElectivo(String anioElectivo) {
        this.anioElectivo = anioElectivo;
    }

    public void setMeta(byte meta) {
        this.meta = meta;
    }

//    /**
//     * agrega el objeto a la lista
//     * @param planCurricular guarda la referencia de planCurricular
//     */
//    public void setPlanCurricular(PlanCurricular planCurricular) {
//        planCurriculars.add(planCurricular);
//    }
//
//    /**
//     * Agrega el objeto semestre a la lista
//     * @param semestre 
//     */
//    public void setSemestre(Semestre semestre) {
//        semestres.add(semestre);
//        semestre.setAnioMedicion(this);
//    }    

    @Override
    public String toString() {
        return "AnioMedicion{" + "anioMedicionId=" + anioMedicionId + ", anioElectivo=" + anioElectivo + ", meta=" + meta + '}';
    }
    
}
