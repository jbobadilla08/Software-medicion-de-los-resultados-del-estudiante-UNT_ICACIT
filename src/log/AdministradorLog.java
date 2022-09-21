package log;

import beans.Administrador;
import beans.Sede;
import dao.AdministradorDao;
import Utilidades.Utilitarios;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class AdministradorLog {
    AdministradorDao obj = new AdministradorDao();
    
    public void agregar(String dni, String nombre, String apellido, int telefono, Date fechaActual, String usuario, String password, boolean privilegios, boolean estado, byte sedeId) {
        byte resultado =obj.agregar(new Administrador(dni, nombre, apellido, telefono,  fechaActual, usuario, password, privilegios, estado, sedeId));
        if (resultado == 0)
            Utilitarios.mensaje("El administrador "+nombre+" "+apellido+" ha sido agregado.", 1);
        else if (resultado == 1)
            Utilitarios.mensaje("Se ha agregado una cuenta de usuario para el administrador "+nombre+" "+apellido+".", 1);
        else 
            Utilitarios.mensaje("El administrador "+nombre+" "+apellido+" ya existe.", 2);
    }
    
    public void actualizar(String dni, String nombre, String apellido, int telefono, Date fechaActual, String usuario, String password, boolean privilegios, boolean estado, byte sedeId) {
        if (obj.actualizar(new Administrador(dni, nombre, apellido, telefono, fechaActual, usuario, password, privilegios, estado, sedeId))) 
            Utilitarios.mensaje("El Administrador"+nombre+" "+apellido+" Actualizado", 1);
        else
            Utilitarios.mensaje("El Administrador "+nombre+" "+apellido+" no pudo ser actualizado", 2);
    }
    
    public void eliminar(String dni) {
        if (obj.eliminar(dni))
            Utilitarios.mensaje("El Administrador con Dni: "+dni+" fue dado de baja.", 1);
        else 
            Utilitarios.mensaje("El Administrador con Dni: "+dni+" ya está en la lista de bajas.", 0);
    }
    
    public boolean buscar(Administrador adm) {
        boolean band = true;
        if (!obj.buscar(adm)) {
            band = false;
            Utilitarios.mensaje("Administrador no encontrado!!!", 2);
        }
        return band;
    }
    
    public DefaultTableModel lista(boolean estado, boolean privilegio, byte sedeId) { // estado = (activo o inactivo), permiso =  (superusuario o admin)
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<Administrador> lista = obj.lista(privilegio, sedeId);
        SedeLog objSede = new SedeLog();
        Sede auxSede = null;
        Iterator<Administrador> it = lista.iterator();
        Administrador aux = null;        
        modelo.addColumn("Dni");
        modelo.addColumn("Nombres y Apellidos");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Usuario");
        modelo.addColumn("Password");
        modelo.addColumn("fecha");
        modelo.addColumn("privilegios");
        modelo.addColumn("sede");
        //p.last_update, a.privilegios, a.sede_id
        Object data[] = new Object[8];
        while(it.hasNext()) {
            aux = it.next();
            if (aux.isEstado() == estado) {
                //persona
                data[0] = aux.getDni();
                data[1] = aux.getNombre()+" "+aux.getApellido();
                data[2] = aux.getTelefono();
                data[3] = aux.getUsuario();
                data[4] = aux.getPassword();
                data[5] = aux.getLastUpdate();
                data[6] = aux.isPrivilegios() == true ? "Si" : "No";

                auxSede = new Sede(aux.getSedeId());
                objSede.buscar(auxSede);
                data[7] = auxSede.getDescripcion();
                modelo.addRow(data);
            }
        }
        return modelo;        
    }
    
    public boolean sesion(Administrador adm, int id) {
        boolean band = obj.sesion(adm, id);
        if (!band) { //True si tiene acceso, false caso contrario
            Utilitarios.mensaje("Usuario y/o contraseña Incorrecta!!!", 2);
        }
        return band;
    }
}
