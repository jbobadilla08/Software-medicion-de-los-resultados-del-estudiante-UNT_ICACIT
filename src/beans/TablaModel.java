package beans;

import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author alfie
 */
public class TablaModel extends AbstractTableModel{
    /** Lista de encabezados de la tabla*/
    private LinkedList<String> encabezado = new LinkedList<String>();
    /** Lista con los datos de la tabla */
    private LinkedList datos = new LinkedList();
    
    /** Lista de suscriptores. El JTable será un suscriptor de este modelo de
     * datos */
    //private LinkedList listeners = new LinkedList();
       
    public TablaModel() {
    }
    public TablaModel(LinkedList encabezado) {
        this.encabezado = encabezado;
    }

    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return encabezado.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
