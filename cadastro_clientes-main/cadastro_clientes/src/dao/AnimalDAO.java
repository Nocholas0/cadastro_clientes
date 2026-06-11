package dao;

import factory.ConnectionFactory;
import modelo.Animal;

import java.sql.*;
import java.util.ArrayList;

public class AnimalDAO {

    public void cadastrar(Animal a) throws SQLException {

        String sql =
            "INSERT INTO animal (nome, data_nascimento, sexo, cor, observacoes, id_cliente, id_raca, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getNome());
            ps.setDate(2, Date.valueOf(a.getDataNascimento()));
            ps.setString(3, a.getSexo());
            ps.setString(4, a.getCor());
            ps.setString(5, a.getObservacoes());

            ps.setInt(6, a.getIdCliente());
            ps.setInt(7, a.getIdRaca());

            ps.setBoolean(8, a.isStatus());

            ps.executeUpdate();
        }
    }

    public ArrayList<Animal> consultar() throws SQLException {

        String sql = "SELECT * FROM animal WHERE status = true";

        ArrayList<Animal> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Animal a = new Animal();

                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setDataNascimento(
                    rs.getDate("data_nascimento").toLocalDate()
                );

                a.setSexo(rs.getString("sexo"));
                a.setCor(rs.getString("cor"));
                a.setObservacoes(rs.getString("observacoes"));

                a.setIdCliente(rs.getInt("id_cliente"));
                a.setIdRaca(rs.getInt("id_raca"));

                a.setStatus(rs.getBoolean("status"));

                lista.add(a);
            }
        }

        return lista;
    }
    public void alterar(Animal a) throws SQLException {

    String sql =
        "UPDATE animal SET nome=?, data_nascimento=?, sexo=?, cor=?, observacoes=?, " +
        "id_cliente=?, id_raca=?, status=? WHERE id=?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, a.getNome());
        ps.setDate(2, Date.valueOf(a.getDataNascimento()));
        ps.setString(3, a.getSexo());
        ps.setString(4, a.getCor());
        ps.setString(5, a.getObservacoes());

        ps.setInt(6, a.getIdCliente());
        ps.setInt(7, a.getIdRaca());

        ps.setBoolean(8, a.isStatus());

        ps.setInt(9, a.getId());

        ps.executeUpdate();
    }
}

public void inativar(int id) throws SQLException {

    String sql = "UPDATE animal SET status = false WHERE id = ?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);

        ps.executeUpdate();
    }
}
public ResultSet consultarPorCliente(String busca) throws SQLException {

    String sql =
        "SELECT a.nome, r.nome_raca, " +
        "TIMESTAMPDIFF(YEAR, a.data_nascimento, CURDATE()) AS idade, " +
        "a.sexo, a.status " +
        "FROM animal a " +
        "INNER JOIN cliente c ON a.id_cliente = c.id " +
        "INNER JOIN raca r ON a.id_raca = r.id " +
        "WHERE c.nome LIKE ? OR c.cpf LIKE ?";

    Connection conn = ConnectionFactory.getConnection();

    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setString(1, "%" + busca + "%");
    ps.setString(2, "%" + busca + "%");

    return ps.executeQuery();
}
}