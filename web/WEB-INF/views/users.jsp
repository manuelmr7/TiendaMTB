<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tienda MTB - Usuarios</title>
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <nav>
            | <a href="${pageContext.request.contextPath}/products">Catálogo</a> |
            <a href="${pageContext.request.contextPath}/user/new">Crear Nuevo Usuario</a> |
            <c:if test="${empty sessionScope.usuario}">
                <a href="${pageContext.request.contextPath}/auth/login">Entrar</a> |
            </c:if>
            <c:if test="${!empty sessionScope.usuario}">
                Hola <c:out value="${sessionScope.usuario.name}"/> |
                <a href="${pageContext.request.contextPath}/auth/logout">Salir</a> |
            </c:if>
        </nav>
        <h1>Tienda MTB</h1>

        <c:if test="${!empty requestScope.users}">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                </tr>
                <c:forEach var="user" items="${requestScope.users}">
                    <tr>
                        <td>${user.id}</td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.email}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty requestScope.users}">
            <p>Oops! No hay Usuarios todavía!</p>
        </c:if>
    </body>
</html>