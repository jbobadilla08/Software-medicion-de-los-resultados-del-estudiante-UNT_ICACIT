package log;

import beans.Resultado;
import dao.ResultadosDao;
import Utilidades.Utilitarios;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class ResultadoLog {
    ResultadosDao obj = new ResultadosDao();
    
    public void agregar(String codigo, String titulo, String descripcion, Date fecha, boolean vigente, String version) {
        if (obj.agregar(new Resultado(codigo, titulo, descripcion, version, vigente, fecha)))
            Utilitarios.mensaje("El Resultado "+codigo+" fue agregado", 1);
        else
            Utilitarios.mensaje("El Resultado "+codigo+" no pudo ser agregado", 2);
    }
    
    public void actualizar(int resultadoId, String codigo, String titulo, String descripcion, Date fechaCreacion, boolean vigente, String version) {
        if (obj.actualizar(new Resultado(resultadoId, codigo, titulo, descripcion, fechaCreacion, vigente, version)))
            Utilitarios.mensaje("El resultado "+codigo+" fue actualizado", 1);
        else
            Utilitarios.mensaje("El resultado "+codigo+" no pudo actualizarse", 2);
    }
    
    public void eliminar(int codigo) {
        if (obj.eliminar(codigo))
            Utilitarios.mensaje("El resultado fue dado de baja", 1);
        else
            Utilitarios.mensaje("El resultado ya está en la lista de bajas", 1);
    }
    
    public boolean buscar(Resultado res) {
        boolean band = true;
        if (!obj.buscar(res)) {
            band = false;
            Utilitarios.mensaje("El resultado no fue encontrado", 2);
        }
        return band;
    }
    
    public DefaultTableModel lista(boolean vigente) {
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<Resultado> list = obj.lista();
        Iterator<Resultado> it = list.iterator();
        Resultado res = null;
        modelo.addColumn("ID");
        modelo.addColumn("Código");
        modelo.addColumn("Título");
        modelo.addColumn("Descripción");
        modelo.addColumn("Fecha Creación");
//        modelo.addColumn("Vigencia");
        modelo.addColumn("Versión");
        Object data[] = new Object[6];
        while (it.hasNext()) {
            res = it.next();
            if (res.isVigente() == vigente)
            {
                data[0] = res.getResultadoId();
                data[1] = res.getCodigo();
                data[2] = res.getTitulo();
                data[3] = res.getDescripcion();
                data[4] = res.getFechaCreacion();
                data[5] = res.getVersion();
                modelo.addRow(data);
            }
        }
        return modelo;
    }
    
    public LinkedList<Resultado> lista() {
        LinkedList<Resultado> activos = new LinkedList<>();
        for (Resultado cr : obj.lista()) {
            if (cr.isVigente())
                activos.add(cr);
        }
        return activos;
    }
    
}
