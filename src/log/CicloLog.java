/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import beans.Ciclo;
import beans.Momento;
import dao.CicloDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class CicloLog {
    CicloDao obj = new CicloDao();
    
    public void agregar(String descripcion, boolean parImpar, byte momentoId) {
        if (obj.agregar(new Ciclo(descripcion, parImpar, momentoId))) //true
            Utilitarios.mensaje("El ciclo " + descripcion + " fue agregado", 1);
        else
            Utilitarios.mensaje("El ciclo " + descripcion + " ya existe", 2);
    }
    
    public void actualizar (byte cicloId, String descripcion, boolean parImpar, byte momentoId) {
        if (obj.actualizar(new Ciclo(cicloId, descripcion, parImpar, momentoId)))
            Utilitarios.mensaje("El ciclo con código " + cicloId + " fue actualizado", 1);
        else
            Utilitarios.mensaje("El ciclo con código " + cicloId + " no pudo ser actualizado", 2);
    }
    
    public boolean buscar(Ciclo ciclo) {
        boolean band = false;
        if (obj.buscar(ciclo)) //encontrado
            band = true;
        else
            Utilitarios.mensaje("El ciclo no fue encontrado", 2);
        return band;
    }
    
    /**
     * Retorna todos los cursos para un JComboBox
     * @return 
     */
    public LinkedList<Ciclo> lista() {
        return obj.lista();
    }
    
    /**
     * Retorna una lista de cursos para una tabla
     * @return 
     */
    public DefaultTableModel listTable() {
        //cargar el el momentoLog
        MomentoLog objMn = new MomentoLog();
        Momento mn;
        
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<Ciclo> listCiclos = obj.lista();
        Iterator<Ciclo> it = listCiclos.iterator();
        Ciclo ciclo = null;
        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Semestre");
        modelo.addColumn("Momento");
        Object data[] = new Object[4];
        while (it.hasNext()) {
            ciclo = it.next();
            data[0] = ciclo.getCicloId();
            data[1] = ciclo.getDescripcion();
            if (ciclo.isParImpar()) //true = par
                data[2] = "Impar";
            else
                data[2] = "Par";
            mn = new Momento(ciclo.getMomentoId());
            objMn.buscar(mn);
            data[3] = mn.getNombre();
            modelo.addRow(data);
        }
        return modelo;
    }
}
