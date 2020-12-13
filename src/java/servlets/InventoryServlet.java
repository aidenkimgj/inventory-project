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
import services.CategoryService;
import services.InventoryService;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String action = request.getParameter("action");
        AccountService as = new AccountService();
        InventoryService is = new InventoryService();
        CategoryService cs = new CategoryService();
        
        if (action != null && action.equals("view")) {
            int selectedItem = Integer.parseInt(request.getParameter("selectedItem"));
            try {
                Items item = is.get(selectedItem);
                request.setAttribute("selectedItem", item);
            } catch (Exception ex) {
                    Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Items> items = null;
        try {
            Users user = as.get(username);
            session.setAttribute("user", user);
            items = is.getAllItems(username);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        List<Categories> categories = null;
        try {
            categories = cs.getAllCategories();
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.setAttribute("categories", categories);
        request.setAttribute("items", items);
        
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
        int itemID;
        double price;
        int categoryID;
        InventoryService is = new InventoryService();

        try {
            switch (action) {
                case "delete":
                    itemID = Integer.parseInt(selectedItem);
                    if(is.delete(itemID)) {
                        request.setAttribute("deleteItem", "Deleting the item has been completed!");
                    }   break;
                case "add":
                    price = Double.parseDouble(price_S);
                    categoryID = Integer.parseInt(categoryId_S);
                    if(is.insert(categoryID, item, price, username)) {
                        request.setAttribute("addItem", "Adding the item has been completed!");
                    }   break;

                default:
                    itemID = Integer.parseInt(selectedItem);
                    price = Double.parseDouble(price_S);
                    categoryID = Integer.parseInt(categoryId_S);

                    if(is.update(itemID, categoryID, item, price))
                        request.setAttribute("updateItem", "Udating the item has been completed!");
                    break;
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
  
       request.setAttribute("items", items);

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }
}
