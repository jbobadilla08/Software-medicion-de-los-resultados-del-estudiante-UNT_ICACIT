/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Utilidades.Utilitarios;
import beans.DefinicionHasMomento;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class DefinicionHasMomentoDao implements ICrudSystem<DefinicionHasMomento>{
    Conexion conn = new Conexion();
    
    @Override
    public boolean agregar(DefinicionHasMomento obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Def_Has_Moment_Add(?,?,?)}");
            cs.setInt(1, obj.getDefinicionId());
            cs.setByte(2, obj.getMomentoId());
            cs.registerOutParameter(3, Types.INTEGER);
            cs.executeUpdate();
            if (cs.getInt(3) == 1) // 1 si se guardo, 0 caso contrario         
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
    public boolean actualizar(DefinicionHasMomento obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Def_Has_Moment_Update(?,?)}");
            cs.setInt(1, obj.getDefinicionId());
            cs.setByte(2, obj.getMomentoId());
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
    public boolean buscar(DefinicionHasMomento obj) {
       boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_Def_Has_Moment_Get(?)}");
            cs.setInt(1, obj.getDefinicionId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setMomentoId(rs.getByte(1));
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
    public LinkedList<DefinicionHasMomento> lista() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(int codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
