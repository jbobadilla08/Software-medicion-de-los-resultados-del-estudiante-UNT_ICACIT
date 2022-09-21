
package dao;

import Utilidades.Utilitarios;
import beans.NivelLogro;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class NivelLogroDao implements ICrudSystem<NivelLogro>{
    Conexion conn = new Conexion();

    @Override
    public boolean agregar(NivelLogro obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_NivelLogro_Add(?,?,?,?,?)}");
            cs.setString(1, obj.getNombre());
            cs.setString(2, obj.getAbreviatura());
            cs.setInt(3, obj.getValMin());
            cs.setInt(4, obj.getValMax());
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
    public boolean actualizar(NivelLogro obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_NivelLogro_Update(?,?,?,?,?)}");
            cs.setInt(1, obj.getNivelId());
            cs.setString(2, obj.getNombre());
            cs.setString(3, obj.getAbreviatura());
            cs.setByte(4, obj.getValMin());
            cs.setByte(5, obj.getValMax());
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
    public boolean buscar(NivelLogro obj) {
        boolean band = false;
        try { 
            CallableStatement cs = conn.getConnection().prepareCall("{call App_NivelLogro_Get(?)}");
            cs.setInt(1, obj.getNivelId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setNombre(rs.getString(1));
                obj.setAbreviatura(rs.getString(2));
                obj.setValMin(rs.getByte(3));
                obj.setValMax(rs.getByte(4));
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
    public LinkedList<NivelLogro> lista() {
        LinkedList<NivelLogro> listNiveles =  new LinkedList<NivelLogro>();
        NivelLogro nl;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_NivelLogro_GetList()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                nl = new NivelLogro(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getByte(4), rs.getByte(5));
                listNiveles.add(nl);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listNiveles;
        }
    }
    
    
}
