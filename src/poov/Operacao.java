package poov;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.TipoSanguineo;
import poov.modelo.dao.DAOFactory;
import poov.modelo.dao.DoadorDAO;

public class Operacao {
    
    
    public void abaDoador() {
        Scanner s = new Scanner(System.in);
        int opcao;
        //acesso ao menu e assim por diante, reaproveitando os menus e utilizando eles como funções/métodos
        do {
            System.out.println("Doador");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Pesquisar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Remover");
            System.out.println("5 - Voltar");
            System.out.print("Digite uma opção: ");
            opcao = s.nextInt();
                
            switch (opcao) {
                case 1:
                    cadastroDoador();
                    break;
    
                case 2:
                    buscaDoador();
                    break;
                
                case 3:
                    break;
    
                case 4:
                    break;
    
                case 5:
                    break;
                
                default:
                    System.out.println("Opção inválida! Digite um dos valores indicados anteriormente: ");
                    break;
            }
        }while(opcao != 5);
        s.close();
    }

    public void abaDoacao() {

    }

    public static void cadastroDoador() {
        Scanner s = new Scanner(System.in);
        Doador doador = new Doador();
        System.out.print("Digite o nome do doador: ");
        doador.setNome(s.nextLine());
        System.out.print("Digite o cpf do doador: ");
        doador.setCpf(s.nextLine());
        System.out.print("Digite o contato do doador: ");
        doador.setContato(s.nextLine());
        System.out.print("Digite o tipo sanguíneo do doador (A, B, AB, O, DESCONHECIDO): ");
        String tipo = s.nextLine().toUpperCase().trim();
        TipoSanguineo tipoS;
        try{
            tipoS = TipoSanguineo.valueOf(tipo);
        } catch (IllegalArgumentException ex) {
            System.out.println("Tipo sanguíneo inválido, definido como DESCONHECIDO");
            tipoS = TipoSanguineo.DESCONHECIDO;
        }
        doador.setTipoSanguineo(tipoS);
        System.out.print("Digite o rh do doador (POSITIVO, NEGATIVO, DESCONHECIDO): ");
        String entradaRh = s.nextLine().toUpperCase().trim();
        RH rh;
        try{
            rh = RH.valueOf(entradaRh);
        } catch (IllegalArgumentException ex) {
            System.out.println("RH inválido, definido como DESCONHECIDO");
            rh = RH.DESCONHECIDO;
        }
        doador.setRh(rh);

        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();
            dao.cadastroDoador(doador);
        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }

        s.close();
        System.out.println(doador);
    }

    public static void buscaDoador() {
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();
            Scanner s = new Scanner(System.in);
            int opcao;

            do{
                System.out.println("Pesquisar ");
                System.out.println("1 - Código");
                System.out.println("2 - Nome");
                System.out.println("3 - CPF");
                System.out.println("4 - Voltar");
                System.out.print("Digite uma opção: ");
                opcao = s.nextInt();
                switch (opcao) {
                    case 1:
                        System.out.print("Digite o código do doador: ");
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
                        System.out.print("Digite o nome do doador ou parte dele: ");
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
                        System.out.print("Digite o cpf do doador ou parte dele: ");
                        s.nextLine();
                        String cpf = s.nextLine();
                        List<Doador> doador3 = dao.buscarDoador(null, null, cpf);
                        if (!doador3.isEmpty()) {
                            System.out.println(doador3);
                        } else {
                            System.out.println("Não foi encontrado nenhum doador com o cpf " + cpf);
                        }
                        break;
    
                    case 4:
                        break;
    
                    default:
                        System.out.print("Opção inválida! Digite um dos valores indicados anteriormente: ");
                }
            }while (opcao != 4);
            s.close();
        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }
}
