package control;

import dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DeleteCustomerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String idStr = req.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idStr);
            if (id <= 0) throw new NumberFormatException("non-positive id");
        } catch (Exception ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer id");
            return;
        }

        try {
            new CustomerDAO().delete(id);

            // Keep current search filter (if any) when returning to manage page
            String q = req.getParameter("q");
            String redirect = req.getContextPath() + "/customers?mode=manage";
            if (q != null && !q.trim().isEmpty()) {
                redirect += "&q=" + URLEncoder.encode(q.trim(), StandardCharsets.UTF_8.name());
            }
            resp.sendRedirect(redirect);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Failed to delete customer", e);
        }
    }
}
