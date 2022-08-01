package icacit;

/**
 * @author alfie
 */

public class Institucion {
    private final String institucionId;
    private final String nombre;
    private final String direccion;
    
    public Institucion(String institucionId, String nombre, String direccion){
        this.institucionId = institucionId;
        this.nombre = nombre;
        this.direccion = direccion;
    }
    
    public String getInstitucionId(){
        return institucionId;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getDireccion(){
        return direccion;
    }
}