<%-- 
    Document   : catalogo
    Created on : 9 oct 2026, 10:13:05
    Author     : manue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<h1>Catálogo de Productos</h1>

<c:if test="${!empty requestScope.products}">
    <table>
        <thead>
            <tr>
                <th>Imagen</th>
                <th>Nombre</th>
                <th>Precio</th>
                <th>Stock</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${requestScope.products}">
                <tr>
                    <td>
                        <img src="${pageContext.request.contextPath}/img/${p.imageFile}" alt="${p.name}" width="80">
                    </td>
                    <td>${p.name}</td>
                    <td>$${p.price}</td>
                    <td>${p.stock}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/product?id=${p.id}" class="btn">Ver</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty requestScope.products}">
    <p>No hay productos disponibles en el catálogo en este momento.</p>
</c:if>