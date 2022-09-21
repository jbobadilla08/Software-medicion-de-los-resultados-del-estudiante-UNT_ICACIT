package beans;
import java.util.Date;
/**
 *
 * @author alfie
 */
public class Administrador extends Persona {
    private String usuario;
    private String password;
    private boolean privilegios;
    private boolean estado;
    private byte sedeId;
    
    //Constructor
    
    public Administrador(){
    }

    public Administrador(String dni, String nombre, String apellido, int telefono, Date lastUpdate, String usuario, String password, boolean privilegios, boolean estado, byte sedeId) {
        super(dni, nombre, apellido, telefono, lastUpdate);
        this.usuario = usuario;
        this.password = password;
        this.privilegios = privilegios;
        this.estado = estado;
        this.sedeId = sedeId;
    }  

    public Administrador(String usuario, String password) {
        super(null, null, null, 0, null);
        this.usuario = usuario;
        this.password = password;
    }

    public Administrador(String dni) {
        super(dni);
    }

    public Administrador(String dni, boolean privilegios) {
        super(dni);
        this.privilegios = privilegios;
    }
    
    
    //---------- METODOS DE INSTANCIA ----------//
    //devuelve el usuario del administrador
    public String getUsuario(){
        return usuario;
    }
    
    //devuelve el password del administrador
    public String getPassword(){
        return password;
    }

    public byte getSedeId() {
        return sedeId;
    }

    public boolean isEstado() {
        return estado;
    }

    public boolean isPrivilegios() {
        return privilegios;
    }
    
    //asigna el usuario
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    //asigna el password
    public void setPassword(String password){
        this.password = password;
    }
    
    //asigna los privilegios
    public void setPrivilegios(boolean privilegios){
        this.privilegios = privilegios;
    }

    public void setSedeId(byte sedeId) {
        this.sedeId = sedeId;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return super.toString() + "Administrador{" + "usuario=" + usuario + ", password=" + password + ", privilegios=" + privilegios + ", estado=" + estado + ", sedeId=" + sedeId + '}';
    }
 
    
}