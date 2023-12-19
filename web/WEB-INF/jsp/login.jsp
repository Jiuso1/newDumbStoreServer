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
            String error = (String) request.getAttribute("error");
            String idCuenta = (String) request.getAttribute("idCuenta");

            if (error != null) {
                out.println("<h2>Error al iniciar sesión</h2>");
                out.println("<h2>"+error+"</h2>");
            } else {
                out.println("<h2>Ha iniciado sesión correctamente</h2>");
                out.println("<h2>El id de su cuenta es " + idCuenta + "</h2>");
                session.setAttribute("idCuenta", idCuenta);//Guardamos idCuenta en la sesión, habiéndola recibido previamente por el Controlador por medio de atrbiutos en la request.
            }
        %>
    </body>
</html>
