package icacit;
import data.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
/**
 *
 * @author alfie
 */
public class Administrador extends Persona {
    private String usuario;
    private String password;
    private byte privilegios;
    private Escuela escuela;
    
    //Constructor
    public Administrador(String dni, String nombre, String apellido, int telefono, byte activo, Date lastUpdate, String usuario, String password, byte privilegios){
        super(dni, nombre, apellido, telefono, activo, lastUpdate);
        this.usuario = usuario;
        this.password = password;
        this.privilegios = privilegios;
    }

    public Administrador(String usuario, String password) {
        super(null, null, null, 0, (byte)0, null);
        this.usuario = usuario;
        this.password = password;
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
    
    //devuelve los privilegios del administrador
    public byte getPrivilegios(){
        return privilegios;
    }
    
    public Escuela getEscuela() {
        return escuela;
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
    public void setPrivilegios(byte privilegios){
        this.privilegios = privilegios;
    }

    //guarda la referencia a escuela
    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    @Override
    public String toString() {
        return "Administrador{" + "usuario=" + usuario + ", password=" + password + ", privilegios=" + privilegios + ", escuela=" + escuela + '}';
    }
 
    /**
     * Consulta el usuario y password en la database para iniciar sesion
     * @return retorna un booleano, true acceso al sistema caso contrario false
     */
    public boolean iniciarSesion()
    {
        Conexion conexion = new Conexion();
        conexion.conectar();
        ResultSet rs = null;
        
        boolean acceso = false;
        String queryUser = "SELECT * FROM administrador WHERE usuario = '"+usuario+"'AND passwd = '"+password+"'";
        rs = conexion.selectQuery(queryUser);
        try {
            //recorremos los datos de la consulta
            rs.next();
            if(usuario.equals(rs.getString(2)) && password.equals(rs.getString(3))) { //compara el usuario y password de la instancia y la database
                setDni(rs.getString(1));
                setPrivilegios(rs.getByte(4));
                byte escuelaId = rs.getByte(5); //captura el id de la escuela
                String queryEscuela = "SELECT * FROM escuela  WHERE escuela_id = '"+escuelaId+"'";
                rs = conexion.selectQuery(queryEscuela);
                rs.next();
                setEscuela(new Escuela(escuelaId, rs.getString(2)));
                acceso = true;
            }          
        } catch (SQLException sql) {
            System.out.println("Error al autenticar "+sql.getMessage());
        }
        finally{
            rs = null;
            conexion.desconectar();
            return acceso;
        }
    }
}
