/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Utilidades.Utilitarios;
import beans.Curso;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class CursoDao implements ICrudSystem<Curso>{
    Conexion conn = new Conexion();

    @Override
    public boolean agregar(Curso obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Curso_Add(?,?,?,?,?,?)}");
            cs.setString(1, obj.getCodigo());
            cs.setString(2, obj.getDescripcion());
            cs.setByte(3, obj.getCreditos());
            cs.setByte(4, obj.getPlanCurricular());
            cs.setByte(5, obj.getCicloId());
            cs.registerOutParameter(6, Types.INTEGER);
            cs.execute();
            if (cs.getInt(6) == 1)
                band = true;
            cs.close();
        } catch(Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean actualizar(Curso obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Curso_Update(?,?,?,?,?,?)}");
            cs.setInt(1, obj.getCursoId());
            cs.setString(2, obj.getCodigo());
            cs.setString(3, obj.getDescripcion());
            cs.setByte(4, obj.getCreditos());
            cs.setByte(5, obj.getPlanCurricular());
            cs.setByte(6, obj.getCicloId());
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

    @Override
    public boolean buscar(Curso obj) {
        boolean band = false;
        try { 
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Curso_Get(?)}");
            cs.setInt(1, obj.getCursoId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setCodigo(rs.getString(1));
                obj.setDescripcion(rs.getString(2));
                obj.setCreditos(rs.getByte(3));
                obj.setPlanCurricular(rs.getByte(4));
                obj.setCicloId(rs.getByte(5));
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

    /**
     * lista los cursos
     * @return retorna todos los cursos
     */
    @Override
    public LinkedList<Curso> lista() {
        LinkedList<Curso> listCursos =  new LinkedList<Curso>();
        Curso curso;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Curso_GetList()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                curso = new Curso(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getByte(4), rs.getByte(5), rs.getByte(6));
                listCursos.add(curso);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listCursos;
        }
    }
    
    /**
     * lista los cursos buscados por nombre
     * @param descripcion
     * @return lista de cursos que coinciden con la descripcion
     */
    public LinkedList<Curso> lista(String descripcion) {
        LinkedList<Curso> listCursos =  new LinkedList<Curso>();
        Curso curso;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Curso_GetSearch(?)}");
            cs.setString(1, descripcion);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                curso = new Curso(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getByte(4), rs.getByte(5), rs.getByte(6));
                listCursos.add(curso);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listCursos;
        }
    }
    
    
    /**
     * lista los cursos que pertenecen a un plan curricular y a un ciclo
     * @param descripcion
     * @return lista de cursos que coinciden con el plan y el ciclo
     */
    public LinkedList<Curso> lista(String plan, String ciclo) {
        LinkedList<Curso> listCursos =  new LinkedList<Curso>();
        Curso curso;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Curso_GetPC(?,?)}");
            cs.setString(1, plan);
            cs.setString(2, ciclo);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                curso = new Curso(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getByte(4));
                listCursos.add(curso);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listCursos;
        }
    }
}
