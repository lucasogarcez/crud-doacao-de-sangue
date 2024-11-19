package poov.modelo.dao;

import java.sql.Connection;

public class DoacaoDAO {
    private final Connection conexao;

    public DoacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }
}
