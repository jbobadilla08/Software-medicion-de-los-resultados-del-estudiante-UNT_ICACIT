/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import beans.AnioMedicion;
import dao.AnioMedicionDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class AnioMedicionLog {
    AnioMedicionDao obj = new AnioMedicionDao();
    
    public void agregar(String anioElectivo, byte meta) {
        if (obj.agregar(new AnioMedicion(anioElectivo, meta))) {//is true
            Utilitarios.mensaje("El año electivo "+anioElectivo+" ha sido agregado.", 1);}
        else 
            Utilitarios.mensaje("El año electivo "+anioElectivo+" ya existe.", 2);
    }
    
    public void actualizar(int anioMedicioId, String anioElectivo, byte meta) {
        if (obj.actualizar(new AnioMedicion(anioMedicioId, anioElectivo, meta))) 
            Utilitarios.mensaje("El año electivo fue actualizado", 1);
        else
            Utilitarios.mensaje("El año electivo no pudo ser actualizado, ya existe", 2);
    }
    
    public boolean buscar(AnioMedicion am) {
        boolean band = true;
        if (!obj.buscar(am)) {
            band =false;
            Utilitarios.mensaje("Año electivo no encontrado!!!", 2);
        }
        return band;
    }
    
    public LinkedList<AnioMedicion> lista() { //retorna todas las sedes registradas
        return obj.lista();        
    }
    
    public DefaultTableModel listTable() { // estado = (activo o inactivo), permiso =  (superusuario o admin)
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<AnioMedicion> listPc = lista();
        Iterator<AnioMedicion> it = listPc.iterator();
        AnioMedicion aux = null;        
        modelo.addColumn("Código");
        modelo.addColumn("Año Electivo");
        modelo.addColumn("Meta");
        Object data[] = new Object[3];
        while(it.hasNext()) {
            aux = it.next();
            data[0] = aux.getAnioMedicionId();
            data[1] = aux.getAnioElectivo();
            data[2] = aux.getMeta();
            modelo.addRow(data);
        }
        return modelo;        
    }
    
     
    public void agregarConfig(byte planc, int anioMedicionId) {
        if (obj.agregarConfig(planc, anioMedicionId)) //is true
            Utilitarios.mensaje("El plan curricular y el año de medición han sido relacionados.", 1);
        else 
            Utilitarios.mensaje("La relación ya existe.", 2);
    }
    
    
    public void actualizarConfig(byte idPlanAnt, int idAnioAnt, byte idPlan, int idAnio) {
        if (obj.actualizarConfig(idPlanAnt, idAnioAnt, idPlan, idAnio)) 
            Utilitarios.mensaje("Relación exitosa", 1);
        else
            Utilitarios.mensaje("La relación ya existe o no fue posible relacionar", 2);
    }
    
    public boolean buscarConfig(byte idPlan, int idAnio) {
        boolean band = true;
        if (!obj.buscarConfig(idPlan, idAnio)) {
            band = false;
            Utilitarios.mensaje("La relación entre el plan y el año de medición elegidos, no existe", 2);
        }
        return band;
    }
    
    public DefaultTableModel listTableConfig() { 
        return obj.listTableConfig();
    }
}

