<%-- 
    Document   : catalogo
    Created on : 9 oct 2026, 10:13:05
    Author     : manue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tienda MTB - Catálogo</title>
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <nav>
            | <a href="${pageContext.request.contextPath}/users">Inicio</a> |
            <c:if test="${empty sessionScope.usuario}">
                <a href="${pageContext.request.contextPath}/auth/login">Entrar</a> |
            </c:if>
            <c:if test="${!empty sessionScope.usuario}">
                Hola ${sessionScope.usuario.name} |
                <a href="${pageContext.request.contextPath}/auth/logout">Salir</a> |
            </c:if>
        </nav>
        <h1>Tienda MTB</h1>
        <h3>Catálogo</h3>
        <c:if test="${!empty requestScope.products}">
            <table>
                <tr>
                    <th>Nombre</th>
                    <th>Categoría</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Foto</th>
                    <th>Detalle</th>
                </tr>
                <c:forEach var="p" items="${requestScope.products}">
                    <tr>
                        <td>${p.name}</td>
                        <td>${p.category.name}</td>
                        <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="€"/></td>
                        <td>${p.stock}</td>
                        <td>
                            <c:if test="${!empty p.imageFile}">
                                <img src="${pageContext.request.contextPath}/img/${p.imageFile}" alt="${p.name}" width="120">
                            </c:if>
                        </td>
                        <td><a href="${pageContext.request.contextPath}/product?id=${p.id}">Ver</a></td>
                    </tr>
                </c:forEach>
                </table>
        </c:if>
        <c:if test="${empty requestScope.products}">
            <p>Todavía no hay productos.</p>
        </c:if>
    </body>
</html>
