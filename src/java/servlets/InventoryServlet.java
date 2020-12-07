package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Categories;
import models.Items;
import models.Users;
import services.AccountService;
import services.InventoryService;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        AccountService as = new AccountService();
        InventoryService is = new InventoryService();
        try {
                Users user = as.get(username);
                session.setAttribute("firstname", user.getFirstName());
                session.setAttribute("lastname", user.getLastName());
               
        } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Items> items = null;
        try {
            items = is.getAllItems(username);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        List<Categories> categories = null;
        try {
            categories = is.getAllCategories();
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.setAttribute("categories", categories);
        
        request.setAttribute("firstname", session.getAttribute("firstname"));
        request.setAttribute("lastname", session.getAttribute("lastname"));
        request.setAttribute("items", items);
        request.setAttribute("categories", session.getAttribute("categories"));
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String action = request.getParameter("action");
        String categoryId_S = request.getParameter("category");
        String item = request.getParameter("name");
        String price_S = request.getParameter("price");
        String selectedItem = request.getParameter("selectedItem");
        
        InventoryService is = new InventoryService();
        
        try {
            if (action.equals("delete")) {
                int itemID = Integer.parseInt(selectedItem);
                if(is.delete(itemID)) {
                    request.setAttribute("message", "Deleting the item has been complete!");
                }
            } else if (action.equals("add")) {
                double price = Double.parseDouble(price_S);
                int categoryID = Integer.parseInt(categoryId_S);
                
                if(is.insert(categoryID, item, price, username)) {
                    request.setAttribute("message", "Adding the item has been complete!");
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Whoops.  Could not perform that action.");
        }
        
        List<Items> items = null;
        try {
            items = is.getAllItems(username);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("firstname", session.getAttribute("firstname"));
        request.setAttribute("lastname", session.getAttribute("lastname"));
        request.setAttribute("items", items);
        request.setAttribute("categories", session.getAttribute("categories"));
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }
}
