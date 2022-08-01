package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author alfie
 */
public class Conexion implements Database {
    
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;
    private boolean valConnection;
    
    //CONSTRUCTOR 
    public Conexion() {
        conn = null;
        pstm = null;
        rs = null;
        valConnection = false;
    }
    
    /**
     * Realiza la conexion con la base de datos
    */
    public void conectar() {
        try{
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("Conexion establecida");
            }
            valConnection = true;
        }catch(SQLException e) {
            System.out.println("Error al conectarse a la DB " + e);
        }
    }
    
    /**
     * Cierra la conexion con la base de datos
     */
    public void desconectar() {
        if(valConnection) {
            valConnection = false;
            System.out.println("Conexion cerrada");
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { /* Ignored */ }
            }
            if (pstm != null) {
                try { pstm.close(); } catch (SQLException e) { /* Ignored */ }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { /* Ignored */ }
            }
        }
    }
    
    public void informacion(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            System.out.println("Información acerca de la columna " + i);
            System.out.println("   Nombre........: " + rsmd.getColumnName(i));
            System.out.println("   Tipo de datos.....: " + rsmd.getColumnType(i) + 
                               " ( " + rsmd.getColumnTypeName(i) + " )");
            System.out.println("   Precisión.....: " + rsmd.getPrecision(i));
            System.out.println("   Escala........: " + rsmd.getScale(i));
            System.out.print  ("   Permitir nulos: ");
            if (rsmd.isNullable(i)==0) 
                System.out.println("false");
            else
                System.out.println("true");
        }
    }
    
    /**
     * Realiza la consulta a la base de datos
     * @param query consulta que se desea realizar
     * @return lista de resultados
     */
    public ResultSet selectQuery(String query)
    {
        try{
            PreparedStatement pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
        }catch(SQLException sqle)
        {
            System.out.println("No se pudo hacer la consulta "+ sqle);
        } finally {
            return rs;
        }
    }
        
    /**
     * Guarda los datos en la dababase
     * @param query consulta que se desea guardar
     */
    public void insertQuery(String query)
    {
        try{
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.executeUpdate();
        }catch(SQLException sqle)
        {
            System.out.println("No se pudo hacer la insercion "+ sqle);
        }
    }   
}

