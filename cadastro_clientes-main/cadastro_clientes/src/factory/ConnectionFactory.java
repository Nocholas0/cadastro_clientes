package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL  = "jdbc:mysql://localhost:3306/cadastro_clientes";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection() throws SQLException {

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException("Driver MySQL não encontrado.", e);
    }

    return DriverManager.getConnection(URL, USER, PASS);
}
    
}
