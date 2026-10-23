<%-- 
    Document   : detalle
    Created on : 23 oct 2026, 10:45:47
    Author     : manue
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${product.name} - TiendaMTB</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    </head>
    <body>
        <div class="container">
            <h1>Detalles del Producto</h1>

            <div class="product-detail">
                <h2>${product.name}</h2><div style="display: flex; align-items: flex-start; gap: 30px;">
                    <img src="${pageContext.request.contextPath}/img/${product.imageFile}" alt="${product.name}" width="500">
                    <div>
                        <p><strong>Precio:</strong> $${product.price}</p>
                        <p><strong>Descripción:</strong> ${product.descripcion}</p>                        
                        <a href="${pageContext.request.contextPath}/products" class="btn">Volver al catálogo</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>