package log;

import beans.Profesor;
import beans.Sede;
import dao.ProfesorDao;
import Utilidades.Utilitarios;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class ProfesorLog {
    ProfesorDao obj = new ProfesorDao();
    
    /**
     * Agrega un profesora a la base
     */
    public void agregar(String dni, String nombre, String apellido, int telefono, Date lastUpdate, String correo, String usuario, String password, boolean estado) {
        byte resultado = obj.agregar(new Profesor(dni, nombre, apellido, telefono, lastUpdate, correo, usuario, password, estado));
        if (resultado == 0)
            Utilitarios.mensaje("El profesor "+nombre+" "+apellido+" ha sido agregado.", 1);
        else if (resultado == 1)
            Utilitarios.mensaje("Se ha agregado una cuenta de usuario para el profesor "+nombre+" "+apellido+".", 1);
        else 
            Utilitarios.mensaje("El profesor "+nombre+" "+apellido+" ya existe.", 2);
    }
    
    public void actualizar(String dni, String nombre, String apellido, int telefono, Date lastUpdate, String correo, String usuario, String password, boolean estado) {
        if (obj.actualizar(new Profesor(dni, nombre, apellido, telefono, lastUpdate, correo, usuario, password, estado))) 
            Utilitarios.mensaje("El profesor "+nombre+" "+apellido+" ha sido Actualizado", 1);
        else
            Utilitarios.mensaje("El profesor "+nombre+" "+apellido+" no pudo ser actualizado", 2);
    }
    
    public void eliminar(String dni) {
        if (obj.eliminar(dni))
            Utilitarios.mensaje("El profesor con Dni: "+dni+" fue dado de baja.", 1);
        else 
            Utilitarios.mensaje("El profesor con Dni: "+dni+" ya está en la lista de bajas.", 0);
    }
    
    public boolean buscar(Profesor prof) {
        boolean band = true;
        if (!obj.buscar(prof)) {
            band = false;
            Utilitarios.mensaje("El profesor no se ha encontrado!!!", 2);
        }
        return band;
    }
    
    public LinkedList<Profesor> lista(){
        return obj.lista();
    }
    
    /**
     * Retorna una lista de profesores
     * @param estado
     * @param privilegios
     * @param sedeId
     * @return 
     */
    public DefaultTableModel listTable(boolean estado, boolean privilegios, byte sedeId) { // estado = (activo o inactivo), permiso =  (superusuario o admin)
        DefaultTableModel modelo = new DefaultTableModel();
        //profesor
        LinkedList<Profesor> lista = obj.lista();
        Iterator<Profesor> it = lista.iterator();
        Profesor aux = null; 
        //sede
        LinkedList<Sede> listSedes;
        Iterator<Sede> itSede;
        Sede sedesProf = null;
        String sedesString;
        modelo.addColumn("Dni");
        modelo.addColumn("Nombres y Apellidos");
        modelo.addColumn("Correo");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Usuario");
        modelo.addColumn("Password");
        modelo.addColumn("fecha");
        modelo.addColumn("sede");
        Object data[] = new Object[8];
        while(it.hasNext()) {
            aux = it.next();
            if (aux.isEstado() == estado) {
                //persona
                data[0] = aux.getDni();
                data[1] = aux.getNombre()+" "+aux.getApellido();
                data[2] = aux.getCorreo(); 
                data[3] = aux.getTelefono();  
                data[4] = aux.getUsuario();  
                data[5] = aux.getPassword();  
                data[6] = aux.getLastUpdate();
                //sede
                listSedes = new SedeLog().lista(aux.getDni());
                itSede = listSedes.iterator();
                sedesString = "";
                while (itSede.hasNext()) {
                    sedesProf = itSede.next();
                    if (!privilegios) { //administrador
                        if (sedesProf.getSedeId() == sedeId) {  //&& !sedesProf.getDescripcion().equals(""))
                            if (sedesString.equals("*"))
                                sedesString = "";
                            sedesString += sedesProf.getDescripcion();
                            break;
                        } else 
                            sedesString = "*";
                            
                    }else if(privilegios) { //superusuario
                        if (!sedesString.equals(""))
                            sedesString += " | ";
                        sedesString += sedesProf.getDescripcion();
                    }
                }
                data[7] = sedesString;
                if (!sedesString.equals("*"))
                    modelo.addRow(data);
            }
        }
        return modelo;        
    }
    
    public boolean sesion(Profesor prof, int id) {
        boolean band = obj.sesion(prof, id);
        if (!band) { //True si tiene acceso, false caso contrario
            Utilitarios.mensaje("Usuario y/o contraseña Incorrecta!!!", 2);
        }
        return band;
    }
    
    /**
     * relaciona un profesor con sede
     * @param dni
     * @param sede 
     */
    public void agregar(String dni, String nombre, byte sede) {
        if (obj.agregar(dni, sede))         
            Utilitarios.mensaje("El profesor "+nombre+" se ha agregó a la sede.", 1);
        else 
            Utilitarios.mensaje("El profesor "+nombre+" ya existe en la sede.", 2);
            
    }
    
    /**
     * Relaciona un profesor con curso
     * @param dni
     * @param sede 
     */
    public void agregar(String dni, int curso, byte sede, String name) {
        if (!obj.agregar(dni, curso, sede))
            Utilitarios.mensaje("El curso "+ name +" ha sido agregado anteriormente a esta sede.", 2);
    }
    
    /**
     * Retorno de una lista de profesores con sus cursos
     */
    public DefaultTableModel listTableCursos( boolean privilegios, byte sedeId) { // estado = (activo o inactivo), permiso =  (superusuario o admin)
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Dni");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Curso");
        modelo.addColumn("Sede");
        Iterator<Object[]> it = obj.listaPhc(privilegios, sedeId).iterator();
        Object[] fila = null;
        while (it.hasNext()) {
            fila = it.next();
            modelo.addRow(fila);
        }
        
        return modelo;        
    }
    
}
