
package dao;

import factory.ConnectionFactory;
import modelo.Raca;

import java.sql.*;
import java.util.ArrayList;

public class RacaDAO {

    public void cadastrar(Raca r) throws SQLException {

        String sql =
            "INSERT INTO raca (nome_raca, tipo_animal, status) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getNomeRaca());
            ps.setString(2, r.getTipoAnimal());
            ps.setBoolean(3, r.isStatus());

            ps.executeUpdate();
        }
    }

    public ArrayList<Raca> consultar() throws SQLException {

        String sql = "SELECT * FROM raca WHERE status = true";

        ArrayList<Raca> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Raca r = new Raca();

                r.setId(rs.getInt("id"));
                r.setNomeRaca(rs.getString("nome_raca"));
                r.setTipoAnimal(rs.getString("tipo_animal"));
                r.setStatus(rs.getBoolean("status"));

                lista.add(r);
            }
        }

        return lista;
    }
    public void alterar(Raca r) throws SQLException {

    String sql =
        "UPDATE raca SET nome_raca=?, tipo_animal=?, status=? WHERE id=?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, r.getNomeRaca());
        ps.setString(2, r.getTipoAnimal());
        ps.setBoolean(3, r.isStatus());
        ps.setInt(4, r.getId());

        ps.executeUpdate();
    }
}
public void inativar(int id) throws SQLException {

    String sql = "UPDATE raca SET status = false WHERE id = ?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);

        ps.executeUpdate();
    }
}

}