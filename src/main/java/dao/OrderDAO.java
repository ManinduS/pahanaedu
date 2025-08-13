package dao;

import model.Cart;
import model.CartItem;
import util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Saves an order and its line items in ONE transaction.
 * Uses columns: address1/address2 (NOT addr1/addr2),
 * and stores subtotal, discount_pct, discount_amt, total.
 */
public class OrderDAO {

    public int saveOrder(
            Integer customerId,
            String customerName,
            String phone,
            String address1,   // <-- address1
            String address2,   // <-- address2
            Cart cart
    ) throws Exception { // DBUtil.getConnection() throws Exception

        final String SQL_ORDER =
                "INSERT INTO orders (" +
                        "customer_id, customer_name, phone, address1, address2, " +
                        "subtotal, discount_pct, discount_amt, total" +
                        ") VALUES (?,?,?,?,?,?,?,?,?)";

        final String SQL_ITEM =
                "INSERT INTO order_items (" +
                        "order_id, item_id, name, qty, unit_price, amount" +
                        ") VALUES (?,?,?,?,?,?)";

        Connection con = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);

            int orderId;

            try (PreparedStatement psOrder =
                         con.prepareStatement(SQL_ORDER, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psItem =
                         con.prepareStatement(SQL_ITEM)) {

                // Totals from cart
                BigDecimal subtotal = cart.getSubtotal();
                BigDecimal discPct  = cart.getDiscountPct();
                BigDecimal discAmt  = cart.getDiscountAmount();
                BigDecimal total    = cart.getTotal();

                // ----- Insert into orders -----
                if (customerId == null || customerId <= 0) {
                    psOrder.setNull(1, Types.INTEGER);
                } else {
                    psOrder.setInt(1, customerId);
                }
                psOrder.setString(2, customerName);
                psOrder.setString(3, phone);
                psOrder.setString(4, address1);
                psOrder.setString(5, address2);
                psOrder.setBigDecimal(6, subtotal);
                psOrder.setBigDecimal(7, discPct);
                psOrder.setBigDecimal(8, discAmt);
                psOrder.setBigDecimal(9, total);
                psOrder.executeUpdate();

                try (ResultSet rs = psOrder.getGeneratedKeys()) {
                    if (!rs.next()) throw new SQLException("Failed to retrieve generated order id");
                    orderId = rs.getInt(1);
                }

                // ----- Insert line items -----
                for (CartItem ci : cart.getItems()) {
                    psItem.setInt(1, orderId);
                    psItem.setInt(2, ci.getItem().getId());
                    psItem.setString(3, ci.getItem().getName());
                    psItem.setInt(4, ci.getQty());
                    psItem.setBigDecimal(5, ci.getUnitPrice());
                    psItem.setBigDecimal(6, ci.getAmount());
                    psItem.addBatch();
                }
                psItem.executeBatch();

                con.commit();
                return orderId;
            } catch (Exception e) {
                if (con != null) try { con.rollback(); } catch (SQLException ignore) {}
                throw e;
            }
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); } catch (SQLException ignore) {}
                try { con.close(); } catch (SQLException ignore) {}
            }
        }
    }
}
