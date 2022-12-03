package dao;

import Utilidades.Utilitarios;
import beans.Profesor;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class ProfesorDao implements IPersona<Profesor>{
    Conexion conn = new Conexion();
    
    /**
     * Agrega un profesor a la base de datos
     * @param obj
     * @return 
     */
    @Override
    public byte agregar(Profesor obj) {
        byte respuesta = 2; // mensaje de la database
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Profesor_Add(?,?,?,?,?,?,?,?,?,?)}");
            //persona
            cs.setString(1, obj.getDni());
            cs.setString(2, obj.getNombre());
            cs.setString(3, obj.getApellido());
            cs.setInt(4, obj.getTelefono());
            cs.setTimestamp(5, new Timestamp(obj.getLastUpdate().getTime()));
            //prof
            cs.setString(6, obj.getCorreo());
            cs.setString(7, obj.getUsuario());
            cs.setString(8, obj.getPassword());
            cs.setBoolean(9, obj.isEstado());
            cs.registerOutParameter(10, Types.TINYINT);
            cs.executeUpdate(); //ejecutamos
            //verificamos el parametro de salida
            respuesta = (byte)cs.getInt(10);// 0= agrego persona y prof/ 1 = persona existe, agrego prof/ 2 = existe persona y prof 
            cs.close();
        }catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return respuesta;
        }
    }
    
    @Override
    public boolean actualizar(Profesor obj) {
        boolean band = false;
        try { 
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Profesor_Update(?,?,?,?,?,?,?,?,?)}");
            //persona
            cs.setString(1, obj.getDni());
            cs.setString(2, obj.getNombre());
            cs.setString(3, obj.getApellido());
            cs.setInt(4, obj.getTelefono());
            cs.setTimestamp(5, new Timestamp(obj.getLastUpdate().getTime()));
            //prof
            cs.setString(6, obj.getCorreo());
            cs.setString(7, obj.getUsuario());
            cs.setString(8, obj.getPassword());
            cs.setBoolean(9, obj.isEstado());
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
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Profesor_Remove(?,?)}");
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
    public boolean buscar(Profesor obj) {
        boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Profesor_Get(?)}");
            cs.setString(1, obj.getDni());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                //persona
                obj.setNombre(rs.getString(1));
                obj.setApellido(rs.getString(2));
                obj.setTelefono(rs.getInt(3));
                obj.setLastUpdate(rs.getTimestamp(4));
                //prof
                obj.setCorreo(rs.getString(5));
                obj.setUsuario(rs.getString(6));
                obj.setPassword(rs.getString(7));
                obj.setEstado(rs.getBoolean(8));
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
    
    public LinkedList<Profesor> lista() {
        LinkedList<Profesor> listaProf = new LinkedList<Profesor>();
        Profesor p;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Profesor_GetList()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                p = new Profesor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getTimestamp(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getBoolean(9));
                listaProf.add(p);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return listaProf;
        }
    }
    
    /**
     * retorna la lista de profesores con su curso
     * @return 
     */
    public LinkedList<Object[]> listaPhc(boolean privilegios, byte sede) {
        LinkedList<Object[]> lista = new LinkedList<Object[]>();
        
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_ProfesorHasCurso_List(?,?)}");
            cs.setBoolean(1, privilegios);
            cs.setByte(2, sede);
            ResultSet rs = cs.executeQuery();
            Object[] fila;
            while(rs.next()) {
                fila = new Object[5];
               fila[0] = rs.getString(1);
               fila[1] = rs.getString(2);
               fila[2] = rs.getString(3);
               fila[3] = rs.getString(4);
               fila[4] = rs.getString(5);
               lista.add(fila);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return lista;
        }
    }

            
    
    @Override
    public boolean sesion(Profesor obj, int id) {
        boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Sesion(?,?,?)}");
            cs.setString(1, obj.getUsuario()); //enviamos el parametro usuario
            cs.setString(2, obj.getPassword()); //enviamos el parametro password
            cs.setInt(3, id); //enviamos el tipo de cuenta 0 = admin, 1 = prof
            ResultSet rs = cs.executeQuery(); //realiza la consulta y nos trae un administrador
            while (rs.next()) {
                if (obj.getUsuario().equals(rs.getString(7)) && obj.getPassword().equals(rs.getString(8)))//compara el usuario y password de la base con el ingresado
                {
                    //persona
                    obj.setDni(rs.getString(1));
                    obj.setNombre(rs.getString(2));
                    obj.setApellido(rs.getString(3));
                    obj.setTelefono(rs.getInt(4));
                    obj.setLastUpdate(rs.getDate(5));
                    //admin
                    obj.setCorreo(rs.getString(6));
                    obj.setEstado(rs.getBoolean(9));
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

    @Override
    public LinkedList<Profesor> lista(boolean privilegio, byte idSede) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * agrega una relacion entre el profesor y la sede
     * @return 
     */
    public boolean agregar(String dni, byte sede) {
        boolean band = false; // mensaje de la database
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_ProfesorHasSede_Add(?,?,?)}");         
            cs.setString(1, dni);
            cs.setByte(2, sede);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.executeUpdate(); //ejecutamos
            //verificamos el parametro de salida
            if (cs.getInt(3) == 1)
                band = true; // 0 = ya existe, 1 = se agregó
            cs.close();
        }catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }
    
    /**
     * agrega la relacion entre el profesor y los cursos
     */
    public boolean agregar(String dni, int curso, byte sede) {
        boolean band = false; // mensaje de la database
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_ProfesorHasCurso_Add(?,?,?,?)}");         
            cs.setString(1, dni);
            cs.setInt(2, curso);
            cs.setByte(3, sede);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.executeUpdate(); //ejecutamos
            //verificamos el parametro de salida
            if (cs.getInt(4) == 1)
                band = true; // 0 = ya existe, 1 = se agregó
            cs.close();
        }catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }
    
    /**
     * retorna la lista de cursos de un profesor en especifico
     * @param privilegios  dependiendo del tipo superusuario o administrador muestra ciertos datos de la database 
     * @param dni identificación del profesor a buscar
     * @param sede sede que desea bsucar
     * @return lista de cursos
     */
    public LinkedList<Object[]> buscar(boolean privilegios, String dni, byte sede) {
        LinkedList<Object[]> lista = new LinkedList<Object[]>();
        
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_ProfesorHasCurso_GetList(?,?,?)}");
            cs.setBoolean(1, privilegios);
            cs.setString(2, dni);
            cs.setByte(3, sede);
            ResultSet rs = cs.executeQuery();
            Object[] fila;
            while(rs.next()) {
                fila = new Object[6];
               fila[0] = rs.getInt(1); //id
               fila[1] = rs.getString(2); //codigo
               fila[2] = rs.getString(3); //descripcion
               fila[3] = rs.getByte(4); //valor
               fila[4] = rs.getString(5);
               fila[5] = rs.getString(6);
               lista.add(fila);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return lista;
        }
    }
    
    public boolean eliminar(String dni, int cursoId, byte sedeId) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_ProfesorHasCurso_Remove(?,?,?)}");
            cs.setString(1, dni);
            cs.setInt(2, cursoId);
            cs.setByte(3, sedeId);
            if ( cs.executeUpdate() > 0 ) 
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
}
