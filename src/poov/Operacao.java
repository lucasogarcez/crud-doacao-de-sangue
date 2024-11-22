package poov;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.Situacao;
import poov.modelo.TipoSanguineo;
import poov.modelo.dao.DAOFactory;
import poov.modelo.dao.DoadorDAO;

public class Operacao {
    
    public void abaDoador(Scanner s) {
        String opcao;
        //acesso ao menu e assim por diante, reaproveitando os menus e utilizando eles como funções/métodos
        do {
            System.out.println("Doador");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Pesquisar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Remover");
            System.out.println("5 - Voltar");
            System.out.print("Digite uma opção: ");
            opcao = s.nextLine();
                
            switch (opcao) {
                case "1":
                    cadastroDoador(s);
                    break;
    
                case "2":
                    List<Doador> doadores = buscaDoador(s);
                    System.out.println(doadores);
                    break;
                
                case "3":
                    alterarDoador(s);
                    break;
    
                case "4":
                    break;
    
                case "5":
                    break;
                
                default:
                    System.out.println("Opção inválida! Digite um dos valores indicados anteriormente: ");
                    break;
            }
        }while(!opcao.equals("5"));
    }

    public void abaDoacao(Scanner s) {

    }

    public static void cadastroDoador(Scanner s) {
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

        System.out.println(doador);
    }

    public static List<Doador> buscaDoador(Scanner s) {
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();
            String opcao;

            do{
                System.out.println("Pesquisar ");
                System.out.println("1 - Código");
                System.out.println("2 - Nome");
                System.out.println("3 - CPF");
                System.out.println("4 - Voltar");
                System.out.print("Digite uma opção: ");
                opcao = s.nextLine();
                switch (opcao) {
                    case "1":
                        System.out.print("Digite o código do doador: ");
                        s.nextLine();
                        Long codigo = s.nextLong();
                        List<Doador> doador = dao.buscarDoador(codigo, null, null);
                        if (doador != null) {
                            return doador;
                        } else {
                            System.out.println("Não foi encontrado nenhum doador com o código " + codigo);
                        }
                        break;
                    
                    case "2":
                        System.out.print("Digite o nome do doador ou parte dele: ");
                        s.nextLine();
                        String nome = s.nextLine();
                        List<Doador> doador2 = dao.buscarDoador(null, nome, null);
                        if (!doador2.isEmpty()) {
                            return doador2;
                        } else {
                            System.out.println("Não foi encontrado nenhum doador com o nome " + nome);
                        }
                        break;
    
                    case "3":
                        System.out.print("Digite o cpf do doador ou parte dele: ");
                        s.nextLine();
                        String cpf = s.nextLine();
                        List<Doador> doador3 = dao.buscarDoador(null, null, cpf);
                        if (!doador3.isEmpty()) {
                            return doador3;
                        } else {
                            System.out.println("Não foi encontrado nenhum doador com o cpf " + cpf);
                        }
                        break;
    
                    case "4":
                        break;
    
                    default:
                        System.out.print("Opção inválida! Digite um dos valores indicados anteriormente: ");
                }
            }while (!opcao.equals("4"));
        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }

        return null;
    }

    public static void alterarDoador(Scanner s) {
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();
            
            System.out.print("Digite o código do doador a ser alterado: ");
            Long cod = s.nextLong();
            List<Doador> doadores = dao.buscarDoador(cod, null, null);
            Doador doador = doadores.get(0);
            if (doador != null) {
                String opcao;
                do {
                    System.out.println("Alterar");
                    System.out.println("1 - Nome");
                    System.out.println("2 - CPF");
                    System.out.println("3 - Contato");
                    System.out.println("4 - TipoERhCorreto");
                    System.out.println("5 - Tipo Sanguíneo");
                    System.out.println("6 - RH");
                    System.out.println("7 - Situação");
                    System.out.println("8 - Voltar");
                    opcao = s.nextLine();
        
                    switch (opcao) {
                        case "1":
                            System.out.print("Digite um novo nome: ");
                            doador.setNome(s.nextLine());
                            break;
                        case "2":
                            System.out.print("Digite um novo cpf: ");
                            doador.setCpf(s.nextLine());
                            break;
                        
                        case "3":
                            System.out.print("Digite um novo contato: ");
                            doador.setContato(s.nextLine());
                            break;
                        
                        case "4":
                            System.out.print("Digite uma letra - C (Correto) ou E (errado): ");
                            String resp = s.nextLine().toUpperCase().trim();
                            if (resp.equals("C"))
                                doador.setTipoERhCorretos(true);
                            else if (resp.equals("E"))
                                doador.setTipoERhCorretos(false);
                            break;
                        
                        case "5":
                            System.out.print("Digite o novo tipo sanguíneo do doador (A, B, AB, O, DESCONHECIDO): ");
                            String tipo = s.nextLine().toUpperCase().trim();
                            TipoSanguineo tipoS;
                            try{
                                tipoS = TipoSanguineo.valueOf(tipo);
                            } catch (IllegalArgumentException ex) {
                                System.out.println("Tipo sanguíneo inválido, definido como DESCONHECIDO");
                                tipoS = TipoSanguineo.DESCONHECIDO;
                            }
                            doador.setTipoSanguineo(tipoS);
                            break;
                        
                        case "6":
                            System.out.print("Digite o novo rh do doador (POSITIVO, NEGATIVO, DESCONHECIDO): ");
                            String entradaRh = s.nextLine().toUpperCase().trim();
                            RH rh;
                            try{
                                rh = RH.valueOf(entradaRh);
                            } catch (IllegalArgumentException ex) {
                                System.out.println("RH inválido, definido como DESCONHECIDO");
                                rh = RH.DESCONHECIDO;
                            }
                            doador.setRh(rh);
                            break;
                        
                        case "7":
                            System.out.print("Digite a nova situação do doador (ATIVO, INATIVO): ");
                            String entradaSituacao = s.nextLine().toUpperCase().trim();
                            Situacao situacao;
                            try{
                                situacao = Situacao.valueOf(entradaSituacao);
                            } catch (IllegalArgumentException ex) {
                                System.out.println("Situação inválida, definida como INATIVA");
                                situacao = Situacao.INATIVO;
                            }
                            doador.setSituacao(situacao);
                            break;
                        
                        case "8":
                            break;
                    
                        default:
                            System.out.println("Opção inválida! Digite um dos valores indicados anteriormente: ");
                    }
                }while (!opcao.equals("8"));
                
                if (dao.alterarDoador(doador)) {
                    System.out.println("Alteração efetuada com sucesso");
                } else {
                    System.out.println("Problema ao alterar o doador");
                }
            } else {
                System.out.println("Não foi encontrado nenhum doador com o código " + cod);
            }
        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }


}