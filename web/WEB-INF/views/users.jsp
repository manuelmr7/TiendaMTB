<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tienda MTB</title>
        <link href="/tienda/css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <nav> | <a href="/tienda/user/new">Crear Nuevo Usuario</a> | </nav>
        <h1>Tienda MTB</h1>

        <c:if test="${!empty requestScope.users}">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                </tr>
                <c:forEach var="user" items="${requestScope.users }" >
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty requestScope.users}">
            <p>Oops! No hay Usuarios todavía!</p>
        </c:if>
            <<script src="/tienda/js/functions.js"></script>
    </body>
</html>