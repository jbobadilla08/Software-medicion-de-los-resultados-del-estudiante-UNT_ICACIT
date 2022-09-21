package log;

import beans.Criterio;
import beans.Resultado;
import dao.CriterioDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class CriterioLog {
    CriterioDao obj = new CriterioDao();
    
    public void agregar(String codigo, String descripcion, int idResultado) {
        if (obj.agregar(new Criterio(codigo, descripcion, idResultado))) //true
            Utilitarios.mensaje("El criterio " + codigo + " fue agregado", 1);
        else
            Utilitarios.mensaje("El criterio " + codigo + " ya existe y esta vigente", 2);
    }
    
    public void actualizar (int criterioId, String codigo, String descripcion, int resultadoId) {
        if (obj.actualizar(new Criterio(criterioId, codigo, descripcion, resultadoId)))
            Utilitarios.mensaje("El criterio con ID " + criterioId + " fue actualizado", 1);
        else
            Utilitarios.mensaje("El criterio con ID " + criterioId + " no pudo ser actualizado", 2);
    }
    
    public void eliminar(int codigo) {
        if (obj.eliminar(codigo))
            Utilitarios.mensaje("El criterio fue dado de baja", 1);
        else
            Utilitarios.mensaje("El criterio ya está en la lista de bajas", 1);
    }
    
    public boolean buscar(Criterio c) {
        boolean band = false;
        if (obj.buscar(c)) //encontrado
            band = true;
        else
            Utilitarios.mensaje("El criterio no fue encontrado", 2);
        return band;
    }
    
    public LinkedList<Criterio> lista() {
        return obj.vigentes();
    }
    public DefaultTableModel lista(boolean vigente) {
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<Criterio> list = obj.lista();
        Iterator<Criterio> it = list.iterator();
        ResultadoLog objr = new ResultadoLog();
        Resultado r;
        Criterio c = null;
        modelo.addColumn("ID");
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Resultado");
        modelo.addColumn("Versión");
        Object data[] = new Object[5];
        while (it.hasNext()) {
            c = it.next();
            if (c.isVigente() == vigente) //separa los 
            {
                data[0] = c.getCriterioId();
                data[1] = c.getCodigo();
                data[2] = c.getDescripcion();
                r = new Resultado(c.getResultadoId());
                objr.buscar(r); //buscamos el resultado al q pertenece
                data[3] = r.getCodigo();
                data[4] = r.getVersion();
                modelo.addRow(data);
            }
        }
        return modelo;
    }
}
