package control;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UpdateCustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // Parse & validate ID
        String idStr = req.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idStr);
            if (id <= 0) throw new NumberFormatException("non-positive id");
        } catch (Exception ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer id");
            return;
        }

        // Read/trim fields
        String name     = safeTrim(req.getParameter("name"));
        String surname  = safeTrim(req.getParameter("surname"));
        String phone    = safeTrim(req.getParameter("phone"));
        String address1 = safeTrim(req.getParameter("address1"));
        String address2 = safeTrim(req.getParameter("address2"));
        String province = safeTrim(req.getParameter("province"));
        String email    = safeTrim(req.getParameter("email"));

        // Minimal validation
        if (name == null || name.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is required");
            return;
        }

        Customer c = new Customer();
        c.setId(id);
        c.setName(name);
        c.setSurname(surname);
        c.setPhone(phone);
        c.setAddress1(address1);
        c.setAddress2(address2);
        c.setProvince(province);
        c.setEmail(email);

        try {
            new CustomerDAO().update(c);

            // Preserve search filter if present
            String q = safeTrim(req.getParameter("q"));
            String redirect = req.getContextPath() + "/customers?mode=manage";
            if (q != null && !q.isEmpty()) {
                redirect += "&q=" + URLEncoder.encode(q, StandardCharsets.UTF_8.name());
            }
            resp.sendRedirect(redirect);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Failed to update customer", e);
        }
    }

    private static String safeTrim(String s) {
        return (s == null) ? null : s.trim();
    }
}
