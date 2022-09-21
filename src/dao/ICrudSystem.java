package dao;

import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public interface ICrudSystem<T> {
    /**
     * Método que agrega un objeto a la base de datos
     * @param obj objeto a agregar depende de la clase q lo hereda
     * @return True o False confirmando si fue agregado o no a la database
     */
    boolean agregar(T obj);
    boolean actualizar(T obj);
    boolean eliminar(int codigo);
    boolean buscar(T obj);
    LinkedList<T> lista();
}