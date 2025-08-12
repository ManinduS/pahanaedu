package control;

import dao.ItemDAO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class ViewItemsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String q = req.getParameter("q");
            req.setAttribute("items", new ItemDAO().findAll(q));
            req.getRequestDispatcher("/viewItems.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
