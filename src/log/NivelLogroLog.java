package log;

import beans.NivelLogro;
import dao.NivelLogroDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class NivelLogroLog {
    NivelLogroDao obj = new NivelLogroDao();
    
    public void agregar(String nombre, String abreviatura, byte min, byte max) {
        if (obj.agregar(new NivelLogro(nombre, abreviatura, min, max))) //true
            Utilitarios.mensaje("El nivel de logro " + nombre + " fue agregado", 1);
        else
            Utilitarios.mensaje("El nivel de logro " + nombre + " ya existe", 2);
    }
    
    public void actualizar (int nivelId, String nombre, String abreviatura, byte min, byte max) {
        if (obj.actualizar(new NivelLogro(nivelId, nombre, abreviatura, min, max)))
            Utilitarios.mensaje("El nivel de logro con ID " + nivelId + " fue actualizado", 1);
        else
            Utilitarios.mensaje("El nivel de logro con ID " + nivelId + " no pudo ser actualizado", 2);
    }
    
    public boolean buscar(NivelLogro nl) {
        boolean band = false;
        if (obj.buscar(nl)) //encontrado
            band = true;
        else
            Utilitarios.mensaje("El nivel de logro no fue encontrado", 2);
        return band;
    }
    
    public LinkedList<NivelLogro> lista() {
        return obj.lista();
    }
    
    public DefaultTableModel listTable() {
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<NivelLogro> listNl = obj.lista();
        Iterator<NivelLogro> it = listNl.iterator();
        NivelLogro nl = null;
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Abreviatura");
        modelo.addColumn("Valor Mínimo");
        modelo.addColumn("Valor máximo");
        Object data[] = new Object[5];
        while (it.hasNext()) {
            nl = it.next();
            data[0] = nl.getNivelId();
            data[1] = nl.getNombre();
            data[2] = nl.getAbreviatura();
            data[3] = "> " + nl.getValMin();
            data[4] = "<=" + nl.getValMax();
            modelo.addRow(data);
        }
        return modelo;
    }
}
