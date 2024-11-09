package DateOfDataBaseConect;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class result {
    public static void main(String[] args) {
        // estabelecendo conexao com o banco de dados
        Connection conexao = conectBD.conectar();
        if (conexao != null) {
            Scanner scanner = new Scanner(System.in);
            int opcao;
            do {
                // exibindo menu principal
                System.out.println("=== menu principal ===");
                System.out.println("1. inserir aluno");
                System.out.println("2. atualizar aluno");
                System.out.println("3. deletar aluno");
                System.out.println("4. ler registros de alunos");
                System.out.println("0. sair");
                System.out.print("escolha uma opcao: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // consumindo quebra de linha

                // executando a acao conforme a escolha do usuario
                switch (opcao) {
                    case 1:
                        inserirAluno(conexao, scanner);
                        break;
                    case 2:
                        atualizarAluno(conexao, scanner);
                        break;
                    case 3:
                        deletarAluno(conexao, scanner);
                        break;
                    case 4:
                        lerDadosAlunos(conexao);
                        break;
                    case 0:
                        System.out.println("saindo...");
                        break;
                    default:
                        System.out.println("opcao invalida. tente novamente.");
                }
            } while (opcao != 0);
            scanner.close();
        }
    }

    private static void inserirAluno(Connection conexao, Scanner scanner) {
        String sql = "INSERT INTO alunos (nome, idade) VALUES (?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            System.out.print("digite o nome do aluno: ");
            String nome = scanner.nextLine();
            System.out.print("digite a idade do aluno: ");
            int idade = scanner.nextInt();

            // definindo os valores dos parametros da query
            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.executeUpdate(); // executando a insercao no banco de dados
            System.out.println("dados inseridos com sucesso!");
        } catch (SQLException e) {
            System.err.println("erro ao inserir dados: " + e.getMessage());
        }
    }

    private static void atualizarAluno(Connection conexao, Scanner scanner) {
        String sql = "UPDATE alunos SET nome = ?, idade = ? WHERE id = ?";
        try {
            System.out.print("digite o id do aluno que deseja atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consumindo quebra de linha
            System.out.print("digite o novo nome do aluno: ");
            String nome = scanner.nextLine();
            System.out.print("digite a nova idade do aluno: ");
            int idade = scanner.nextInt();

            PreparedStatement stmt = conexao.prepareStatement(sql);
            // definindo os valores dos parametros da query
            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.setInt(3, id);
            int rowsUpdated = stmt.executeUpdate(); // executando a atualizacao no banco de dados
            if (rowsUpdated > 0) {
                System.out.println("registro atualizado com sucesso!");
            } else {
                System.out.println("nenhum registro encontrado com o id especificado.");
            }
        } catch (SQLException e) {
            System.err.println("erro ao atualizar dados: " + e.getMessage());
        }
    }

    private static void deletarAluno(Connection conexao, Scanner scanner) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try {
            System.out.print("digite o id do aluno que deseja deletar: ");
            int id = scanner.nextInt();

            PreparedStatement stmt = conexao.prepareStatement(sql);
            // definindo o valor do parametro da query
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate(); // executando a exclusao no banco de dados
            if (rowsDeleted > 0) {
                System.out.println("registro deletado com sucesso!");
            } else {
                System.out.println("nenhum registro encontrado com o id especificado.");
            }
        } catch (SQLException e) {
            System.err.println("erro ao deletar dados: " + e.getMessage());
        }
    }

    private static void lerDadosAlunos(Connection conexao) {
        String sql = "SELECT * FROM alunos";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(); // executando a consulta no banco de dados

            // iterando sobre o resultado da consulta
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");

                System.out.println("id: " + id + ", nome: " + nome + ", idade: " + idade);
            }
        } catch (SQLException e) {
            System.err.println("erro ao ler dados: " + e.getMessage());
        }
    }
}
