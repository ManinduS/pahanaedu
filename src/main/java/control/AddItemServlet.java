package control;

import dao.ItemDAO;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;          // ✅ add this
import java.math.BigDecimal;

@WebServlet(name = "AddItemServlet", urlPatterns = {"/items/add"})
public class AddItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        req.getRequestDispatcher("/addItem.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        req.setCharacterEncoding("UTF-8");

        String name = trim(req.getParameter("name"));
        String description = trim(req.getParameter("description"));
        String priceStr = trim(req.getParameter("price"));
        String qtyStr = trim(req.getParameter("quantity"));

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
            setErrorAndBack(req, resp, "Please fill all fields.");
            return;
        }

        try {
            BigDecimal price = new BigDecimal(priceStr);
            int quantity = Integer.parseInt(qtyStr);

            if (price.compareTo(BigDecimal.ZERO) < 0 || quantity < 0) {
                setErrorAndBack(req, resp, "Price and quantity must be zero or positive.");
                return;
            }

            Item item = new Item(name, description, price, quantity);
            new ItemDAO().insert(item);

            // Stay on the form with success message
            req.setAttribute("success", "Item added successfully.");
            req.setAttribute("name", "");
            req.setAttribute("description", "");
            req.setAttribute("price", "");
            req.setAttribute("quantity", "");
            req.getRequestDispatcher("/addItem.jsp").forward(req, resp);

        } catch (NumberFormatException nfe) {
            setErrorAndBack(req, resp, "Enter valid numbers for price and quantity.");
        } catch (Exception e) {
            setErrorAndBack(req, resp, "Failed to add item: " + e.getMessage());
        }
    }

    private void setErrorAndBack(HttpServletRequest req, HttpServletResponse resp, String msg)
            throws ServletException, IOException {
        req.setAttribute("error", msg);
        req.setAttribute("name", trim(req.getParameter("name")));
        req.setAttribute("description", trim(req.getParameter("description")));
        req.setAttribute("price", trim(req.getParameter("price")));
        req.setAttribute("quantity", trim(req.getParameter("quantity")));
        req.getRequestDispatcher("/addItem.jsp").forward(req, resp);
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
