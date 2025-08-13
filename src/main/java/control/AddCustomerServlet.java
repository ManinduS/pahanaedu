package control;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AddCustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // Read and trim parameters
        String name     = trim(req.getParameter("name"));
        String surname  = trim(req.getParameter("surname"));
        String phone    = trim(req.getParameter("phone"));
        String address1 = trim(req.getParameter("address1"));
        String address2 = trim(req.getParameter("address2"));
        String province = trim(req.getParameter("province"));
        String email    = trim(req.getParameter("email"));

        // Minimal validation: Name is mandatory
        if (name == null || name.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is required");
            return;
        }

        Customer c = new Customer();
        c.setName(name);
        c.setSurname(surname);
        c.setPhone(phone);
        c.setAddress1(address1);
        c.setAddress2(address2);
        c.setProvince(province);
        c.setEmail(email);

        try {
            new CustomerDAO().add(c);
            // Redirect to view customers list after successful add
            resp.sendRedirect(req.getContextPath() + "/customers");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Failed to add customer", e);
        }
    }

    private String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
