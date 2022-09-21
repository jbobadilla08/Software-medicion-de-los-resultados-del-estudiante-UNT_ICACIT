/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import beans.Sede;
import dao.SedeDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class SedeLog {
    SedeDao obj = new SedeDao();
    
    public boolean buscar(Sede sede) {
        boolean band = true;
        if (!obj.buscar(sede)) {
            Utilitarios.mensaje("La sede no fue encontrada", 2);
            band = false;
        }
        return band;
    }
    
     public LinkedList<Sede> lista() { //retorna todas las sedes registradas
        return obj.lista();        
    }
    
     /**
     * lista de sedes a las q pertenece un profesor
     * @param dni
     * @return 
     */
    public LinkedList<Sede> lista(String dni) { 
        return obj.lista(dni);
    }
    
}
