package control;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    // 🔐 Hardcoded credentials
    private static final String HARDCODED_USERNAME = "admin";
    private static final String HARDCODED_PASSWORD = "12345";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (HARDCODED_USERNAME.equals(username) && HARDCODED_PASSWORD.equals(password)) {
            // ✅ Successful login
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("welcome.jsp");
        } else {
            // ❌ Invalid login
            request.setAttribute("error", "Invalid username or password!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }
}
