
package dao;

import Utilidades.Utilitarios;
import beans.Ciclo;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class CicloDao implements ICrudSystem<Ciclo>{
Conexion conn = new Conexion();

    @Override
    public boolean agregar(Ciclo obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Ciclo_Add(?,?,?,?)}");
            cs.setString(1, obj.getDescripcion());
            cs.setBoolean(2, obj.isParImpar());
            cs.setByte(3, obj.getMomentoId());
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
    public boolean actualizar(Ciclo obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Ciclo_Update(?,?,?,?)}");
            cs.setByte(1, obj.getCicloId());
            cs.setString(2, obj.getDescripcion());
            cs.setBoolean(3, obj.isParImpar());
            cs.setByte(4, obj.getMomentoId());
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
    public boolean buscar(Ciclo obj) {
        boolean band = false;
        try { 
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Ciclo_Get(?)}");
            cs.setByte(1, obj.getCicloId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setDescripcion(rs.getString(1));
                obj.setParImpar(rs.getBoolean(2));
                obj.setMomentoId(rs.getByte(3));
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
     * lista los ciclos
     * @return retorna todos los ciclos
     */
    @Override
    public LinkedList<Ciclo> lista() {
        LinkedList<Ciclo> listCiclos =  new LinkedList<Ciclo>();
        Ciclo ciclo;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Ciclo_GetList()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                ciclo = new Ciclo(rs.getByte(1),rs.getString(2), rs.getBoolean(3), rs.getByte(4));
                listCiclos.add(ciclo);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listCiclos;
        }
    }
    
    
}
