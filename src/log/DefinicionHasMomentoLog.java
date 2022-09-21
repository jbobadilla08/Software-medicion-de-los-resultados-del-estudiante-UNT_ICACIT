/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import beans.DefinicionHasMomento;
import dao.DefinicionHasMomentoDao;
import Utilidades.Utilitarios;

/**
 *
 * @author alfie
 */
public class DefinicionHasMomentoLog {
    DefinicionHasMomentoDao obj = new DefinicionHasMomentoDao();
    
    public void agregar(int definicionId, byte momentoId) {
        if (obj.agregar(new DefinicionHasMomento(definicionId, momentoId))) //true
            Utilitarios.mensaje("La relación entre la rúbrica y el momento fue agregado", 1);
        else
            Utilitarios.mensaje("La relación entre la rúbrica y el momento ya existe", 2);
    }
    
    public void actualizar (int definicionId, byte momentoId) {
        if (obj.actualizar(new DefinicionHasMomento(definicionId, momentoId)))
            Utilitarios.mensaje("La relación entre la rúbrica y el momento fue actualizado", 1);
        else
            Utilitarios.mensaje("La relación entre la rúbrica y el momento no pudo ser actualizado", 2);
    }
    
    
    public boolean buscar(DefinicionHasMomento dhm) {
        boolean band = false;
        if (obj.buscar(dhm)) //encontrado
            band = true;
        else
            Utilitarios.mensaje("La relación entre la rúbrica y el momento no fue encontrada", 2);
        return band;
    }
    
}
