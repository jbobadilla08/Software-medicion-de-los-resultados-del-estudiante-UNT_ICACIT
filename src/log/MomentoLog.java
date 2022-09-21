
package log;

import beans.Momento;
import dao.MomentoDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class MomentoLog {
    MomentoDao obj = new MomentoDao();
    
    public void agregar(String nombre) {
        if (obj.agregar(new  Momento(nombre))) //true
            Utilitarios.mensaje("El momento " + nombre + " fue agregado", 1);
        else
            Utilitarios.mensaje("El momento " + nombre + " ya existe y esta vigente", 2);
    }
    
    public void actualizar (byte momentoId, String nombre, boolean vigente) {
        if (obj.actualizar(new Momento(momentoId, nombre, vigente)))
            Utilitarios.mensaje("El momento  " + nombre + " fue actualizado", 1);
        else
            Utilitarios.mensaje("El momento " + nombre + " no pudo ser actualizado", 2);
    }
    
    public void eliminar(int codigo) {
        if (obj.eliminar(codigo))
            Utilitarios.mensaje("El momento fue dado de baja", 1);
        else
            Utilitarios.mensaje("El momento ya está en la lista de bajas", 1);
    }
    
    public boolean buscar(Momento m) {
        boolean band = false;
        if (obj.buscar(m)) //encontrado
            band = true;
        else
            Utilitarios.mensaje("El momento no fue encontrado", 2);
        return band;
    }
    
    public LinkedList<Momento> lista() {
        return obj.vigentes();
    }
    public DefaultTableModel listTable(boolean vigente) {
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<Momento> list = obj.lista();
        Iterator<Momento> it = list.iterator();
        Momento m = null;
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Vigente");
        Object data[] = new Object[3];
        while (it.hasNext()) {
            m = it.next();
            if (m.isVigente() == vigente) //separa los 
            {
                data[0] = m.getMomentoId();
                data[1] = m.getNombre();
                data[2] = (m.isVigente() == true ? "Si" : "No");
                modelo.addRow(data);
            }
        }
        return modelo;
    }
}
