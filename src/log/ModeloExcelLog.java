package log;

import Utilidades.Utilitarios;
import beans.Alumno;
import beans.ModeloExcelDao;
import java.io.File;
import java.util.LinkedList;

/**
 *
 * @author alfie
 */
public class ModeloExcelLog {
    ModeloExcelDao obj = new ModeloExcelDao();
    
    public boolean importar(File file, LinkedList<Alumno> lista) {
        boolean band = false;
        if (obj.importar(file, lista)) { //true
            Utilitarios.mensaje("Los datos del excel fueron importados con exito!", 1);
            band = true;
        }            
        else
            Utilitarios.mensaje("No se realizó la importación con exito!", 1);
        return band;
    }
    
}
