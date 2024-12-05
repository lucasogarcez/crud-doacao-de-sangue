package poov.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import poov.modelo.Doacao;
import poov.modelo.Doador;
import poov.modelo.Situacao;

public class DoacaoDAO {
    private final Connection conexao;

    public DoacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastroDoacao(Doacao doacao) throws SQLException {
        String sql = "INSERT INTO Doacao(data, hora, volume, doador_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setDate(1, Date.valueOf(doacao.getData()));
        pstmt.setTime(2, Time.valueOf(doacao.getHora()));
        pstmt.setDouble(3, doacao.getVolume());
        pstmt.setInt(4, ((int)doacao.getDoador().getCodigo()));
        int inseridos = pstmt.executeUpdate();
        if(inseridos == 1) {
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                doacao.setCodigo(rs.getLong(1));
            } else {
                System.out.println("Não foi possível pegar a chave do doador inserido");
            }
            rs.close();
        } else {
            System.out.println("Não foi possível inserir o doador");
        }
        pstmt.close();
    }

    public List<Doacao> buscaDoacao(List<Doador> doadores, Long codigo_doacao) throws SQLException{
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();
            DoadorDAO dao = factory.criarDoadorDAO();


            List<Doacao> doacoes = new ArrayList<>();
            Doacao doacao;
            
            String sql = "SELECT * FROM Doacao WHERE situacao = 'ATIVO' ";
    
            if (doadores != null && !doadores.isEmpty()) {
                sql += "AND doador_id = ?";
                for (Doador doador_unique : doadores) {
                    PreparedStatement pstmt = conexao.prepareStatement(sql);
                    pstmt.setLong(1, (doador_unique.getCodigo()));
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        doacao = new Doacao(rs.getLong(1), rs.getDate(2).toLocalDate(), rs.getTime(3).toLocalTime(), rs.getDouble(4), Situacao.valueOf(rs.getString(6)));
                        doacao.setDoador(doador_unique);
                        doacoes.add(doacao);
                    }
                    rs.close();
                    pstmt.close();
                }
            }else if (codigo_doacao != null){
                sql += "AND codigo = ?";
                PreparedStatement pstmt = conexao.prepareStatement(sql);
                pstmt.setLong(1, codigo_doacao);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    doacao = new Doacao(rs.getLong(1), rs.getDate(2).toLocalDate(), rs.getTime(3).toLocalTime(), rs.getDouble(4), Situacao.valueOf(rs.getString(6)));
                    doadores = dao.buscarDoador(rs.getLong(5), null, null);
                    doacao.setDoador(doadores.get(0));
                    doacoes.add(doacao);
                }
                rs.close();
                pstmt.close();
            }
    
            return doacoes;

        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
        return null;
    }

    public List<Doacao> buscaDoacaoData (LocalDate data_a_partir, LocalDate data_ate) throws SQLException{
        DAOFactory factory = new DAOFactory();
        try {
            factory.abrirConexao();

            List<Doacao> doacoes = new ArrayList<>();
            Doacao doacao;
            DoadorDAO dao = factory.criarDoadorDAO();
            List<Doador> doadores;

            String sql = "SELECT * FROM Doacao WHERE situacao = 'ATIVO' ";
    
            if (data_a_partir != null && data_ate != null) {
                sql += "AND data BETWEEN ? AND ?";
            } else if (data_a_partir != null) {
                sql += "AND data >= ?";
            } else if (data_ate != null) {
                sql += "AND data <= ?";
            }
            
            PreparedStatement pstmt = conexao.prepareStatement(sql);
    
            if (data_a_partir != null && data_ate != null) {
                pstmt.setDate(1, Date.valueOf(data_a_partir));
                pstmt.setDate(2, Date.valueOf(data_ate));
            } else if (data_a_partir != null) {
                pstmt.setDate(1, Date.valueOf(data_a_partir));
            } else if (data_ate != null) {
                pstmt.setDate(1, Date.valueOf(data_ate));
            }
    
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                doacao = new Doacao(rs.getLong(1), rs.getDate(2).toLocalDate(), rs.getTime(3).toLocalTime(), rs.getDouble(4), Situacao.valueOf(rs.getString(6)));
                doadores = dao.buscarDoador(rs.getLong(5), null, null, true);
                doacao.setDoador(doadores.get(0));
                doacoes.add(doacao);
            }
            rs.close();
            pstmt.close();
    
            return doacoes;
        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
        return null;
    }
}
