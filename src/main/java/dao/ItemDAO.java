package dao;

import model.Item;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public void insert(Item item) throws Exception {
        String sql = "INSERT INTO items(name,description,price,quantity) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setBigDecimal(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.executeUpdate();
        }
    }

    public List<Item> findAll(String q) throws Exception {
        String base = "SELECT * FROM items";
        String sql = (q == null || q.isEmpty()) ? base : base + " WHERE name LIKE ? OR description LIKE ? ORDER BY id DESC";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (sql.contains("LIKE")) {
                String like = "%" + q + "%";
                ps.setString(1, like); ps.setString(2, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Item> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(map(rs));
                }
                return list;
            }
        }
    }

    public Item findById(int id) throws Exception {
        String sql = "SELECT * FROM items WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public void update(Item item) throws Exception {
        String sql = "UPDATE items SET name=?, description=?, price=?, quantity=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setBigDecimal(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setInt(5, item.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM items WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Item map(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("quantity")
        );
    }
}
