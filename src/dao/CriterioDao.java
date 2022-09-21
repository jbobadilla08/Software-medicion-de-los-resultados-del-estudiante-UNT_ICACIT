package dao;

import Utilidades.Utilitarios;
import beans.Criterio;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class CriterioDao implements ICrudSystem<Criterio>{
    Conexion conn = new Conexion();

    @Override
    public boolean agregar(Criterio obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_Add(?,?,?,?)}");
            cs.setString(1, obj.getCodigo());
            cs.setString(2, obj.getDescripcion());
            cs.setInt(3, obj.getResultadoId());
            cs.registerOutParameter(4, Types.INTEGER);
            cs.execute();
            if (cs.getInt(4) == 1)
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
    public boolean actualizar(Criterio obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_Update(?,?,?,?)}");
            cs.setInt(1, obj.getCriterioId());
            cs.setString(2, obj.getCodigo());
            cs.setString(3, obj.getDescripcion());
            cs.setInt(4, obj.getResultadoId());
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
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_Remove(?,?)}");
            cs.setInt(1, codigo);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            if (cs.getInt(2) == 1)
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
    public boolean buscar(Criterio obj) {
        boolean band = false;
        try { 
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_Get(?)}");
            cs.setInt(1, obj.getCriterioId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setCodigo(rs.getString(1));
                obj.setDescripcion(rs.getString(2));
                obj.setVigente(rs.getBoolean(3));
                obj.setResultadoId(rs.getInt(4));
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

    /** Método devuelve una lista de criterios vigentes y no vigentes
     * @return 
     */
    @Override
    public LinkedList<Criterio> lista() {
        LinkedList<Criterio> listCriterios =  new LinkedList<Criterio>();
        Criterio c;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_GetList()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                c = new Criterio(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5));
                listCriterios.add(c);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listCriterios;
        }
    }
    
    /**
     * Método devuelve una lista de criterios vigentes
     * @return 
     */
    public LinkedList<Criterio> vigentes() {
        LinkedList<Criterio> listCriterios =  new LinkedList<Criterio>();
        Criterio c;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Criterio_GetListVigent()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                c = new Criterio(rs.getInt(1), rs.getString(2));
                listCriterios.add(c);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listCriterios;
        }
    }
}
