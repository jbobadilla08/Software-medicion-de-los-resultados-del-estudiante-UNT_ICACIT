package dao;

import Utilidades.Utilitarios;
import beans.Definicion;
import beans.DefinicionHasMomento;
import beans.Momento;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class DefinicionDao implements ICrudSystem<Definicion>{
        Conexion conn = new Conexion();

    @Override
    public boolean agregar(Definicion obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Definicion_Add(?,?,?)}");
            cs.setString(1, obj.getDescripcion());
            cs.setInt(2, obj.getCriterioId());
            cs.registerOutParameter(3, Types.INTEGER);
            if (cs.executeUpdate() > 0) {// 1 si se guardo, 0 caso contrario           
                obj.setDefinicionId(cs.getInt(3));
                band = true;
            }
            cs.close();
        }catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean actualizar(Definicion obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Definicion_Update(?,?,?)}");
            cs.setInt(1, obj.getDefinicionId());
            cs.setString(2, obj.getDescripcion());
            cs.setInt(3, obj.getCriterioId());
            System.out.println( obj.toString());
            if (cs.executeUpdate() > 0 ) // update exitoso
                band = true;
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close(); //cerramos conexion
            return band;
        }
    }

    @Override
    public boolean eliminar(int codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean buscar(Definicion obj) {
        boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Definicion_Get(?)}");
            cs.setInt(1, obj.getDefinicionId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setDescripcion(rs.getString(1));
                obj.setCriterioId(rs.getInt(2));
                acceso = true;
            }
            rs.close();
            cs.close();
        } catch ( Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return acceso;
        }
    }

    @Override
    public LinkedList<Definicion> lista() {
        LinkedList<Definicion> listDf = new LinkedList<Definicion>();
        Definicion df;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Definicion_GetList()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                df = new Definicion(rs.getInt(1), rs.getString(2), rs.getInt(3));
                listDf.add(df);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return listDf;
        }
    }
    
    public Momento momentGet(int definicionId) {
        Momento dh = null;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Def_has_moment_GetDef(?)}");
            cs.setInt(1, definicionId);
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                dh = new Momento(rs.getByte(1), rs.getString(2));
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return dh;
        }
    }
}
