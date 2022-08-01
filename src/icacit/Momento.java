package icacit;

import java.util.HashSet;

/**
 *
 * @author alfie
 */
public class Momento {
    private int momentoId;
    private String nombre;
    private HashSet<Ciclo> ciclos = new HashSet<Ciclo>();

    public Momento(int momentoId, String nombre) {
        this.momentoId = momentoId;
        this.nombre = nombre;
    }

    public int getMomentoId() {
        return momentoId;
    }

    public String getNombre() {
        return nombre;
    }

    public HashSet<Ciclo> getCursos() {
        return ciclos;
    }
    
    public void setMomentoId(int momentoId) {
        this.momentoId = momentoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Agrega el Objeto curso en la lista de cursos 
     * @param curso pertenece a un momento 
     */
    public void setCiclo(Ciclo ciclo) {
        ciclos.add(ciclo);
        ciclo.setMomento(this);
    }    
}
