package Utilidades;

import javax.swing.JOptionPane;

/**
 * Clase crea los mensaje de JOptionPane con la finalidad de minimizar codigo
 * @author alfie
 */
public class Utilitarios {
    
    /**
     * invoca a JOptionPane para enviar mensajes
     * @param msj el mensaje a mostrar
     * @param i tipo de mensaje:
     * <ul>
     * <li>PLAIN_MESSAGE = -1 (sin icono)</li>
     * <li>ERROR_MESSAGE = 0 (X => error)</li>
     * <li>INFORMATION_MESSAJE = 1 (¡ => información)</li>
     * <li>WARNIN_MESSAJE = 2 (! => advertencia)</li>
     * <li>QUESTION_MESSAGE = 3 (? => pregunta)</li>
     * </ul>
     */
    public static void mensaje(String msj, int i) {
        JOptionPane.showMessageDialog(null, msj, "Proyecto", i);
    }
    
    /**
     * invoca un JOptionPane para confirmar datos
     * @param msj pregunta para confirmar
     * @param i tipo de botones
     * CLOSED_OPTION = -1 (ACEPTAR)
     * YES_OPTION/OK_OPTION = 0 (SI/NO)
     * NO_OPTION = 1 (SI/NO/CANCELAR)
     * CANCEL_OPTION = 2 (ACEPTAR/CANCELAR)
     */
    public static int confirmacion(String msj, int i) {
        return JOptionPane.showConfirmDialog(null, msj, "Proyecto", i);
    }
    
}