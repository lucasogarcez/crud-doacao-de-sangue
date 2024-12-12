package poov;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import poov.modelo.Doacao;
import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.Situacao;
import poov.modelo.TipoSanguineo;
import poov.modelo.dao.DAOFactory;
import poov.modelo.dao.DoacaoDAO;
import poov.modelo.dao.DoadorDAO;

public class Operacao {
    
    public void abaDoador(Scanner s) throws SQLException{
        String opcao;

        do {
            System.out.println("\n===========================");
            System.out.println("          DOADOR           ");
            System.out.println("===========================");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Pesquisar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Remover");
            System.out.println("5 - Voltar");
            System.out.println("===========================");
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
                    removerDoador(s);
                    break;
    
                case "5":
                    break;
                
                default:
                    System.out.println("Opção inválida! Digite um dos valores indicados anteriormente: ");
            }
        }while(!opcao.equals("5"));
    }

    public void abaDoacao(Scanner s) throws SQLException{
        String opcao;

        do {
            System.out.println("\n===========================");
            System.out.println("          DOAÇÃO           ");
            System.out.println("===========================");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Pesquisar");
            System.out.println("3 - Voltar");
            System.out.println("===========================");
            System.out.print("Digite uma opção: ");
            opcao = s.nextLine();

            switch (opcao) {
                case "1":
                    cadastroDoacao(s);
                    break;
                
                case "2":
                    List<Doacao> doacao = pesquisarDoacao(s);
                    if (doacao != null && !doacao.isEmpty())
                        System.out.println(doacao);
                    else
                        System.out.println("Não foi encontrada nenhuma doação");
                    break;

                case "3":
                    break;

                default:
                    System.out.println("Opção inválida! Digite um dos valores indicados anteriormente: ");
            }

        }while (!opcao.equals("3"));
    }

    public static void cadastroDoador(Scanner s) throws SQLException{
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

    public static List<Doador> buscaDoador(Scanner s) throws SQLException{
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();
            String opcao;

            do{
                System.out.println("\n===========================");
                System.out.println("         PESQUISAR         ");
                System.out.println("===========================");
                System.out.println("1 - Código");
                System.out.println("2 - Nome");
                System.out.println("3 - CPF");
                System.out.println("4 - Voltar");
                System.out.println("===========================");
                System.out.print("Digite uma opção: ");
                opcao = s.nextLine();
                switch (opcao) {
                    case "1":
                        System.out.print("Digite o código do doador: ");
                        Long codigo = s.nextLong();
                        s.nextLine();
                        List<Doador> doador = dao.buscarDoador(codigo, null, null);
                        if (!doador.isEmpty()) {
                            return doador;
                        } else {
                            System.out.println("Não foi encontrado nenhum doador com o código " + codigo);
                        }
                        break;
                    
                    case "2":
                        System.out.print("Digite o nome do doador ou parte dele: ");
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

    public static void alterarDoador(Scanner s) throws SQLException{
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();
            
            System.out.print("Digite o código do doador a ser alterado: ");
            Long cod = s.nextLong();
            s.nextLine();
            List<Doador> doadores = dao.buscarDoador(cod, null, null);
            if (!doadores.isEmpty()) {
                Doador doador = doadores.get(0);
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
                    System.out.print("Digite um opção: ");
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
                            System.out.print("Digite o novo tipo sanguíneo do doador (A, B, AB, O): ");
                            String tipo = s.nextLine().toUpperCase().trim();
                            TipoSanguineo tipoS;
                            while (true) {
                                try{
                                    tipoS = TipoSanguineo.valueOf(tipo);
                                    break;
                                } catch (IllegalArgumentException ex) {
                                    System.out.print("Tipo sanguíneo inválido. Insira uma opção válida (A, B, AB, O): ");
                                    tipo = s.nextLine().toUpperCase().trim();
                                }
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
                    break;
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

    public static void removerDoador(Scanner s) throws SQLException{
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();
            
            System.out.print("Digite o código do doador a ser removido: ");
            Long cod = s.nextLong();
            s.nextLine();
            List<Doador> doadores = dao.buscarDoador(cod, null, null);
            if (!doadores.isEmpty()) {
                Doador doador = doadores.get(0);
                if (dao.removerDoador(doador)) {
                    System.out.println("Remoção efetuada com sucesso");
                } else {
                    System.out.println("Problema ao remover o doador");
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

    public static void cadastroDoacao(Scanner s) {
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoacaoDAO dao = factory.criarDoacaoDAO();
            DoadorDAO daoD = factory.criarDoadorDAO();
        
            Doacao doacao = new Doacao();
            System.out.print("Digite o volume (ml) de sangue da doacao: ");
            doacao.setVolume(s.nextDouble());
            s.nextLine();
            System.out.print("Digite o código do respectivo doador: ");
            Long cod = s.nextLong();
            s.nextLine();
            System.out.print("Digite a data da doação (dd/MM/yyyy): ");
            doacao.setData(LocalDate.parse(s.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.print("Digite a hora da doação (HH:mm): ");
            doacao.setHora(LocalTime.parse(s.nextLine(), DateTimeFormatter.ofPattern("HH:mm")));
            List<Doador> doadores = daoD.buscarDoador(cod, null, null);
            if (!doadores.isEmpty()) {
                doacao.setDoador(doadores.get(0));
                dao.cadastroDoacao(doacao);
            } else {
                System.out.println("Doador não disponível para a doação.");
            }


        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }

    public static List<Doacao> pesquisarDoacao(Scanner s) throws SQLException {
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoacaoDAO daoD = factory.criarDoacaoDAO();
            DoadorDAO dao = factory.criarDoadorDAO();
            String opcao;

            do{
                System.out.println("\n===========================");
                System.out.println("     PESQUISAR DOAÇÃO       ");
                System.out.println("===========================");
                System.out.println("1 - Código do doador");
                System.out.println("2 - Nome do doador");
                System.out.println("3 - CPF do doador");
                System.out.println("4 - Código da doação");
                System.out.println("5 - Data da doação");
                System.out.println("6 - Voltar");
                System.out.println("===========================");
                System.out.print("Digite uma opção: ");
                opcao = s.nextLine();
                switch (opcao) {
                    case "1":
                        System.out.print("Digite o código do doador: ");
                        Long codigo = s.nextLong();
                        s.nextLine();
                        List<Doador> doadores = dao.buscarDoador(codigo, null, null, true);
                        if (!doadores.isEmpty()) {
                            List<Doacao> doacoes = daoD.buscaDoacao(doadores, null);
                            return doacoes;
                        } else {
                            System.out.println("Não foi encontrado nenhum doador com o código " + codigo);
                        }
                        break;
                    
                    case "2":
                        System.out.print("Digite o nome do doador ou parte dele: ");
                        String nome = s.nextLine();
                        List<Doador> doadores2 = dao.buscarDoador(null, nome, null, true);
                        if (!doadores2.isEmpty()) {
                            List<Doacao> doacoes2 = daoD.buscaDoacao(doadores2, null);
                            return doacoes2;
                        } else {
                            System.out.println("Não foi encontrado nenhum doador com o nome " + nome);
                        }
                        break;
    
                    case "3":
                        System.out.print("Digite o cpf do doador ou parte dele: ");
                        String cpf = s.nextLine();
                        List<Doador> doadores3 = dao.buscarDoador(null, null, cpf, true);
                        if (!doadores3.isEmpty()) {
                            List<Doacao> doacoes3 = daoD.buscaDoacao(doadores3, null);
                            return doacoes3;
                        } else {
                            System.out.println("Não foi encontrado nenhum doador com o cpf " + cpf);
                        }
                        break;
    
                    case "4":
                        System.out.print("Digite o código da doação: ");
                        Long codigo_doacao = s.nextLong();
                        s.nextLine();
                        List<Doacao> doacao4 = daoD.buscaDoacao(null, codigo_doacao);
                        if (!doacao4.isEmpty()) {
                            return doacao4;
                        }else {
                            System.out.println("Não foi encontrada nenhuma doação com o código " + codigo_doacao);
                        }
                        break;

                    case "5":
                        String opcao2;
                        do {
                            System.out.println("\n===========================");
                            System.out.println("PESQUISAR DOAÇÃO - POR DATA       ");
                            System.out.println("===========================");
                            System.out.println("1 - Data inicial");
                            System.out.println("2 - Intervalo de datas");
                            System.out.println("3 - Data final");
                            System.out.println("4 - Voltar");
                            System.out.println("===========================");
                            System.out.print("Digite uma opção: ");
                            opcao2 = s.nextLine();
                            
                            List<Doacao> doacao;

                            switch(opcao2) {
                                case "1":
                                    System.out.print("Digite uma data inicial no formato (dd/MM/yyyy): ");
                                    String data = s.nextLine();
                                    LocalDate dataInicial = null;
                                    try {
                                        dataInicial = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Formato de data inválido.");
                                    }
                                    doacao = daoD.buscaDoacaoData(dataInicial, null);
                                    if (!doacao.isEmpty()) {
                                        System.out.println(doacao);
                                    } else {
                                        System.out.println("Não foi encontrada nenhuma doação entre as datas " + dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " e " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                    }
                                    break;
                                case "2":
                                    System.out.print("Digite uma data inicial no formato (dd/MM/yyyy): ");
                                    String dataEntrada1 = s.nextLine();
                                    System.out.print("Digite um data final no formato (dd/MM/yyyy): ");
                                    String dataEntrada2 = s.nextLine();

                                    LocalDate dataInicio = null;
                                    LocalDate dataFinal = null;
                                    try {
                                        dataInicio = LocalDate.parse(dataEntrada1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                        dataFinal = LocalDate.parse(dataEntrada2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Formato de data inválido.");
                                    }
                                    if (dataInicio.isBefore(dataFinal) || dataInicio.isEqual(dataFinal)) {
                                        doacao = daoD.buscaDoacaoData(dataInicio, dataFinal);
                                        if (!doacao.isEmpty()) {
                                            System.out.println(doacao);
                                        } else {
                                            System.out.println("Não foi encontrada nenhuma doação entre as datas " + dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " e " + dataFinal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                        }
                                    } else {
                                        System.out.println("Inserção inválida de datas!");
                                    }
                                    break;
                                case "3":
                                    System.out.print("Digite uma data inicial no formato (dd/MM/yyyy): ");
                                    String dataF = s.nextLine();
                                    LocalDate dataFim = null;
                                    try {
                                        dataFim = LocalDate.parse(dataF, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Formato de data inválido.");
                                    }
                                    doacao = daoD.buscaDoacaoData(null, dataFim);
                                    if (!doacao.isEmpty()) {
                                        System.out.println(doacao);
                                    } else {
                                        System.out.println("Não foi encontrada nenhuma doação até a data de "  + dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                    }
                                    break;
                                case "4":
                                    break;
                            }
                        }while (!opcao2.equals("4"));
                        break;
                    
                    case "6":
                        break;
    
                    default:
                        System.out.print("Opção inválida! Digite um dos valores indicados anteriormente: ");
                }
            }while (!opcao.equals("6"));
        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }

        return null;
    }
}