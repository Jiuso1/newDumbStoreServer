<%-- 
    Document   : login
    Created on : 19 dic 2023, 13:03:36
    Author     : jesus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login</title>
        <link rel="stylesheet" type="text/css" href="..\css\estilo.css"/>
        <link rel="icon" href="..\media\photo\favicon.png">
    </head>
    <body>
        <div class="imagenLogin">
            <div class="imagenCentrada">
                <img src="..\media\photo\favicon.png">
            </div>
        </div>
        <%
            String correo = request.getParameter("correo");
            String contrasenha = request.getParameter("contrasenha");
            String xd = (String) request.getAttribute("xd");
            out.println(correo);
            out.println(contrasenha);
            out.println(xd);
        %>
    </body>
</html>
