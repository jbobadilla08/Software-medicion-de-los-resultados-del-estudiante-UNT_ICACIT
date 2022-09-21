/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import beans.Criterio;
import beans.DefinicionHasNivel;
import beans.Momento;
import beans.NivelLogro;
import dao.DefinicionHasNivelDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class DefinicionHasNivelLog {
    DefinicionHasNivelDao obj = new DefinicionHasNivelDao();
    
    public void agregar(int criterioId, int nivelId, String descripcion, byte momentoId) {
        if (obj.agregar(new DefinicionHasNivel(criterioId, nivelId, descripcion, momentoId))) //true
            Utilitarios.mensaje("La rúbrica analítica fue agregada", 1);
        else
            Utilitarios.mensaje("La rúbrica analítica ya existe", 2);
    }
    
    public void actualizar (int codigo, int criterioId, int nivelId, String descripcion, byte momentoId) {
        if (obj.actualizar(new DefinicionHasNivel(codigo, criterioId, nivelId, descripcion, momentoId)))
            Utilitarios.mensaje("La rúbrica analítica fue actualizada", 1);
        else
            Utilitarios.mensaje("La rúbrica analítica no pudo ser actualizada", 2);
    }
    
    public boolean buscar(DefinicionHasNivel dhn) {
        boolean band = false;
        if (obj.buscar(dhn)) //encontrado
            band = true;
        else
            Utilitarios.mensaje("La rúbrica analítica no fue encontrada", 2);
        return band;
    }
    
    public LinkedList<DefinicionHasNivel> lista() {
        return obj.lista();
    }
    
    public DefaultTableModel listTable() {
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<DefinicionHasNivel> listDhn = obj.lista();
        Iterator<DefinicionHasNivel> it = listDhn.iterator();
        DefinicionHasNivel dhn = null;
        CriterioLog objCr = new CriterioLog();
        Criterio cr = null;
        NivelLogroLog objNl = new NivelLogroLog();
        NivelLogro nl = null;
        MomentoLog objMn = new MomentoLog();
        Momento mn = null;
        modelo.addColumn("ID");
        modelo.addColumn("Criterio");
        modelo.addColumn("Nivel Logro");
        modelo.addColumn("Descripción");
        modelo.addColumn("Momentos");
        Object data[] = new Object[5];
        while (it.hasNext()) {
            dhn = it.next();
            data[0] = dhn.getCodigo();
            cr = new Criterio(dhn.getCriterioId());
            objCr.buscar(cr);
            data[1] = cr.getCodigo();
            nl = new NivelLogro(dhn.getNivelId());
            objNl.buscar(nl);
            data[2] = nl.getNombre();
            data[3] = dhn.getDescripcion();
            mn = new Momento(dhn.getMomentoId());
            objMn.buscar(mn);
            data[4] = mn.getNombre();
            modelo.addRow(data);
        }
        return modelo;
    }
}
