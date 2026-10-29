<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<h1>Detalles del Producto</h1>

<div class="product-detail">
    <h2><c:out value="${product.name}"/></h2>

    <div style="display: flex; align-items: flex-start; gap: 30px;">
        <c:if test="${!empty product.imageFile}">
            <img src="${pageContext.request.contextPath}/img/${product.imageFile}" alt="<c:out value='${product.name}'/>" width="500">
        </c:if>

        <div>
            <p><strong>Descripción:</strong> <c:out value="${product.descripcion}"/></p>
            <p><strong>Categoría:</strong> <c:out value="${product.category.name}"/></p>
            <p><strong>Precio:</strong> <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="€"/></p>
            <p><strong>Stock:</strong> ${product.stock} unidades</p>
            <a href="${pageContext.request.contextPath}/products" class="btn">Volver al catálogo</a>
        </div>
    </div>
</div>