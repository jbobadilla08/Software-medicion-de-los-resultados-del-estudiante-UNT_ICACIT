package icacit;
import data.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
/**
 * @author alfie
 */

public class Profesor extends Persona{
    private String correo;
    private String usuario;
    private String password;
    private HashSet<Sede> sedes = new HashSet<Sede>();
    private HashSet<Curso> cursos = new HashSet<Curso>();
    
    public Profesor(String dni, String nombre, String apellido, int telefono, byte activo, Date lastUpdate, String correo, String usuario, String password){
        super(dni, nombre, apellido, telefono, activo, lastUpdate);
        this.correo = correo;
        this.usuario = usuario;
        this.password = password;
    }

    public Profesor(String usuario, String password) {
        super(null, null, null, 0, (byte)0, null);
        this.usuario = usuario;
        this.password = password;
    }
    
    //---------- METODOS DE INSTANCIA ----------//
    //devuelve el correo de profesor
    public String getCorreo(){
        return correo;
    }
    
    // devuelve el usuario de un profesor
    public String getUsuario(){
        return usuario;
    }
    
    //devuelve la contraseña del profesor
    public String getPassword(){
        return password;
    }

    public HashSet<Sede> getSedes() {
        return sedes;
    }

    public HashSet<Curso> getCursos() {
        return cursos;
    }
       
    //asigna el correo
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    // asigna el usuario
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    // asigna el password
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Agrega un objeto sede a la lista de sedes
     * @param sede 
     */
    public void setSede(Sede sede) {
        sedes.add(sede);
    }

    /**
     * Asigna los cursos q enseña cada profesor
     * @param cursos 
     */
    public void setCursos(Curso curso) {
        cursos.add(curso);
        curso.setProfesor(this);
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
        String queryUser = "SELECT * FROM profesor WHERE usuario = '"+usuario+"'AND passwd = '"+password+"'";
        rs = conexion.selectQuery(queryUser);
        try {
            //recorremos los datos de la consulta
            rs.next();
            if(usuario.equals(rs.getString(3)) && password.equals(rs.getString(4))) { //compara el usuario y password de la instancia y la database
                setDni(rs.getString(1));
                setCorreo(rs.getString(2));
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