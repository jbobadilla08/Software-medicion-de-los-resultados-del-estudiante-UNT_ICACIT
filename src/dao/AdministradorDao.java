package dao;

import Utilidades.Utilitarios;
import beans.Administrador;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.LinkedList;


/**
 *
 * @author alfie
 */
public class AdministradorDao implements IPersona<Administrador>{
    Conexion conn = new Conexion();
    
    @Override
    public byte agregar(Administrador obj) {
        byte respuesta = 2; // mensaje de la database
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Admin_Add(?,?,?,?,?,?,?,?,?,?,?)}");
            //persona
            cs.setString(1, obj.getDni());
            cs.setString(2, obj.getNombre());
            cs.setString(3, obj.getApellido());
            cs.setInt(4, obj.getTelefono());
            cs.setTimestamp(5, new Timestamp(obj.getLastUpdate().getTime()));
            //admin
            cs.setString(6, obj.getUsuario());
            cs.setString(7, obj.getPassword());
            cs.setBoolean(8, obj.isPrivilegios());
            cs.setBoolean(9, obj.isEstado());
            cs.setByte(10, obj.getSedeId()); //id de la sede a la q se le asigna
            cs.registerOutParameter(11, Types.TINYINT);
            cs.executeUpdate(); //ejecutamos
            //verificamos el parametro de salida
            respuesta = cs.getByte(11);// 0= agrego persona y admin/ 1 = persona existe, agrego admin/ 2 = existe persona y admin 
            cs.close();
        }catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return respuesta;
        }
    }
    
    @Override
    public boolean actualizar(Administrador obj) {
        boolean band = false;
        try { 
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Admin_Update(?,?,?,?,?,?,?,?,?,?)}");
            //persona
            cs.setString(1, obj.getDni());
            cs.setString(2, obj.getNombre());
            cs.setString(3, obj.getApellido());
            cs.setInt(4, obj.getTelefono());
            cs.setTimestamp(5, new Timestamp(obj.getLastUpdate().getTime()));
            //admin
            cs.setString(6, obj.getUsuario());
            cs.setString(7, obj.getPassword());
            cs.setBoolean(8, obj.isPrivilegios());
            cs.setBoolean(9, obj.isEstado());
            cs.setByte(10, obj.getSedeId());
            if (cs.executeUpdate() > 0) //devuelve el numero de filas afectadas, cero si no se ingreso
                band = true;
            cs.close();
        }catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean eliminar(String dni) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Admin_Remove(?,?)}");
            cs.setString(1, dni);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.executeUpdate();
            int rpta = cs.getInt(2);
            if (rpta == 1) //develve 1 cuando el acceso es negado, devuelve 0 si el acceso ya esta negado
                band = true;
            cs.close();
        }catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        }
        finally {
            conn.close();
            return band;
        }
        
    }
    
    @Override
    public boolean buscar(Administrador obj) {
        boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Admin_Get(?)}");
            cs.setString(1, obj.getDni());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                //persona
                obj.setNombre(rs.getString(1));
                obj.setApellido(rs.getString(2));
                obj.setTelefono(rs.getInt(3));
                obj.setLastUpdate(rs.getTimestamp(4));
                //admin
                obj.setUsuario(rs.getString(5));
                obj.setPassword(rs.getString(6));
                obj.setPrivilegios(rs.getBoolean(7));
                obj.setEstado(rs.getBoolean(8));
                obj.setSedeId(rs.getByte(9));
                acceso = true;
            }
            rs.close();
            cs.close();
        } catch ( SQLException ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return acceso;
        }
    }
    
    @Override
    public LinkedList<Administrador> lista(boolean privilegio, byte idSede) {
        LinkedList<Administrador> listaAdmin = new LinkedList<Administrador>();
        Administrador p;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Admin_GetList(?,?)}");
            cs.setBoolean(1, privilegio);
            cs.setByte(2, idSede);
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                p = new Administrador(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getTimestamp(5), rs.getString(6), rs.getString(7), rs.getBoolean(8), rs.getBoolean(9), rs.getByte(10));
                listaAdmin.add(p);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return listaAdmin;
        }
    }

    
    @Override
    public boolean sesion(Administrador obj, int id) {
        boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Sesion(?,?,?)}");
            cs.setString(1, obj.getUsuario()); //enviamos el parametro usuario
            cs.setString(2, obj.getPassword()); //enviamos el parametro password
            cs.setInt(3, id); //enviamos el tipo de cuenta 0 = admin, 1 = prof
            ResultSet rs = cs.executeQuery(); //realiza la consulta y nos trae un administrador
            while (rs.next()) {
                if (obj.getUsuario().equals(rs.getString(6)) && obj.getPassword().equals(rs.getString(7)))//compara el usuario y password de la base con el ingresado
                {
                    //persona
                    obj.setDni(rs.getString(1));
                    obj.setNombre(rs.getString(2));
                    obj.setApellido(rs.getString(3));
                    obj.setTelefono(rs.getInt(4));
                    obj.setLastUpdate(rs.getDate(5));
                    //admin
                    obj.setPrivilegios(rs.getBoolean(8));
                    obj.setEstado(rs.getBoolean(9));
                    obj.setSedeId(rs.getByte(10)); //carga el id de la sede a la q pertenece
                    acceso = true;
                }
            }
            rs.close(); //cierra el resultSet
            cs.close(); //cierra el CallableStatement
        } catch ( SQLException ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close(); //cierra conexion con la database
            return acceso;
        }
    } 
}

        