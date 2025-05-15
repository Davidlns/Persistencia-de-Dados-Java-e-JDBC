package cadastrobd.model.dao;

import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    public PessoaJuridica getPessoa(int id) {
        String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, j.cnpj " +
                     "FROM Pessoa p JOIN PessoaJuridica j ON p.id = j.id WHERE p.id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PessoaJuridica pessoa = null;

        try {
            conn = ConectorBD.getConnection();
            stmt = ConectorBD.getPrepared(sql, conn);
            stmt.setInt(1, id);
            rs = ConectorBD.getSelect(stmt);

            if (rs.next()) {
                pessoa = new PessoaJuridica();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setLogradouro(rs.getString("logradouro"));
                pessoa.setCidade(rs.getString("cidade"));
                pessoa.setEstado(rs.getString("estado"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setCnpj(rs.getString("cnpj"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectorBD.close(rs);
            ConectorBD.close(stmt);
            ConectorBD.close(conn);
        }

        return pessoa;
    }

    public List<PessoaJuridica> getPessoas() {
        List<PessoaJuridica> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, j.cnpj " +
                     "FROM Pessoa p JOIN PessoaJuridica j ON p.id = j.id";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConectorBD.getConnection();
            stmt = ConectorBD.getPrepared(sql, conn);
            rs = ConectorBD.getSelect(stmt);

            while (rs.next()) {
                PessoaJuridica pessoa = new PessoaJuridica();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setLogradouro(rs.getString("logradouro"));
                pessoa.setCidade(rs.getString("cidade"));
                pessoa.setEstado(rs.getString("estado"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setCnpj(rs.getString("cnpj"));
                lista.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectorBD.close(rs);
            ConectorBD.close(stmt);
            ConectorBD.close(conn);
        }

        return lista;
    }

    public void incluir(PessoaJuridica pessoa) {
        int id = SequenceManager.getValue("seq_pessoa");
        String sqlPessoa = "INSERT INTO Pessoa (id, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlJuridica = "INSERT INTO PessoaJuridica (id, cnpj) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            stmt = ConectorBD.getPrepared(sqlPessoa, conn);
            stmt.setInt(1, id);
            stmt.setString(2, pessoa.getNome());
            stmt.setString(3, pessoa.getLogradouro());
            stmt.setString(4, pessoa.getCidade());
            stmt.setString(5, pessoa.getEstado());
            stmt.setString(6, pessoa.getTelefone());
            stmt.setString(7, pessoa.getEmail());
            stmt.executeUpdate();

            stmt = ConectorBD.getPrepared(sqlJuridica, conn);
            stmt.setInt(1, id);
            stmt.setString(2, pessoa.getCnpj());
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        } finally {
            ConectorBD.close(stmt);
            ConectorBD.close(conn);
        }
    }

    public void alterar(PessoaJuridica pessoa) {
        String sqlPessoa = "UPDATE Pessoa SET nome=?, logradouro=?, cidade=?, estado=?, telefone=?, email=? WHERE id=?";
        String sqlJuridica = "UPDATE PessoaJuridica SET cnpj=? WHERE id=?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            stmt = ConectorBD.getPrepared(sqlPessoa, conn);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getLogradouro());
            stmt.setString(3, pessoa.getCidade());
            stmt.setString(4, pessoa.getEstado());
            stmt.setString(5, pessoa.getTelefone());
            stmt.setString(6, pessoa.getEmail());
            stmt.setInt(7, pessoa.getId());
            stmt.executeUpdate();

            stmt = ConectorBD.getPrepared(sqlJuridica, conn);
            stmt.setString(1, pessoa.getCnpj());
            stmt.setInt(2, pessoa.getId());
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        } finally {
            ConectorBD.close(stmt);
            ConectorBD.close(conn);
        }
    }

    public void excluir(int id) {
        String sqlJuridica = "DELETE FROM PessoaJuridica WHERE id=?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id=?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            stmt = ConectorBD.getPrepared(sqlJuridica, conn);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            stmt = ConectorBD.getPrepared(sqlPessoa, conn);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        } finally {
            ConectorBD.close(stmt);
            ConectorBD.close(conn);
        }
    }
}