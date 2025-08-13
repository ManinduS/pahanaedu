package control;

import dao.ItemDAO;
import model.Cart;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BillingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Cart getCart(HttpServletRequest req) {
        HttpSession s = req.getSession();
        Cart cart = (Cart) s.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            s.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String q = req.getParameter("q");
        if (q != null && !q.trim().isEmpty()) {
            try {
                List<Item> results = new ItemDAO().searchByName(q.trim());
                req.setAttribute("results", results);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        req.getRequestDispatcher("/billing.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/billing");
            return;
        }

        Cart cart = getCart(req);
        ItemDAO itemDAO = new ItemDAO();

        try {
            switch (action) {
                case "add": {
                    int id  = Integer.parseInt(req.getParameter("id"));
                    int qty = Integer.parseInt(req.getParameter("qty"));
                    Item item = itemDAO.findById(id);
                    if (item != null) cart.add(item, qty);
                    resp.sendRedirect(req.getContextPath() + "/billing");
                    return;
                }
                case "updateQty": {
                    int id  = Integer.parseInt(req.getParameter("id"));
                    int qty = Integer.parseInt(req.getParameter("qty"));
                    cart.updateQty(id, qty);
                    resp.sendRedirect(req.getContextPath() + "/billing");
                    return;
                }
                case "remove": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    cart.remove(id);
                    resp.sendRedirect(req.getContextPath() + "/billing");
                    return;
                }
                case "discount": {
                    BigDecimal pct = new BigDecimal(req.getParameter("pct"));
                    cart.setDiscountPct(pct);
                    resp.sendRedirect(req.getContextPath() + "/billing");
                    return;
                }
                case "clear": {
                    cart.clear();
                    resp.sendRedirect(req.getContextPath() + "/billing");
                    return;
                }
                case "checkout": {
                    // Optional customer fields (print only, no DB)
                    String name   = req.getParameter("cust_name");
                    String phone  = req.getParameter("cust_phone");
                    String addr1  = req.getParameter("cust_addr1");
                    String addr2  = req.getParameter("cust_addr2");

                    if (cart.isEmpty()) {
                        resp.sendRedirect(req.getContextPath() + "/billing");
                        return;
                    }

                    String billNo = "B" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

                    req.setAttribute("orderId", billNo);
                    req.setAttribute("cart", cart);
                    req.setAttribute("cust_name", name);
                    req.setAttribute("cust_phone", phone);
                    req.setAttribute("cust_addr1", addr1);
                    req.setAttribute("cust_addr2", addr2);

                    // reset cart for next sale
                    req.getSession().removeAttribute("cart");

                    req.getRequestDispatcher("/bill_print.jsp").forward(req, resp);
                    return;
                }
                default:
                    resp.sendRedirect(req.getContextPath() + "/billing");
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
