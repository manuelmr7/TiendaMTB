<%-- 
    Document   : template
    Created on : 23 oct 2026, 11:19:45
    Author     : manue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>TiendaMTB</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    </head>
    <body>
        <!-- Cabecera común -->
        <nav>
            <a href="${pageContext.request.contextPath}/products" style="color: white; margin-right: 15px;">Catálogo</a>
            <a href="${pageContext.request.contextPath}/users" style="color: white; margin-right: 15px;">Usuarios</a>
            <c:if test="${empty sessionScope.usuario}">
                <a href="${pageContext.request.contextPath}/auth/login" style="color: white;">Entrar</a>
            </c:if>
            <c:if test="${!empty sessionScope.usuario}">
                <span style="margin-right: 15px;">Hola <c:out value="${sessionScope.usuario.name}"/></span>
                <a href="${pageContext.request.contextPath}/auth/logout" style="color: white;">Salir</a>
            </c:if>
        </nav>

        <!-- Contenido dinámico: aquí se inyectará catalogo.jsp, detalle.jsp, etc. -->
        <main class="container" style="padding: 20px; min-height: 75vh;">
            <jsp:include page="${requestScope.view}.jsp" />
        </main>

        <!-- Pie de página común -->
        <footer style="background-color: #222; color: white; text-align: center; padding: 15px; margin-top: 40px;">
            &copy; 2026. TiendaMTB - Desarrollo de Aplicaciones Web.
        </footer>
    </body>
</html>
