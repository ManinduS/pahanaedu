package control;

import dao.ItemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewItemsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Require login
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        req.setCharacterEncoding("UTF-8");

        String q = trim(req.getParameter("q"));
        String status = trim(req.getParameter("status"));
        if (status.isEmpty()) status = "all"; // default

        try {
            // DAO method that handles search + stock filter
            req.setAttribute("items", new ItemDAO().findAll(q, status));

            // keep current filters for the JSP if you want to read as attributes
            req.setAttribute("q", q);
            req.setAttribute("status", status);

            req.getRequestDispatcher("/viewItems.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Failed to load items", e);
        }
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
