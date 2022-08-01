
package icacit;
import data.Conexion;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

/**
 * @author alfie
 */

public class Icacit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
                
        //fechas sistema
//        int dia, mes, anio, hra, min, seg;
//        Calendar hoy = Calendar.getInstance();
//        dia = hoy.get(Calendar.DATE);
//        mes = hoy.get(Calendar.MONTH); //mes empieza a contar de 0
//        anio = hoy.get(Calendar.YEAR);
//        hra = hoy.get(Calendar.HOUR);
//        min = hoy.get(Calendar.MINUTE);
//        seg = hoy.get(Calendar.SECOND);
//        System.out.println("=> " + dia + ":"+mes+":"+anio+" "+hra+":"+min+":"+seg);
//        System.out.println(hoy.getTime());
//        hoy.add(Calendar.DATE, 2);
//        System.out.println(hoy.getTime());


//        /*composicion*/
//        Curso c1 = new Curso(100, "DB-I", "base de datos 1", 4, 1);
//        Curso c2 = new Curso(100, "c2", "Algoritmos", 4, 1);
//        //se crea una asignacion
//        Curso.Asignacion asignacion1 = c1.new Asignacion(1, "pulido", new Date(2022, 05, 15), true);
//        Curso.Asignacion asignacion2 = c1.new Asignacion(2, "Orlando", new Date(2022, 05, 15), true);
//        //asignamos la asignacion al curso
//        c1.asociarAsignacion(asignacion1);
//        c1.asociarAsignacion(asignacion2);
//        System.out.println("-> "+c1.getDescripcion());
//        c1.getAsignaciones();
//        System.out.println("-> "+c2.getDescripcion());
//        c2.asociarAsignacion(asignacion2);
//        c2.getAsignaciones();
//        

        int rgb = new Color(80,73,69).getRGB();
        System.out.println("color->"+rgb);

    
    }
    
}
