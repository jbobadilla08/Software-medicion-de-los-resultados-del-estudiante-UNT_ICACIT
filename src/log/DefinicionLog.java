
package log;

import beans.Criterio;
import beans.Definicion;
import beans.DefinicionHasMomento;
import beans.Momento;
import dao.DefinicionDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class DefinicionLog {
    DefinicionDao obj = new DefinicionDao();
    
    public void agregar(Definicion def) {
        if (obj.agregar(def)) {//is true
            Utilitarios.mensaje("La rúbrica holística ha sido agregado.", 1);}
        else 
            Utilitarios.mensaje("La rúbrica holística ya existe.", 2);
    }
    
    public void actualizar(int definicionId, String descripcion, int criterioId) {
        if (obj.actualizar(new Definicion(definicionId, descripcion, criterioId))) 
            Utilitarios.mensaje("La rúbrica holística fue actualizada", 1);
        else
            Utilitarios.mensaje("La rúbrica holística no pudo ser actualizada, ya existe", 2);
    }
    
    public boolean buscar(Definicion df) {
        boolean band = true;
        if (!obj.buscar(df)) {
            band =false;
            Utilitarios.mensaje("Rúbrica holística no encontrada!!!", 2);
        }
        return band;
    }
    
    public LinkedList<Definicion> lista() { //retorna todas las sedes registradas
        return obj.lista();        
    }
    
    public Momento moment(int definicionId) {
        return obj.momentGet(definicionId);
    }
    
    public DefaultTableModel listTable() { // estado = (activo o inactivo), permiso =  (superusuario o admin)
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<Definicion> listDf = lista();
        Momento momento;
        Iterator<Definicion> it = listDf.iterator();
        Definicion aux = null;
        Criterio criterio;
        CriterioLog objCr = new CriterioLog();
//        Momento auxMoment = null;
        modelo.addColumn("ID");
        modelo.addColumn("Criterio");
        modelo.addColumn("Descripción");
        modelo.addColumn("Momentos");
        Object data[] = new Object[4];
        while(it.hasNext()) {
            aux = it.next();
            data[0] = aux.getDefinicionId();
            criterio = new Criterio(aux.getCriterioId());
            objCr.buscar(criterio);
            data[1] = criterio.getCodigo();
            data[2] = aux.getDescripcion();
            momento = obj.momentGet(aux.getDefinicionId());
            data[3] = momento.getNombre();
            modelo.addRow(data);
        }
        return modelo;
    }
    
    
    
}
     
//    public void agregarConfig(byte planc, int anioMedicionId) {
//        if (obj.agregarConfig(planc, anioMedicionId)) //is true
//            Utilitarios.mensaje("El plan curricular y el año de medición han sido relacionados.", 1);
//        else 
//            Utilitarios.mensaje("La relación ya existe.", 2);
//    }
//    
//    
//    public void actualizarConfig(byte idPlanAnt, int idAnioAnt, byte idPlan, int idAnio) {
//        if (obj.actualizarConfig(idPlanAnt, idAnioAnt, idPlan, idAnio)) 
//            Utilitarios.mensaje("Relación exitosa", 1);
//        else
//            Utilitarios.mensaje("La relación ya existe o no fue posible relacionar", 2);
//    }
//    
//    public boolean buscarConfig(byte idPlan, int idAnio) {
//        boolean band = true;
//        if (!obj.buscarConfig(idPlan, idAnio)) {
//            band = false;
//            Utilitarios.mensaje("La relación entre el plan y el año de medición elegidos, no existe", 2);
//        }
//        return band;
//    }
//    
//    public DefaultTableModel listTableConfig() { 
//        return obj.listTableConfig();
//    }

