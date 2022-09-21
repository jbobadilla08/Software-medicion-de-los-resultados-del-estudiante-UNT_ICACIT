
package dao;

import Utilidades.Utilitarios;
import beans.DefinicionHasNivel;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class DefinicionHasNivelDao implements ICrudSystem<DefinicionHasNivel>{
    Conexion conn = new Conexion();

    @Override
    public boolean agregar(DefinicionHasNivel obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_Has_Nivel_Add(?,?,?,?,?)}");
            cs.setInt(1, obj.getCriterioId());
            cs.setInt(2, obj.getNivelId());
            cs.setString(3, obj.getDescripcion());
            cs.setByte(4, obj.getMomentoId());
            cs.registerOutParameter(5, Types.INTEGER);
            cs.execute();
            if (cs.getInt(5) == 1)
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
    public boolean actualizar(DefinicionHasNivel obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_Has_Nivel_Update(?,?,?,?,?)}");
            cs.setInt(1, obj.getCodigo());
            cs.setInt(2, obj.getCriterioId());
            cs.setInt(3, obj.getNivelId());
            cs.setString(4, obj.getDescripcion());
            cs.setByte(5, obj.getMomentoId());
           
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
    public boolean buscar(DefinicionHasNivel obj) {
        boolean band = false;
        try { 
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_has_Nivel_Get(?)}");
            cs.setInt(1, obj.getCodigo());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setCriterioId(rs.getInt(1));
                obj.setNivelId(rs.getInt(2));
                obj.setDescripcion(rs.getString(3));
                obj.setMomentoId(rs.getByte(4));
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

    @Override
    public LinkedList<DefinicionHasNivel> lista() {
        LinkedList<DefinicionHasNivel> list =  new LinkedList<DefinicionHasNivel>();
        DefinicionHasNivel nl;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_Has_Nivel_GetList()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                nl = new DefinicionHasNivel(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getByte(5));
                list.add(nl);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return list;
        }
    }
    
    
}
