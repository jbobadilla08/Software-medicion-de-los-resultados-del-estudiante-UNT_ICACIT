/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Utilidades.Utilitarios;
import beans.Momento;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class MomentoDao implements ICrudSystem<Momento>{
    Conexion conn = new Conexion();

    @Override
    public boolean agregar(Momento obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Momento_Add(?,?)}");
            cs.setString(1, obj.getNombre());
            cs.registerOutParameter(2, Types.INTEGER);
            cs.executeUpdate();
            if (cs.getInt(2) == 1) // 1 si se guardo, 0 caso contrario           
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
    public boolean actualizar(Momento obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Momento_Update(?,?,?)}");
            cs.setByte(1, obj.getMomentoId());
            cs.setString(2, obj.getNombre());
            cs.setBoolean(3, obj.isVigente());
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
       boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Momento_Remove(?,?)}");
            cs.setByte(1, (byte)codigo);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.executeUpdate();
            if ( cs.getInt(2) == 1)
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
    public boolean buscar(Momento obj) {
        boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Momento_Get(?)}");
            cs.setByte(1, obj.getMomentoId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setNombre(rs.getString(1));
                obj.setVigente(rs.getBoolean(2));
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
    public LinkedList<Momento> lista() {
        LinkedList<Momento> listDf = new LinkedList<Momento>();
        Momento m;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Momento_GetList()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                m = new Momento(rs.getByte(1), rs.getString(2), rs.getBoolean(3));
                listDf.add(m);
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
    
    /**
     * Método devuelve una lista de criterios vigentes
     * @return 
     */
    public LinkedList<Momento> vigentes() {
        LinkedList<Momento> listMomentos =  new LinkedList<Momento>();
        Momento m;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_Momento_GetListVigent()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                m = new Momento(rs.getByte(1), rs.getString(2));
                listMomentos.add(m);
            }
            rs.close();
            cs.close();
        } catch (Exception e) {
            Utilitarios.mensaje(e.toString(), 0);
        } finally {
            conn.close();
            return listMomentos;
        }
    }
    
    
    
}
