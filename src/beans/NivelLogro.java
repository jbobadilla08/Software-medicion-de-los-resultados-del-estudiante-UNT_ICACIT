package beans;

/**
 *
 * @author alfie
 */
public class NivelLogro {
    private int nivelId;
    private String nombre;
    private String abreviatura;
    private byte valMin;
    private byte valMax;

    public NivelLogro() {
    }

    public NivelLogro(int nivelId) {
        this.nivelId = nivelId;
    }

    public NivelLogro(String nombre, String abreviatura, byte valMin, byte valMax) {
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.valMin = valMin;
        this.valMax = valMax;
    }

    public NivelLogro(int nivelId, byte valMin, byte valMax) {
        this.nivelId = nivelId;
        this.valMin = valMin;
        this.valMax = valMax;
    }

    public NivelLogro(int nivelId, String nombre, String abreviatura, byte valMin, byte valMax) {
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

    public byte getValMin() {
        return valMin;
    }

    public byte getValMax() {
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

    public void setValMin(byte valMin) {
        this.valMin = valMin;
    }

    public void setValMax(byte valMax) {
        this.valMax = valMax;
    }
    
    
}
