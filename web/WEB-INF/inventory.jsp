<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME Inventory</title>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@600&display=swap" rel="stylesheet">
        <link href="assets/css/base.css" rel="stylesheet" type="text/css"/>
        <script src="https://kit.fontawesome.com/80ee7ec6c8.js" crossorigin="anonymous"></script>
        <script src="assets/scripts/header.js" defer></script>        
    </head>
    <body>
         <header>
            <nav class="navbar">
                <div class="navbar_logo">
                    <i class="fas fa-laptop-house"></i>
                    <a href="home">Home Inventory</a>
                </div>

                <ul class="navbar_menu">
                    <li><a href="account">Account</a></li>
                    <li><a href="admin">Admin</a></li>
                    <li><a href="inventory">Inventory</a></li>
                    <c:if test="${username == null}">
                        <li><a href="login">Login</a></li>
                    </c:if>
                    <c:if test="${username != null}">
                        <li><a href="login?logout">Logout</a></li>
                    </c:if>
                </ul>

                <ul class="navbar_icons">
                    <li><a href="https://www.instagram.com/"><i class="fab fa-instagram"></i></a></li>
                    <li><a class="twitter" href="https://twitter.com/?lang=en"><i class="fab fa-twitter"></i></a></li>
                    <li><a class="facebook" href="https://www.facebook.com/"><i class="fab fa-facebook-square"></i></a></li>
                </ul>
                <a href="#" class="navbar_toogleBtn">
                    <i class="fas fa-bars"></i>
                </a>
            </nav>
        </header>
        
        <div class="admin_container">
            
            <c:if test="${deleteItem != null}">
                <script>
                    alert('Deleting the item has been completed!');
                    document.location.href="inventory";
                </script>
            </c:if>
            <c:if test="${addItem != null}">
                <script>
                    alert('Adding the item has been completed!');
                    document.location.href="inventory";
                </script>
            </c:if>                        
            <c:if test="${updateItem != null}">
                <script>
                    alert('Udating the item has been completed!');
                    document.location.href="inventory";
                </script>
            </c:if>
            <c:if test="${message != null}">
                <script>
                    alert('Whoops.  Could not perform that action.');
                    document.location.href="inventory";
                </script>
            </c:if>            
            <div class="admin_title">
                <h2>Inventory for <strong>${user.firstName} ${user.lastName}</strong></h2>
            </div>
            
            <div class="item_table">
                <h2>Item list</h2>
                <table>
                    <tr>
                        <th>Category</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Delete</th>
                        <th>Edit</th>
                    </tr>
                    <c:forEach var="item" items="${items}">
                        <tr>
                            <td>${item.category.categoryName}</td>
                            <td>${item.itemName}</td>
                            <td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${item.price}"/></td>
                            <td id="in_btn">
                                <form class="in_form" action="inventory" method="post" >
                                    <div class="btn_style">
                                        <input type="submit" value="Delete">
                                    </div>
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="selectedItem" value="${item.itemID}">
                                </form>
                            </td>
                            <td id="in_btn">
                                <form class="in_form" action="inventory" method="get" >
                                    <div class="btn_style">
                                        <input type="submit" value="Edit">
                                    </div>
                                    <input type="hidden" name="action" value="view">
                                    <input type="hidden" name="selectedItem" value="${item.itemID}">
                                </form>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
            </div>
         
            <div class="item_form">
                <c:if test="${selectedItem == null}">
                    <h3>Add Item</h3>
                    <form action="inventory" method="POST">

                        <div class="admin_input_div">
                            <p> Category </p>
                            <select class="inventory_select" name="category">
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.categoryID}">${category.categoryName}</option>
                                </c:forEach>

                            </select>
                        </div>

                        <div class="admin_input_div">
                            <p> Name </p>
                            <input type="text" name="name">
                        </div>

                        <div class="admin_input_div">
                            <p> Price </p>
                            <input type="text" name="price">
                        </div>

                        <input type="hidden" name="action" value="add">
                        <div class="submit_div">
                             <input type="submit" value="Save">
                        </div>
                    </form>                     
                </c:if>
                <c:if test="${selectedItem != null}">
                    <h3>Edit Item</h3>
                    <form action="inventory" method="POST">

                        <div class="admin_input_div">
                            <p> Category </p>
                            <select class="inventory_select" name="category">
                                <c:forEach var="category" items="${categories}">
                                    <c:choose>
                                        <c:when test="${selectedItem.category.categoryName == category.categoryName}">
                                            <option value="${category.categoryID}" selected>${category.categoryName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${category.categoryID}">${category.categoryName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="admin_input_div">
                            <p> Name </p>
                            <input type="text" name="name" value="${selectedItem.itemName}">
                        </div>

                        <div class="admin_input_div">
                            <p> Price </p>
                            <input type="text" name="price" value="<fmt:formatNumber type='number' groupingUsed = 'false' minFractionDigits='2' value='${selectedItem.price}'/>">
                        </div>
                        <input type="hidden" name="selectedItem" value="${selectedItem.itemID}">
                        <input type="hidden" name="action" value="edit">
                        <div class="submit_div">
                             <input type="submit" value="Save">
                        </div>
                    </form>                     
                </c:if>
                
            </div>

            <footer class="login_footer">
                <h3>Copyright © Aiden's Inventory Service</h3>
            </footer> 
        </div>
           
    </body>
</html>