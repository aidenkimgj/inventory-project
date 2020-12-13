package services;

import dataaccess.CategoriesDB;
import java.util.List;
import models.Categories;

public class CategoryService {
    private CategoriesDB categoryDB;
    
    public CategoryService() {
        categoryDB = new CategoriesDB();
    }
    
    public List<Categories> getAllCategories() throws Exception {
        return categoryDB.getAllCategories();
    }
    
    public Categories get(int selectedCategory) throws Exception {
        return categoryDB.get(selectedCategory);
    }

    public boolean insert(String categoryName) throws Exception {
        Categories category = new Categories();
        
        category.setCategoryName(categoryName);
        
        return categoryDB.insert(category);
    }

    public boolean update(String categoryID, String categoryName) throws Exception {
        int categoryId = Integer.parseInt(categoryID);
        Categories category = new Categories(categoryId, categoryName);
        
        return categoryDB.update(category);
    }
    
    public boolean delete(String categoryID) throws Exception {
        int categoryId = Integer.parseInt(categoryID);
        Categories category = categoryDB.get(categoryId);
        return categoryDB.delete(category);
    }    
}
