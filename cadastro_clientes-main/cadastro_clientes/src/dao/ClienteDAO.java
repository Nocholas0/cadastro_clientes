package dao;

import factory.ConnectionFactory;
import modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {

    public void cadastrar(Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf, data_nascimento, telefone, " +
             "endereco, bairro, cidade, estado, cep, status) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setDate(3, Date.valueOf(c.getDataNascimento()));
            ps.setString(4, c.getTelefone());
            ps.setString(5, c.getEndereco());
            ps.setString(6, c.getBairro());
            ps.setString(7, c.getCidade());
            ps.setString(8, c.getEstado());
            ps.setString(9, c.getCep());
            ps.setBoolean(10, true);

            ps.executeUpdate();
        }
    }

    public ArrayList<Cliente> consultar(String busca) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE nome LIKE ? OR cpf LIKE ?";
        ArrayList<Cliente> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + busca + "%");
            ps.setString(2, "%" + busca + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        }
        return null;
    }

    public void alterar(Cliente c) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE status = true AND (nome LIKE ? OR cpf LIKE ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setDate(3, Date.valueOf(c.getDataNascimento()));
            ps.setString(4, c.getTelefone());
            ps.setString(5, c.getEndereco());
            ps.setString(6, c.getBairro());
            ps.setString(7, c.getCidade());
            ps.setString(8, c.getEstado());
            ps.setString(9, c.getCep());
            ps.setInt(10, c.getId());

            ps.executeUpdate();
        }
    }

   public void excluir(int id) throws SQLException {

    String sql = "UPDATE cliente SET status = false WHERE id = ?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        c.setTelefone(rs.getString("telefone"));
        c.setEndereco(rs.getString("endereco"));
        c.setBairro(rs.getString("bairro"));
        c.setCidade(rs.getString("cidade"));
        c.setEstado(rs.getString("estado"));
        c.setCep(rs.getString("cep"));
        c.setStatus(rs.getBoolean("status"));
        return c;
    }
}
