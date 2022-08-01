package icacit;

import java.util.HashSet;

/**
 *
 * @author alfie
 */
public class AnioMedicion {
    private int anioMedicionId;
    private String anioElectivo;
    private int meta;
    private HashSet<PlanCurricular> planCurriculars = new HashSet<PlanCurricular>();
    private HashSet<Semestre> semestres = new HashSet<Semestre>();

    public AnioMedicion(int anioMedicionId, String anioElectivo, int meta) {
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

    public int getMeta() {
        return meta;
    }

    public HashSet<PlanCurricular> getPlanCurriculars() {
        return planCurriculars;
    }

    public HashSet<Semestre> getSemestres() {
        return semestres;
    }
        
    public void setAnioMedicionId(int anioMedicionId) {
        this.anioMedicionId = anioMedicionId;
    }

    public void setAnioElectivo(String anioElectivo) {
        this.anioElectivo = anioElectivo;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    /**
     * agrega el objeto a la lista
     * @param planCurricular guarda la referencia de planCurricular
     */
    public void setPlanCurricular(PlanCurricular planCurricular) {
        planCurriculars.add(planCurricular);
    }

    /**
     * Agrega el objeto semestre a la lista
     * @param semestre 
     */
    public void setSemestre(Semestre semestre) {
        semestres.add(semestre);
        semestre.setAnioMedicion(this);
    }
    

    
    
    
}
