package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;


public class ActiveServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        
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
                System.out.println("Your account has been activated!");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } else {
            as.activationEmail(email, path, url);
            System.out.println("Activation email has been sent to " + email + ".");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        
    }
}
