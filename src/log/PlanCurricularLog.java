/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import beans.PlanCurricular;
import dao.PlanCurricularDao;
import Utilidades.Utilitarios;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class PlanCurricularLog {
    PlanCurricularDao obj = new PlanCurricularDao();
    
    public void agregar(String planEstudios, Date fecha ) {
        if (obj.agregar(new PlanCurricular(planEstudios, fecha))) //is true
            Utilitarios.mensaje("El plan curricular "+planEstudios+" ha sido agregado.", 1);
        else 
            Utilitarios.mensaje("El plan curricular "+planEstudios+" ya existe.", 2);
    }
    
    public void actualizar(byte sedeId, String planEstudios) {
        if (obj.actualizar(new PlanCurricular(sedeId, planEstudios))) 
            Utilitarios.mensaje("El plan curricular "+planEstudios+" fue actualizado", 1);
        else
            Utilitarios.mensaje("El plan curricular "+planEstudios+" no pudo ser actualizado", 2);
    }
    
    public boolean buscar(PlanCurricular pc) {
        boolean band = true;
        if (!obj.buscar(pc)) {
            band =false;
            Utilitarios.mensaje("Administrador no encontrado!!!", 2);
        }
        return band;
    }
    
    public LinkedList<PlanCurricular> lista() { //retorna todas las sedes registradas
        return obj.lista();        
    }
    
    public DefaultTableModel listTable() { // estado = (activo o inactivo), permiso =  (superusuario o admin)
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<PlanCurricular> listPc = lista();
        Iterator<PlanCurricular> it = listPc.iterator();
        PlanCurricular aux = null;        
        modelo.addColumn("Código");
        modelo.addColumn("Plan Curricular");
        modelo.addColumn("Fecha Aplicación");
        Object data[] = new Object[3];
        while(it.hasNext()) {
            aux = it.next();
            data[0] = aux.getPlancId();
            data[1] = aux.getPlanEstudios();
            data[2] = aux.getFechaCreacion();
            modelo.addRow(data);
        }
        return modelo;        
    }
   
}
