package dao;

import model.Customer;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void add(Customer c) throws Exception {
        String sql = "INSERT INTO customers(name,surname,phone,address1,address2,province,email) " +
                "VALUES(?,?,?,?,?,?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getSurname());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getAddress1());
            ps.setString(5, c.getAddress2());
            ps.setString(6, c.getProvince());
            ps.setString(7, c.getEmail());
            ps.executeUpdate();
        }
    }

    /** List customers; if q present, case-insensitive match on name/surname/phone/email. */
    public List<Customer> list(String q) throws Exception {
        boolean hasQ = (q != null && !q.isBlank());
        String base = "SELECT * FROM customers";
        String where = hasQ
                ? " WHERE LOWER(name) LIKE ? OR LOWER(surname) LIKE ? OR phone LIKE ? OR LOWER(email) LIKE ?"
                : "";
        String sql = base + where + " ORDER BY id DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (hasQ) {
                String like = "%" + q.trim().toLowerCase() + "%";
                ps.setString(1, like);
                ps.setString(2, like);
                ps.setString(3, "%" + q.trim() + "%"); // keep phone as typed
                ps.setString(4, like);
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<Customer> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;
            }
        }
    }

    /** Find by primary key. */
    public Customer find(int id) throws Exception {
        String sql = "SELECT * FROM customers WHERE id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /** Alias used by Billing flow for clarity/compatibility. */
    public Customer findById(int id) throws Exception {
        return find(id);
    }

    /** Quick search for Billing page: by name/surname/phone (case-insensitive), limited to 50. */
    public List<Customer> searchByNameOrPhone(String q) throws Exception {
        List<Customer> list = new ArrayList<>();
        if (q == null || q.isBlank()) return list;

        String sql = "SELECT id, name, surname, phone, address1, address2, province, email " +
                "FROM customers " +
                "WHERE LOWER(name) LIKE ? OR LOWER(surname) LIKE ? OR phone LIKE ? " +
                "ORDER BY name ASC LIMIT 50";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + q.trim().toLowerCase() + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, "%" + q.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public void update(Customer c) throws Exception {
        String sql = "UPDATE customers SET name=?,surname=?,phone=?,address1=?,address2=?,province=?,email=? WHERE id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getSurname());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getAddress1());
            ps.setString(5, c.getAddress2());
            ps.setString(6, c.getProvince());
            ps.setString(7, c.getEmail());
            ps.setInt(8, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM customers WHERE id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /* ---------- helper ---------- */
    private Customer map(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("phone"),
                rs.getString("address1"),
                rs.getString("address2"),
                rs.getString("province"),
                rs.getString("email")
        );
    }
}
