<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2 class="h5 mb-0">Administración de productos</h2>
    <a href="${ctx}/admin/products/new" class="btn btn-danger btn-sm">+ Nuevo producto</a>
</div>

<c:if test="${!empty requestScope.products}">
    <div class="table-responsive">
        <table class="table table-striped align-middle">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Imagen</th>
                    <th>Nombre</th>
                    <th>Categoría</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${requestScope.products}">
                    <tr>
                        <td>${p.id}</td>
                        <td>
                            <c:if test="${!empty p.imageFile}">
                                <img src="${ctx}/img/${p.imageFile}" alt="<c:out value='${p.name}'/>" height="40" class="rounded">
                            </c:if>
                        </td>
                        <td><c:out value="${p.name}"/></td>
                        <td><c:out value="${p.category.name}"/></td>
                        <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="€"/></td>
                        <td>${p.stock}</td>
                        <td class="text-nowrap">
                            <a href="${ctx}/admin/products/edit?id=${p.id}" class="btn btn-outline-dark btn-sm">Editar</a>
                            <form action="${ctx}/admin/products/delete" method="POST" class="d-inline js-confirm"
                                  data-confirm="¿Eliminar este producto? Esta acción no se puede deshacer.">
                                <input type="hidden" name="id" value="${p.id}">
                                <button type="submit" class="btn btn-outline-danger btn-sm">Borrar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<c:if test="${empty requestScope.products}">
    <p class="text-muted">No hay productos todavía.</p>
</c:if>