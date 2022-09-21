/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author alfie
 */
public class DefinicionHasMomento {
    private int definicionId;
    private byte momentoId;

    public DefinicionHasMomento() {
    }
    

    public DefinicionHasMomento(int definicionId) {
        this.definicionId = definicionId;
    }
    
    public DefinicionHasMomento(int definicionId, byte momentoId) {
        this.definicionId = definicionId;
        this.momentoId = momentoId;
    }

    public DefinicionHasMomento(byte momentoId) {
        this.momentoId = momentoId;
    }

    public int getDefinicionId() {
        return definicionId;
    }

    public void setDefinicionId(int definicionId) {
        this.definicionId = definicionId;
    }

    public byte getMomentoId() {
        return momentoId;
    }

    public void setMomentoId(byte momentoId) {
        this.momentoId = momentoId;
    }
    
    
    
}
