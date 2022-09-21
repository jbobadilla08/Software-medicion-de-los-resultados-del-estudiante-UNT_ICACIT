package dao;

import Utilidades.Utilitarios;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author alfie
 */
public class Conexion {
    private Connection conn = null;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String user = "root";
    private static String password = "";
    private static String url = "jdbc:mysql://localhost:3306/icacit"; //? serverTimezone=UTC";
    Utilitarios util = new Utilitarios();

    public Conexion() {
    }

    //conectar a la base de datos
    public Connection getConnection()
    {
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        }catch(Exception ex) {
            util.mensaje(ex.toString(), 0);
        } finally {
            return conn;
        }
    }
    
    /**
     * Cierra la conexion con la base de datos
     */
    public void close() {
        if (conn != null) {
            try { 
                conn.close(); 
            } catch (SQLException ex) {
                Utilitarios.mensaje(ex.toString(), 0);
            }
        }
    }

    public static void informacion(ResultSet rs) throws SQLException {
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
}
//    
//    /**
//     * Realiza la consulta a la base de datos
//     * @param query consulta que se desea realizar
//     * @return lista de resultados
//     */
//    public ResultSet selectQuery(String query)
//    {
//        try{
//            PreparedStatement pstm = conn.prepareStatement(query);
//            rs = pstm.executeQuery();
//        }catch(SQLException sqle)
//        {
//            System.out.println("No se pudo hacer la consulta "+ sqle);
//        } finally {
//            return rs;
//        }
//    }
//        
//    /**
//     * Guarda los datos en la dababase
//     * @param query consulta que se desea guardar
//     */
//    public void insertQuery(String query)
//    {
//        try{
//            PreparedStatement pstm = conn.prepareStatement(query);
//            pstm.executeUpdate();
//        }catch(SQLException sqle)
//        {
//            System.out.println("No se pudo hacer la insercion "+ sqle);
//        }
//    }   


