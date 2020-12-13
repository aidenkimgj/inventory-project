package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Users;
import services.AccountService;

public class ActiveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String welcome = request.getParameter("welcome");
        if (welcome != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        
        if (uuid != null) {
            request.setAttribute("uuid", uuid);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/activation.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uuid = request.getParameter("uuid");
        String url = request.getRequestURL().toString();
        String email = request.getParameter("email");
        String path = getServletContext().getRealPath("/WEB-INF");
        
        AccountService as = new AccountService();
        
        if (uuid != null) {
            if (as.activateAccount(uuid)) {
                request.setAttribute("activated", "Your account has been activated!");
                try {
                    Users user = as.getUserByUUID(uuid);
                    String userEmail = user.getEmail();
                    as.welcomeEmail(userEmail, path, url);
                } catch (Exception ex) {
                    Logger.getLogger(ActiveServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } else {
            if (as.activationEmail(email, path, url)){
                request.setAttribute("email", "Account activation email has been sent to your email which has been registered our service.");
            } else {
                request.setAttribute("account", "Your account already activated.");
            }
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
