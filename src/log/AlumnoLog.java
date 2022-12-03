/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import Utilidades.Utilitarios;
import beans.Alumno;
import dao.AlumnoDao;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alfie
 */
public class AlumnoLog {
     AlumnoDao obj = new AlumnoDao();
    
    public void agregar(String codMatricula, String nombre, String apellido) {
        if (obj.agregar(new Alumno(codMatricula, nombre, apellido))) //true
            Utilitarios.mensaje("El alumno " + nombre + " " + apellido + " fue agregado", 1);
        else
            Utilitarios.mensaje("El alumno " + nombre + " " + apellido + " ya existe", 2);
    }
    
    public void agregar(LinkedList<Alumno> lista) {
        int contAlumnos = 0, totalAlumnos = 0;
        Alumno al = null;
        Iterator<Alumno> it = lista.iterator();
        while (it.hasNext()) {
            totalAlumnos +=1;
            al  = it.next();
            if (obj.agregar(al)) //true = agregado
                contAlumnos++;
        }
        int existentes = totalAlumnos - contAlumnos;
        String mensaje = "Se agregarón " + contAlumnos + " de " + totalAlumnos + " alumnos, " + existentes;
        if (existentes == 1)
            mensaje += " alumno ya existe";
        else
            mensaje += " alumnos ya existen";
        Utilitarios.mensaje(mensaje, 1);
    }
    
    public void actualizar (String codMatricula, String nombre, String apellido) {
        if (obj.actualizar(new Alumno(codMatricula, nombre, apellido)))
            Utilitarios.mensaje("El alumno " + nombre + " " + apellido + " fuue actualizado", 1);
        else
            Utilitarios.mensaje("El alumno " + nombre + " " + apellido + " fuue actualizado", 2);
    }
    
    public boolean buscar(Alumno a) {
        boolean band = false;
        if (obj.buscar(a)) //encontrado
            band = true;
        else
            Utilitarios.mensaje("El alumno no fue encontrado", 2);
        return band;
    }
    
    /**
     * lista todos los almunos para un jcombobox
     * @return 
     */
    public LinkedList<Alumno> lista() {
        return obj.lista();
    }
    
    /**
     * retorna toda la lista de alumnos modo tabla
     * @param vigente
     * @return 
     */
    public DefaultTableModel lista(String apellido) {
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<Alumno> list;
        if (apellido.equals("")) //carga todos los alumnos
            list = obj.lista();
        else //carga los alumnos segun el apellido
            list = obj.lista(apellido);
        Iterator<Alumno> it = list.iterator();
        Alumno alumno;
        modelo.addColumn("Cod. Matricula");
        modelo.addColumn("Nombres ");
        modelo.addColumn("Apellidos");
        Object data[] = new Object[3];
        while (it.hasNext()) {
            alumno = it.next();
            data[0] = alumno.getCodMatricula();
            data[1] = alumno.getNombre();
            data[2] = alumno.getApellido();
            modelo.addRow(data);
        }
        return modelo;
    }
}
