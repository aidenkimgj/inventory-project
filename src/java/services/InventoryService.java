package services;

import dataaccess.CategoriesDB;
import dataaccess.ItemsDB;
import dataaccess.UsersDB;
import java.util.List;
import models.Categories;
import models.Items;
import models.Users;

public class InventoryService {
    
    private CategoriesDB categoryDB;
    private ItemsDB itemDB;
    
    public InventoryService() {
        categoryDB = new CategoriesDB();
        itemDB = new ItemsDB();
    }
    public List<Categories> getAllCategories() throws Exception {
        return categoryDB.getAllCategories();
    }

    public List<Items> getAllItems(String username) throws Exception {
        return itemDB.getAllItems(username);
    }

    public boolean delete(int itemID) throws Exception {
        Items item = itemDB.get(itemID);
        return itemDB.delete(item);
    }

    public boolean insert(int categoryID, String itemName, double price, String owner) throws Exception {
        Items item = new Items();
        UsersDB userDB = new UsersDB();
        Categories category = categoryDB.get(categoryID);
        Users user = userDB.getUser(owner);
       
        item.setItemName(itemName);
        item.setPrice(price);
        item.setOwner(user);
        item.setCategory(category);
          
        return itemDB.insert(item);
    }
    
}