<%-- 
    Document   : catalogo
    Created on : 9 oct 2026, 10:13:05
    Author     : manue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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
                        <c:if test="${!empty p.imageFile}">
                            <img src="${pageContext.request.contextPath}/img/${p.imageFile}" alt="<c:out value='${p.name}'/>" width="80">
                        </c:if>
                    </td>
                    <td><c:out value="${p.name}"/></td>
                    <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="€"/></td>
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