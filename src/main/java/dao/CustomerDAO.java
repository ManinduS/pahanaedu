package dao;

import model.Customer;
import util.DBUtil;
import java.sql.*;
import java.util.*;

public class CustomerDAO {
    public void add(Customer c) throws Exception {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO customers(name,surname,phone,address1,address2,province,email) VALUES(?,?,?,?,?,?,?)")) {
            ps.setString(1,c.getName()); ps.setString(2,c.getSurname()); ps.setString(3,c.getPhone());
            ps.setString(4,c.getAddress1()); ps.setString(5,c.getAddress2());
            ps.setString(6,c.getProvince()); ps.setString(7,c.getEmail());
            ps.executeUpdate();
        }
    }

    public List<Customer> list(String q) throws Exception {
        String sql = (q==null || q.isBlank())
                ? "SELECT * FROM customers ORDER BY id DESC"
                : "SELECT * FROM customers WHERE name LIKE ? OR phone LIKE ? OR email LIKE ? ORDER BY id DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (sql.contains("LIKE")) {
                String like="%"+q+"%";
                ps.setString(1,like); ps.setString(2,like); ps.setString(3,like);
            }
            ResultSet rs = ps.executeQuery();
            List<Customer> out = new ArrayList<>();
            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("id"), rs.getString("name"), rs.getString("surname"),
                        rs.getString("phone"), rs.getString("address1"), rs.getString("address2"),
                        rs.getString("province"), rs.getString("email"));
                out.add(c);
            }
            return out;
        }
    }

    public Customer find(int id) throws Exception {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM customers WHERE id=?")) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"), rs.getString("name"), rs.getString("surname"),
                        rs.getString("phone"), rs.getString("address1"), rs.getString("address2"),
                        rs.getString("province"), rs.getString("email"));
            }
            return null;
        }
    }

    public void update(Customer c) throws Exception {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE customers SET name=?,surname=?,phone=?,address1=?,address2=?,province=?,email=? WHERE id=?")) {
            ps.setString(1,c.getName()); ps.setString(2,c.getSurname()); ps.setString(3,c.getPhone());
            ps.setString(4,c.getAddress1()); ps.setString(5,c.getAddress2());
            ps.setString(6,c.getProvince()); ps.setString(7,c.getEmail()); ps.setInt(8,c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM customers WHERE id=?")) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }
}
