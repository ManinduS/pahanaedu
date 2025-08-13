package control;

import dao.ItemDAO;
import dao.OrderDAO;
import dao.CustomerDAO;
import model.Cart;
import model.Item;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BillingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Cart getCart(HttpServletRequest req) {
        HttpSession s = req.getSession();
        Cart cart = (Cart) s.getAttribute("cart");
        if (cart == null) { cart = new Cart(); s.setAttribute("cart", cart); }
        return cart;
    }

    private void setSelectedCustomer(HttpServletRequest req, Customer c) {
        req.getSession().setAttribute("billingCustomer", c);
    }
    private Customer getSelectedCustomer(HttpServletRequest req) {
        return (Customer) req.getSession().getAttribute("billingCustomer");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Preselect via /billing?customerId=123
            String cid = req.getParameter("customerId");
            if (cid != null && !cid.isEmpty()) {
                Customer c = new CustomerDAO().findById(Integer.parseInt(cid));
                if (c != null) setSelectedCustomer(req, c);
            }

            // Item search
            String q = req.getParameter("q");
            if (q != null && !q.trim().isEmpty()) {
                List<Item> results = new ItemDAO().searchByName(q.trim());
                req.setAttribute("results", results);
            }

            // Customer quick search (?custq=...)
            String cq = req.getParameter("custq");
            if (cq != null && !cq.trim().isEmpty()) {
                List<Customer> custResults = new CustomerDAO().searchByNameOrPhone(cq.trim());
                req.setAttribute("custResults", custResults);
            }

            req.getRequestDispatcher("/billing.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) { resp.sendRedirect(req.getContextPath() + "/billing"); return; }

        Cart cart = getCart(req);
        ItemDAO itemDAO = new ItemDAO();

        try {
            switch (action) {
                case "selectCustomer": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Customer c = new CustomerDAO().findById(id);
                    if (c != null) setSelectedCustomer(req, c);
                    resp.sendRedirect(req.getContextPath() + "/billing");
                    return;
                }
                case "clearCustomer": {
                    req.getSession().removeAttribute("billingCustomer");
                    resp.sendRedirect(req.getContextPath() + "/billing");
                    return;
                }
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
                    if (cart.isEmpty()) { resp.sendRedirect(req.getContextPath() + "/billing"); return; }
                    Customer c = getSelectedCustomer(req);
                    if (c == null) { resp.sendRedirect(req.getContextPath() + "/billing?needCustomer=1"); return; }

                    Integer cid  = c.getId();
                    String name  = (c.getSurname()==null || c.getSurname().isEmpty())
                            ? c.getName() : (c.getName()+" "+c.getSurname());
                    String phone = c.getPhone();
                    String a1    = c.getAddress1();
                    String a2    = c.getAddress2();

                    int orderId = new OrderDAO().saveOrder(cid, name, phone, a1, a2, cart);

                    req.setAttribute("orderId", "B" + orderId);
                    req.setAttribute("cart", cart);
                    req.setAttribute("cust_name", name);
                    req.setAttribute("cust_phone", phone);
                    req.setAttribute("cust_addr1", a1);
                    req.setAttribute("cust_addr2", a2);

                    req.getSession().removeAttribute("cart");
                    req.getSession().removeAttribute("billingCustomer");

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
