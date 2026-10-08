<%-- 
    Document   : error
    Created on : 2 oct 2026, 10:22:30
    Author     : manue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tienda MTB - Error</title>
        <link href="/tienda/css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Tienda MTB</h1>
        <h3>Ha ocurrido un error</h3>
        <p><c:out value="${requestScope.msg}" default="La página solicitada no existe o la acción no está permitida."/></p>
        <a href="/tienda/users">Inicio</a>
    </body>
</html>