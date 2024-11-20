package poov.testes;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import poov.modelo.Doador;
import poov.modelo.dao.DAOFactory;
import poov.modelo.dao.DoadorDAO;


public class BuscaDoador {
    
    public static void main (String[] args) {
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();
            Scanner s = new Scanner(System.in);
            System.out.println("Pesquisa ");
            System.out.println("1 - Código");
            System.out.println("2 - Nome");
            System.out.println("3 - CPF");
            System.out.print("Digite uma opção: ");
            int opcao = s.nextInt();
            switch (opcao) {
                case 1:
                    System.out.println("Digite o código do doador: ");
                    s.nextLine();
                    Long codigo = s.nextLong();
                    List<Doador> doador = dao.buscarDoador(codigo, null, null);
                    if (doador != null) {
                        System.out.println(doador);
                    } else {
                        System.out.println("Não foi encontrado nenhum doador com o código " + codigo);
                    }
                    break;
                
                case 2:
                    System.out.println("Digite o nome do doador ou parte dele: ");
                    s.nextLine();
                    String nome = s.nextLine();
                    List<Doador> doador2 = dao.buscarDoador(null, nome, null);
                    if (!doador2.isEmpty()) {
                        System.out.println(doador2);
                    } else {
                        System.out.println("Não foi encontrado nenhum doador com o nome " + nome);
                    }
                    break;

                case 3:
                    System.out.println("Digite o cpf do doador ou parte dele: ");
                    s.nextLine();
                    String cpf = s.nextLine();
                    List<Doador> doador3 = dao.buscarDoador(null, null, cpf);
                    if (!doador3.isEmpty()) {
                        System.out.println(doador3);
                    } else {
                        System.out.println("Não foi encontrado nenhum doador com o cpf " + cpf);
                    }
                    break;

                default:
                    System.out.println("Opção inválida");
                }
            s.close();
        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }
}
