/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseMysql;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class DbConnection {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/covid_symptoms_tracker?useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "Zaiafdev";

    public static Connection getconnection() {
        Connection conn;
        conn = null;
        try {
          // Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection(DATABASE_URL , DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Connected");
           
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,"Not Connected");
            System.out.println(e.getMessage());

        }

        return conn;

    }

}
