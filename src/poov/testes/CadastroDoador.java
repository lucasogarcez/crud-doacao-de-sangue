package poov.testes;

import java.sql.SQLException;
import java.util.Scanner;

import poov.modelo.dao.DAOFactory;
import poov.modelo.dao.DoadorDAO;
import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.TipoSanguineo;

public class CadastroDoador {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Doador doador = new Doador();
        System.out.print("Digite o nome do doador: ");
        doador.setNome(s.nextLine());
        System.out.print("Digite o cpf do doador: ");
        doador.setCpf(s.nextLine());
        System.out.print("Digite o contato do doador: ");
        doador.setContato(s.nextLine());
        System.out.print("Digite o tipo sanguíneo do doador (A, B, AB, O, DESCONHECIDO): ");
        doador.setTipoSanguineo(TipoSanguineo.valueOf(s.nextLine().toUpperCase()));
        System.out.print("Digite o rh do doador (POSITIVO, NEGATIVO, DESCONHECIDO): ");
        doador.setRh(RH.valueOf(s.nextLine().toUpperCase()));

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
}
