package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.AccountService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("logout");
        
        if (action != null) {
            session.invalidate();
            session = request.getSession();
            request.setAttribute("logout", "logout");
        }
        
        if (session.getAttribute("username") == null ) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else if (session.getAttribute("username").equals("admin")){
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("inventory");
        } 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        AccountService as = new AccountService();
        try {
            Users user = as.login(username, password);
            
            if (user == null) {
                request.setAttribute("login", "Invalid login");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
                
            if (!user.getActive()) {
                request.setAttribute("active", "You are non-active user!");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("_user", user);
                session.setAttribute("username", username);
                session.setAttribute("role", user.getRole().getRoleid());
                
                if (user.getRole().getRoleid()== 1) {
                    response.sendRedirect("admin");
                } else {
                    response.sendRedirect("inventory");
                }
            }
        } catch (Exception ex) {
            
        }
    }
}
