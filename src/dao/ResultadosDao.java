package dao;

import Utilidades.Utilitarios;
import beans.Resultado;
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
public class ResultadosDao implements ICrudSystem<Resultado>{
    Conexion conn = new Conexion();
    @Override
    public boolean agregar(Resultado obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Resultado_Add(?,?,?,?,?,?)}");
            cs.setString(1, obj.getCodigo());
            cs.setString(2, obj.getTitulo());
            cs.setString(3, obj.getDescripcion());
            cs.setTimestamp(4, new Timestamp(obj.getFechaCreacion().getTime()));
            cs.setBoolean(5, obj.isVigente());
            cs.setString(6, obj.getVersion());
            if (cs.executeUpdate() > 0) 
                band = true;
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean actualizar(Resultado obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Resultado_Update(?,?,?,?,?,?,?)}");
            cs.setInt(1, obj.getResultadoId());
            cs.setString(2, obj.getCodigo());
            cs.setString(3, obj.getTitulo());
            cs.setString(4, obj.getDescripcion());
            cs.setTimestamp(5, new Timestamp(obj.getFechaCreacion().getTime()));
            cs.setBoolean(6, obj.isVigente());
            cs.setString(7, obj.getVersion());
            if (cs.executeUpdate() > 0)  //update ejecutado con exito
                band = true;
            cs.close();
        } catch (SQLException ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean eliminar(int codigo) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Resultado_Remove(?,?)}");
            cs.setInt(1, codigo);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.executeUpdate();
            if (cs.getInt(2) == 1)
                band =  true;
            cs.close();
        } catch (SQLException e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean buscar(Resultado obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Resultado_Get(?)}");
            cs.setInt(1, obj.getResultadoId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setCodigo(rs.getString(1));
                obj.setTitulo(rs.getString(2));
                obj.setDescripcion(rs.getString(3));
                obj.setFechaCreacion(rs.getTimestamp(4));
                obj.setVigente(rs.getBoolean(5));
                obj.setVersion(rs.getString(6));
                band = true;
            }
            rs.close();
            cs.close();
        } catch (SQLException ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public LinkedList<Resultado> lista() {
        LinkedList<Resultado> listResultados = new LinkedList<Resultado>();
        Resultado r;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Resultado_GetList()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                r = new Resultado(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5), rs.getBoolean(6), rs.getString(7));
                listResultados.add(r);                
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listResultados;
        }
    }
    
}
