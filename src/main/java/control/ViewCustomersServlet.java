package control;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ViewCustomersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String q = req.getParameter("q");
        String mode = req.getParameter("mode");

        // normalize inputs
        if (q != null) q = q.trim();
        boolean manage = mode != null && mode.equalsIgnoreCase("manage");

        try {
            // fetch list (DAO already handles null/blank q if you coded it that way)
            List<Customer> customers = new CustomerDAO().list((q == null || q.isEmpty()) ? null : q);

            // pass data + current search term back to JSP
            req.setAttribute("customers", customers);
            req.setAttribute("q", q == null ? "" : q);

            // choose page
            String page = manage ? "/editCustomer.jsp" : "/viewCustomer.jsp";
            req.getRequestDispatcher(page).forward(req, resp);

        } catch (Exception e) {
            // log and fail clearly
            e.printStackTrace();
            throw new ServletException("Failed to load customers", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // allow forms to POST and still hit the same logic
        doGet(req, resp);
    }
}
