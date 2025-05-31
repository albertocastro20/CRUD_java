
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    String bd = "escuela";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "Cris8426";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;
    
    public Connection conectar()
    {
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url+bd, user, password);
            
            System.out.println("Conexion Exitosa");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conecto a la BD");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cx;
    }
    
    
}
