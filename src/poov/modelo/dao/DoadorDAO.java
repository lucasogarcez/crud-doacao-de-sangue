package poov.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.Situacao;
import poov.modelo.TipoSanguineo;

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
        pstmt.setObject(4, doador.getTipoSanguineo().name());
        pstmt.setObject(5, doador.getRh().name());
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

    public List<Doador> buscarDoador(Long codigo, String nome, String cpf) throws SQLException{
        List<Doador> doadores = new ArrayList<>();
        Doador doador; 
        String sql = "SELECT * FROM Doador WHERE situacao = 'ATIVO'";

        if (codigo != null) {
            sql += " AND codigo = ?";
        }
        if (nome != null && !nome.isEmpty()) {
            sql += " AND nome ILIKE ?";
        }
        if (cpf != null && !cpf.isEmpty()) {
            sql += " AND cpf ILIKE ?";
        }
        
        PreparedStatement pstmt = conexao.prepareStatement(sql);
        if (codigo != null) {
            pstmt.setLong(1, codigo);
        }
        if (nome != null && !nome.isEmpty()) {
            pstmt.setString(1, "%" + nome + "%");
        }
        if (cpf != null && !cpf.isEmpty()) {
            pstmt.setString(1, "%" + cpf + "%");
        }

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            doador = new Doador(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), TipoSanguineo.valueOf(rs.getString(6).toUpperCase().trim()), RH.valueOf(rs.getString(7).toUpperCase().trim()), Situacao.valueOf(rs.getString(8).toUpperCase().trim()));
            doadores.add(doador);
        }
        rs.close();
        pstmt.close();

        return doadores;
    }
}
