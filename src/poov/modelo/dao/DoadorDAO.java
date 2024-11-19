package poov.modelo.dao;

import java.sql.Connection;

public class DoadorDAO {
    private final Connection conexao;

    public DoadorDAO(Connection conexao) {
        this.conexao = conexao;
    }
}
