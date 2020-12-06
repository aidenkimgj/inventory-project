<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME Inventory</title>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@600&display=swap" rel="stylesheet">
        <link href="assets/css/base.css" rel="stylesheet" type="text/css"/>
        <script src="https://kit.fontawesome.com/80ee7ec6c8.js" crossorigin="anonymous"></script>
        
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
                    <li><a href="login">Login</a></li>
                </ul>

                <ul class="navbar_icons">
                    <li><a href="https://www.instagram.com/"><i class="fab fa-instagram"></i></a></li>
                    <li><a href="https://twitter.com/?lang=en"><i class="fab fa-twitter"></i></a></li>
                    <li><a href="https://www.facebook.com/"><i class="fab fa-facebook-square"></i></a></li>
                </ul>
                <a href="#" class="navbar_toogleBtn">
                    <i class="fas fa-bars"></i>
                </a>
            </nav>
        </header>
        
        <div class="create_container">
            <form action="create" method="post">
                <div class="create_intro">
                    <i class="fas fa-laptop-house"></i>
                    <h2>Create your Account</h2>
                </div>
                <div class="create_div">
                    <div class="create_input_div">
                        <p> First name </p>
                        <input type="text" name="firstname" placeholder="Enter First name"/>
                    </div>
                    
                    <div class="create_input_div">
                        <p> Last name </p>
                        <input type="text" name="lastname" placeholder="Enter Last name"/>
                    </div>
                    
                    <div class="create_input_div">
                        <p> Username </p>
                        <input type="text" name="username" placeholder="Enter Username"/>
                    </div>
                    
                    <div class="create_input_div">
                        <p> Email </p>
                        <input type="text" name="email" placeholder="Enter Email"/>
                    </div>
                    
                    <div class="create_input_div">
                        <p> Password </p>
                        <input type="password" name="password" placeholder="Enter Password"/>    
                    </div>
                   
                    <div class="submit_div">
                        <input type="submit" value="Create account">
                    </div>
                </div>    
            </form>

        </div>
        
        <footer>
            <h3>Copyright © Aiden's Inventory Service</h3>
        </footer>
    </body>
</html>