/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Utilidades.Utilitarios;
import beans.Alumno;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class AlumnoDao implements ICrudSystem<Alumno>{
    Conexion conn = new Conexion();

    @Override
    public boolean agregar(Alumno obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Alumno_Add(?,?,?)}");
            cs.setString(1, obj.getCodMatricula());
            cs.setString(2, obj.getNombre());
            cs.setString(3, obj.getApellido());
            if (cs.executeUpdate() > 0)
                band = true;
            cs.close();
        } catch(SQLException e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean actualizar(Alumno obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Alumnos_Update(?,?,?)}");
            cs.setString(1, obj.getCodMatricula());
            cs.setString(2, obj.getNombre());
            cs.setString(3, obj.getApellido());
            if (cs.executeUpdate() > 0) 
                band = true;
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean eliminar(int codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**busca un alumno por su dni
     * @param obj
     * @return 
     */
    @Override
    public boolean buscar(Alumno obj) {
        boolean band = false;
        try { 
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Alumno_Get(?)}");
            cs.setString(1, obj.getCodMatricula());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setNombre(rs.getString(1));
                obj.setApellido(rs.getString(2));
                band = true;
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    /** Método devuelve una lista de alumnos
     * @return 
     */
    @Override
    public LinkedList<Alumno> lista() {
        LinkedList<Alumno> listAlumnos =  new LinkedList<Alumno>();
        Alumno a;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Alumno_GetList()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                a = new Alumno(rs.getString(1), rs.getString(2), rs.getString(3));
                listAlumnos.add(a);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listAlumnos;
        }
    }
    
    /** Método devuelve una lista de alumnos buscados por el nombre
     * @return 
     */
    
    public LinkedList<Alumno> lista(String apellido) {
        LinkedList<Alumno> listAlumnos =  new LinkedList<Alumno>();
        Alumno a;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Alumno_GetSearch(?)}");
            cs.setString(1, apellido);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                a = new Alumno(rs.getString(1), rs.getString(2), rs.getString(3));
                listAlumnos.add(a);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listAlumnos;
        }
    }
}
