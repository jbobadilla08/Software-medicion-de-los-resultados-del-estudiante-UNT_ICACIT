package dao;

import Utilidades.Utilitarios;
import beans.PlanCurricular;
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
public class PlanCurricularDao implements ICrudSystem<PlanCurricular>{
    Conexion conn = new Conexion();
    
    @Override
    public boolean agregar(PlanCurricular obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_PlanCurricular_Add(?,?,?)}");
            cs.setString(1, obj.getPlanEstudios());
            cs.setTimestamp(2, new Timestamp(obj.getFechaCreacion().getTime()));
            cs.registerOutParameter(3, Types.INTEGER);
            cs.executeUpdate();
            if (cs.getInt(3) == 1) // 1 si se guardo, 0 caso contrario           
                band = true;
            cs.close();
        }catch (SQLException ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }

    @Override
    public boolean actualizar(PlanCurricular obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_PlanCurricular_Update(?,?,?)}");
            cs.setByte(1, obj.getPlancId());
            cs.setString(2, obj.getPlanEstudios());
            cs.registerOutParameter(3, Types.INTEGER);
            cs.executeUpdate();
            if (cs.getInt(3) == 1) // update exitoso
                band = true;
            cs.close();
        } catch (SQLException ex) {
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
    public boolean buscar(PlanCurricular obj) {
        boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_PlanCurricular_Get(?)}");
            cs.setByte(1, obj.getPlancId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setPlanEstudios(rs.getString(1));
                obj.setFechaCreacion(rs.getTimestamp(2));
                acceso = true;
            }
            rs.close();
            cs.close();
        } catch ( SQLException ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return acceso;
        }
    }

    @Override
    public LinkedList<PlanCurricular> lista() {
        LinkedList<PlanCurricular> listPc = new LinkedList<PlanCurricular>();
        PlanCurricular pc;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_PlanCurricular_GetList()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                pc = new PlanCurricular(rs.getByte(1), rs.getString(2), rs.getTimestamp(3));
                listPc.add(pc);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return listPc;
        }
    }
    
}
