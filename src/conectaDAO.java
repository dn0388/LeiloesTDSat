import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {

    public Connection connectDB() {
    Connection conn = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        conn = DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306/leiloes?useSSL=false&serverTimezone=UTC",
            "root",
            "Dnmrochajcc1234@" 
        );

        System.out.println("Conectado com sucesso!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Erro ConectaDAO: " + e.getMessage());
    }

    return conn;
}

}


