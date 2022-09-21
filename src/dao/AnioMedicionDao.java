package dao;

import Utilidades.Utilitarios;
import beans.AnioMedicion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class AnioMedicionDao implements ICrudSystem<AnioMedicion>{
    Conexion conn = new Conexion();

    @Override
    public boolean agregar(AnioMedicion obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_AnioMedicion_Add(?,?,?)}");
            cs.setString(1, obj.getAnioElectivo());
            cs.setByte(2, obj.getMeta());
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
    public boolean actualizar(AnioMedicion obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_AnioMedicion_Update(?,?,?,?)}");
            cs.setInt(1, obj.getAnioMedicionId());
            cs.setString(2, obj.getAnioElectivo());
            cs.setByte(3, obj.getMeta());
            cs.registerOutParameter(4, Types.INTEGER);
            cs.executeUpdate();
            if (cs.getInt(4) == 1) // update exitoso
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
    public boolean buscar(AnioMedicion obj) {
        boolean acceso = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_AnioMedicion_Get(?)}");
            cs.setInt(1, obj.getAnioMedicionId());
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setAnioElectivo(rs.getString(1));
                obj.setMeta(rs.getByte(2));
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
    public LinkedList<AnioMedicion> lista() {
        LinkedList<AnioMedicion> listAm = new LinkedList<AnioMedicion>();
        AnioMedicion am;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_AnioMedicion_GetList()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                am = new AnioMedicion(rs.getInt(1), rs.getString(2), rs.getByte(3));
                listAm.add(am);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return listAm;
        }
    }
    
    /**
     * Método para agregar la relacion entre la tabla planCurricular y la tabla AnioMedicion
     * @param planc parámetro que pertenece a planCurricular
     * @param anioMedicionId parámetro que pertenece a AnioMedicion
     * @return retorna una bandera de estado si fue agregado o no
     */
    public boolean agregarConfig(byte planc, int anioMedicionId) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_AnioMedicion_AddConfig(?,?,?)}");
            cs.setByte(1, planc);
            cs.setInt(2, anioMedicionId);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.executeUpdate();
            if (cs.getInt(3) == 1) // 1 si se guardo        
                band = true;
            cs.close();
        }catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }
    
    public LinkedList<AnioMedicion> listaConfig(int a) {
        LinkedList<AnioMedicion> listAm = new LinkedList<AnioMedicion>();
        AnioMedicion am;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_AnioMedicion_GetList()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                am = new AnioMedicion(rs.getInt(1), rs.getString(2), rs.getByte(3));
                listAm.add(am);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return listAm;
        }
    }
    
    public DefaultTableModel listTableConfig() { 
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Plan Curricular");
        modelo.addColumn("Año de Medición");
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{call App_AnioMedicion_GetListConfig()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                Object data[] = {rs.getString(1), rs.getString(2)};
                modelo.addRow(data);
            }
            rs.close();
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return modelo;   
        }
    }
    
     public boolean buscarConfig(byte idPlan, int idAnio) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_PlanAnioMedicion_Get(?,?)}");
            cs.setByte(1, idPlan);
            cs.setInt(2, idAnio);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                if (rs.getByte(1) == idPlan && rs.getInt(2) == idAnio)
                    band = true;
            }
            cs.close();
        } catch ( Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close();
            return band;
        }
    }
     
    public boolean actualizarConfig(byte idPlanAnt, int idAnioAnt, byte idPlan, int idAnio) {
        boolean band = false;
        try {
            CallableStatement cs = conn.getConnection().prepareCall("{CALL App_PlanAnioMedicion_Update(?,?,?,?,?)}");
            cs.setByte(1, idPlanAnt);
            cs.setInt(2, idAnioAnt);
            cs.setByte(3, idPlan);
            cs.setInt(4, idAnio);
            cs.registerOutParameter(5, Types.INTEGER);
            cs.executeUpdate();
            if (cs.getInt(5) == 1) // update exitoso
                band = true;
            cs.close();
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            conn.close(); //cerramos conexion
            return band;
        }
    }
}
