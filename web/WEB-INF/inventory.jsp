<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
        
    </head>
    <body>
        <h1>Home Inventory</h1>
        <h4>Menu</h4>
        <ul>
            <li><a href="inventory">Inventory</a></li>
            <li><a href="admin">Admin</a></li>
            <li><a href="login?logout">Logout</a></li>
        </ul>
        <h2>Inventory for ${firstname} ${lastname}</h2>
        <table border="1" cellpadding="1px" cellspacing="2" width="70%">
            <tr>
                <th>Category</th>
                <th>Name</th>
                <th>Price</th>
                <th>Delete</th>
                
            </tr>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.category.categoryName}</td>
                    <td>${item.itemName}</td>
                    <td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${item.price}"/></td>
                    <td>
                        <form action="inventory" method="post" >
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedItem" value="${item.itemID}">
                        </form>
                    </td>
                    
                </tr>
            </c:forEach>
        </table>
        
        <h3>Add Item</h3>
        <form action="inventory" method="POST">
            Category: 
            <select name="category">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryID}">${category.categoryName}</option>
                </c:forEach>

            </select><br>
            Name: <input type="text" name="name"><br>
            Price: <input type="text" name="price"><br>
            <input type="hidden" name="action" value="add">
            <input type="submit" value="Save">
        </form>


        <p>${message}</p>
    </body>
</html>