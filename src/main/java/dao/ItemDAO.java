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

    /** Backward-compatible: search only, no stock filter */
    public List<Item> findAll(String q) throws Exception {
        return findAll(q, "all");
    }

    /** Search + stock filter: status = all | in | low | out */
    public List<Item> findAll(String q, String status) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM items");
        List<String> where = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        // Search by name or description
        if (q != null && !q.trim().isEmpty()) {
            where.add("(name LIKE ? OR description LIKE ?)");
            String like = "%" + q.trim() + "%";
            params.add(like);
            params.add(like);
        }

        // Stock filter
        if (status != null) {
            switch (status.toLowerCase()) {
                case "in":
                    where.add("quantity > 0");
                    break;
                case "low":
                    where.add("quantity <= 5 AND quantity > 0");
                    break;
                case "out":
                    where.add("quantity = 0");
                    break;
                // "all" or anything else -> no extra condition
            }
        }

        if (!where.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", where));
        }
        sql.append(" ORDER BY id DESC");

        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {

            int idx = 1;
            for (Object p : params) {
                ps.setObject(idx++, p);
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
