package icacit;

/**
 *
 * @author alfie
 */
public class NivelLogro {
    private int nivelId;
    private String nombre;
    private String abreviatura;
    private int valMin;
    private int valMax;

    public NivelLogro(int nivelId, String nombre, String abreviatura, int valMin, int valMax) {
        this.nivelId = nivelId;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.valMin = valMin;
        this.valMax = valMax;
    }

    public int getNivelId() {
        return nivelId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public int getValMin() {
        return valMin;
    }

    public int getValMax() {
        return valMax;
    }

    public void setNivelId(int nivelId) {
        this.nivelId = nivelId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public void setValMin(int valMin) {
        this.valMin = valMin;
    }

    public void setValMax(int valMax) {
        this.valMax = valMax;
    }
    
    
}
