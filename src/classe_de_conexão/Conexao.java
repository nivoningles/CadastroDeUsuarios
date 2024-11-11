package classe_de_conexão;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static String caminho = "jdbc:mysql://localhost:3306/primeiroprojeto?serverTimezone=America/Sao_Paulo";
    private static String senha = "devnivo1@";

    public static Connection faz_Conexao() throws SQLException {
        try {
            // Use o driver correto para versões mais recentes do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Use a URL correta com a porta
            String usuario = "root";
            return DriverManager.getConnection(caminho, usuario,senha);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver não encontrado: " + e.getMessage());
        }
    }
}
