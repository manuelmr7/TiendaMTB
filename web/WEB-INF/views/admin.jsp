<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="tab" value="${empty requestScope.tab ? 'users' : requestScope.tab}"/>

<h1 class="h2 fw-bold mb-4">Panel de administración</h1>

<ul class="nav nav-tabs" id="adminTabs" role="tablist">
    <li class="nav-item" role="presentation">
        <button class="nav-link ${tab == 'users' ? 'active' : ''}" id="users-tab" data-bs-toggle="tab"
                data-bs-target="#users" type="button" role="tab" aria-controls="users"
                aria-selected="${tab == 'users'}">Usuarios</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link ${tab == 'products' ? 'active' : ''}" id="products-tab" data-bs-toggle="tab"
                data-bs-target="#products" type="button" role="tab" aria-controls="products"
                aria-selected="${tab == 'products'}">Productos</button>
    </li>
</ul>

<div class="tab-content mt-3">
    <div class="tab-pane fade ${tab == 'users' ? 'show active' : ''}" id="users" role="tabpanel" aria-labelledby="users-tab">
        <jsp:include page="admin/users.jsp" />
    </div>
    <div class="tab-pane fade ${tab == 'products' ? 'show active' : ''}" id="products" role="tabpanel" aria-labelledby="products-tab">
        <jsp:include page="admin/products.jsp" />
    </div>
</div>