package poov.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import poov.modelo.Doador;

public class DoadorDAO {
    private final Connection conexao;

    public DoadorDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastroDoador(Doador doador) throws SQLException {
        String sql = "INSERT INTO Doador(nome, cpf, contato, tipoSanguineo, rh) VALUES (?, ?, ?, ?::TipoSanguineo, ?::RH)";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, doador.getNome());
        pstmt.setString(2, doador.getCpf());
        pstmt.setString(3, doador.getContato());
        pstmt.setString(4, doador.getTipoSanguineo().name());
        pstmt.setString(5, doador.getRh().name());
        int inseridos = pstmt.executeUpdate();
        if(inseridos == 1) {
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                doador.setCodigo(rs.getLong(1));
            } else {
                System.out.println("Não foi possível pegar a chave do doador inserido");
            }
            rs.close();
        } else {
            System.out.println("Não foi possível inserir o doador");
        }
        pstmt.close();
    }
}
