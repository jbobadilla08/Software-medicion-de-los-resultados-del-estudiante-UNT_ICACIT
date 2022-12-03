package beans;

import Utilidades.Utilitarios;
import beans.Alumno;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author alfie
 */
public class ModeloExcelDao {
    Workbook wb;
    
    public boolean importar(File file, LinkedList<Alumno> listAlumnos) {
        boolean band = false;        
        try {
            wb = WorkbookFactory.create(new FileInputStream(file));
            Sheet hola = wb.getSheetAt(0); //carga la primera hoja
            Iterator itFila = hola.rowIterator(); //recorre las filas del excel
            int indFila = -1; //control de la fila
            while (itFila.hasNext()) { //recorre las filas
                indFila++;
                Row fila = (Row)itFila.next(); // capturamos toda la fila del excel
                Iterator itColumna = fila.cellIterator();
                Alumno alumno =  new Alumno();
                int indCol = -1;
                while (itColumna.hasNext()) { //recorre las columnas
                    indCol++;
                    Cell celda = (Cell)itColumna.next();
                    if (indFila != 0){ //diferente del encabezado
                        if (celda != null) {
                            switch (indCol) {
                                case 0: //obtenemos el cod
                                    String cod = String.valueOf(Math.round(celda.getNumericCellValue()));
                                    alumno.setCodMatricula(cod);
                                    break;
                                case 1:  //obtenemos el nombre
                                    String nom = celda.getStringCellValue();
                                    alumno.setNombre(nom);
                                    break;
                                case 2: //obtenemos el apellido
                                    String ape = celda.getStringCellValue();
                                    alumno.setApellido(ape);
                                    break;
                            }
                        }
                    }
                }
                //agregamos el alumno
                if(indFila != 0)
                    listAlumnos.add(alumno);           
            }
            band = true;
        } catch (Exception e) {
            Utilitarios.mensaje(e.getMessage(), 2);
        }
        finally {
            return band;
        }
        
    }
}
