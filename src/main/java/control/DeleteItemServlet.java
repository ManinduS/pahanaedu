package control;

import dao.ItemDAO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            new ItemDAO().delete(id);
            resp.sendRedirect(req.getContextPath()+"/items");
        } catch (Exception e) { throw new ServletException(e); }
    }
}
