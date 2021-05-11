package application.database;

import java.sql.*;

import javax.swing.JOptionPane;

public class MySqlDaoFactory{

    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_NAME="chat";

    private static final String DBURL= "jdbc:mysql://localhost:3306/"+DATABASE_NAME;
    private static final String USERNAME="root";
    private static final String PASSWORD="";

    public static Connection createConnection() {
        try {
            Class.forName(DRIVER);
            Connection conn=DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
            return conn;
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
}
