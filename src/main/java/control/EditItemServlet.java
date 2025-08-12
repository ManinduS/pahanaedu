package control;

import dao.ItemDAO;
import model.Item;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class EditItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Item item = new ItemDAO().findById(id);
            if (item == null) { resp.sendRedirect(req.getContextPath()+"/items"); return; }
            req.setAttribute("item", item);
            req.getRequestDispatcher("/editItem.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Item item = new Item(
                    Integer.parseInt(req.getParameter("id")),
                    req.getParameter("name"),
                    req.getParameter("description"),
                    new BigDecimal(req.getParameter("price")),
                    Integer.parseInt(req.getParameter("quantity"))
            );
            new ItemDAO().update(item);
            resp.sendRedirect(req.getContextPath()+"/items");
        } catch (Exception e) {
            req.setAttribute("error", "Failed to update item: "+e.getMessage());
            req.getRequestDispatcher("/editItem.jsp").forward(req, resp);
        }
    }
}
