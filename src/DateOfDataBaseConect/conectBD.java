package DateOfDataBaseConect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conectBD {
    // definindo porta de entrada para a IDE acesse meu BD
    private static final String URL = "jdbc:mysql://localhost:3306/bd";
    private static final String USUARIO = "root";
    private static final String SENHA = "Uvm@luc15"; // coloque a senha do seu mysql, se houver

    // metodo para conexao com o banco
    public static Connection conectar() {
        Connection conexao = null;
        try {
            // carregando o driver jdbc (ferramenta)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // tentando estabelecer conexao
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("conexao realizada com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("driver nao encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("erro ao conectar: " + e.getMessage());
        }
        return conexao;
    }

    public static void main(String[] args) {
        // testando a conexao
        conectar();
    }
}
