package dao;

import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public interface IPersona<T> {
    byte agregar(T obj);
    boolean actualizar(T obj);
    boolean eliminar(String dni);
    boolean buscar(T obj);
    boolean sesion(T obj, int id);
    LinkedList<T> lista(boolean privilegio, byte idSede);
}
