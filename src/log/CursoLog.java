/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import beans.Ciclo;
import beans.Curso;
import beans.PlanCurricular;
import dao.CursoDao;
import Utilidades.Utilitarios;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author alfie
 */
public class CursoLog {
    CursoDao obj = new CursoDao();
    
    public void agregar(String codigo, String descripcion, byte creditos, byte planCurricular, byte cicloId) {
        if (obj.agregar(new Curso(codigo, descripcion, creditos, planCurricular, cicloId))) //true
            Utilitarios.mensaje("El curso con código " + codigo + " fue agregado", 1);
        else
            Utilitarios.mensaje("El curso con código " + codigo + " ya existe", 2);
    }
    
    public void actualizar (int cursoId, String codigo, String descripcion, byte creditos, byte planCurricular, byte cicloId) {
        if (obj.actualizar(new Curso(cursoId, codigo, descripcion, creditos, planCurricular, cicloId)))
            Utilitarios.mensaje("El curso con código " + codigo + " fue actualizado", 1);
        else
            Utilitarios.mensaje("El curso con código " + codigo + " no pudo ser actualizado", 2);
    }
    
    public boolean buscar(Curso cu) {
        boolean band = false;
        if (obj.buscar(cu)) //encontrado
            band = true;
        else
            Utilitarios.mensaje("El curso no fue encontrado", 2);
        return band;
    }
    
    /**
     * Retorna todos los cursos para un JComboBox
     * @return 
     */
    public LinkedList<Curso> lista() {
        return obj.lista();
    }
    
    /**
     * Retorna una lista de cursos para una tabla
     * @return 
     */
    public DefaultTableModel listTable(String descripcion) {
        PlanCurricularLog objPl = new PlanCurricularLog();
        CicloLog objCiclo = new CicloLog();
        PlanCurricular pl;
        Ciclo cl;
        DefaultTableModel modelo = new DefaultTableModel();
        LinkedList<Curso> listCursos;
        if(descripcion.equals("")) //
            listCursos = obj.lista();
        else // buscar un curso
            listCursos = obj.lista(descripcion);
            
        Iterator<Curso> it = listCursos.iterator();
        Curso curso = null;
        modelo.addColumn("ID");
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Creditos");
        modelo.addColumn("Plan Curricular");
        modelo.addColumn("Ciclo");
        Object data[] = new Object[6];
        while (it.hasNext()) {
            curso = it.next();
            data[0] = curso.getCursoId(); //id curso
            data[1] = curso.getCodigo(); // codigo
            data[2] = curso.getDescripcion(); // nombre
            data[3] = curso.getCreditos(); //numero de creditos
            pl = new PlanCurricular(curso.getPlanCurricular()); // id de planCurricular
            objPl.buscar(pl);
            data[4] = pl.getPlanEstudios(); // nombre del plan
            cl = new Ciclo(curso.getCicloId()); //id del ciclo
            objCiclo.buscar(cl);
            data[5] = cl.getDescripcion(); //nombre del ciclo
            modelo.addRow(data);
        }
        return modelo;
    }
    
    /**
     * lista los cursos que pertenecen a un plan y un ciclo en especifico
     */
    public LinkedList<Curso> lista(String plan, String ciclo) {
        return obj.lista(plan, ciclo);
    }
}
