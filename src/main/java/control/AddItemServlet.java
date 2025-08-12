package control;

import dao.ItemDAO;
import model.Item;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class AddItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Item item = new Item(
                    req.getParameter("name"),
                    req.getParameter("description"),
                    new BigDecimal(req.getParameter("price")),
                    Integer.parseInt(req.getParameter("quantity"))
            );
            new ItemDAO().insert(item);
            resp.sendRedirect(req.getContextPath() + "/items"); // go to list
        } catch (Exception e) {
            req.setAttribute("error", "Failed to add item: " + e.getMessage());
            req.getRequestDispatcher("/addItem.jsp").forward(req, resp);
        }
    }
}
