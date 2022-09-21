package beans;
import java.util.Date;
/**
 * @author alfie
 */

public class Profesor extends Persona{
    private String correo;
    private String usuario;
    private String password;
    private boolean estado;

    public Profesor() {
    }

    public Profesor(String dni) {
        super(dni);
    }
    
    public Profesor(String dni, String nombre, String apellido, int telefono, Date lastUpdate, String correo, String usuario, String password, boolean estado){
        super(dni, nombre, apellido, telefono, lastUpdate);
        this.correo = correo;
        this.usuario = usuario;
        this.password = password;
        this.estado = estado;
    }

    public Profesor(String usuario, String password) {
        super(null, null, null, 0, null);
        this.usuario = usuario;
        this.password = password;
    }
    
    //---------- METODOS DE INSTANCIA ----------//

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return super.toString() + "Profesor{" + "correo=" + correo + ", usuario=" + usuario + ", password=" + password + ", estado=" + estado + '}';
    }
    
    
}