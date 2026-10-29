<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<h1>Detalles del Producto</h1>

<div class="product-detail">
    <h2>${product.name}</h2>

    <div style="display: flex; align-items: flex-start; gap: 30px;">
        <img src="${pageContext.request.contextPath}/img/${product.imageFile}" alt="${product.name}" width="500">
        
        <div>
            <p><strong>Descripción:</strong> ${product.descripcion}</p>
            <p><strong>Precio:</strong> $${product.price}</p>
            <a href="${pageContext.request.contextPath}/products" class="btn">Volver al catálogo</a>
        </div>
    </div>
</div>