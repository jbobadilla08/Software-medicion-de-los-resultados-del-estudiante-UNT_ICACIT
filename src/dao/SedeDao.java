package dao;

import Utilidades.Utilitarios;
import beans.Sede;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class SedeDao implements ICrudSystem<Sede>{
    Connection conn = new Conexion().getConnection();
    
    @Override
    public boolean agregar(Sede obj) { return false; }

    @Override
    public boolean actualizar(Sede obj) {return false; }
    
    @Override
    public  boolean eliminar(int codigo){ return false;}
    
    @Override
    public boolean buscar(Sede obj) {
        boolean band = false;
        try {
            CallableStatement cs = conn.prepareCall("{CALL App_Sede_Get(?)}");
            cs.setByte(1, obj.getSedeId()); //codigo de la sede a buscar
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                obj.setDescripcion(rs.getString(1));
                band = true;
            }
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            return band;
        }
    }
     

    @Override
    public LinkedList<Sede> lista() { 
        LinkedList<Sede> listSede = new LinkedList<Sede>();
        Sede s;
        try {
            CallableStatement cs = conn.prepareCall("{call App_Sede_GetList()}");
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                s = new Sede(rs.getByte(1), rs.getString(2));
                listSede.add(s);
            }
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            return listSede;
        }
    }
    
    /**
     * lista de sedes a las q pertenece un profesor
     * @param dni
     * @return 
     */
    public LinkedList<Sede> lista(String dni) { 
        LinkedList<Sede> listSede = new LinkedList<Sede>();
        Sede s;
        try {
            CallableStatement cs = conn.prepareCall("{call App_Prof_Sede_GetList(?)}");
            cs.setString(1, dni);
            ResultSet rs = cs.executeQuery();
            while(rs.next()) {
                s = new Sede(rs.getByte(1), rs.getString(2));
                listSede.add(s);
            }
        } catch (Exception ex) {
            Utilitarios.mensaje(ex.toString(), 0);
        } finally {
            return listSede;
        }
    }
}
