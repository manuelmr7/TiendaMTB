<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- Camino de migas -->
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${ctx}/products">Catálogo</a></li>
        <li class="breadcrumb-item active" aria-current="page"><c:out value="${product.name}"/></li>
    </ol>
</nav>

<div class="row g-5 align-items-center">

    <!-- Foto -->
    <div class="col-12 col-md-6">
        <div class="detail-img-wrap shadow-sm">
            <c:choose>
                <c:when test="${!empty product.imageFile}">
                    <img src="${ctx}/img/${product.imageFile}" class="detail-img" alt="<c:out value='${product.name}'/>">
                </c:when>
                <c:otherwise>
                    <span class="text-muted">Sin imagen</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Datos -->
    <div class="col-12 col-md-6">
        <c:if test="${!empty product.category}">
            <span class="badge text-bg-secondary mb-2"><c:out value="${product.category.name}"/></span>
        </c:if>

        <h1 class="h2 fw-bold"><c:out value="${product.name}"/></h1>

        <p class="display-6 fw-bold text-danger my-3">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="€"/>
        </p>

        <c:choose>
            <c:when test="${product.stock > 0}">
                <p class="text-success fw-semibold">En stock (${product.stock} unidades)</p>
            </c:when>
            <c:otherwise>
                <p class="text-danger fw-semibold">Agotado</p>
            </c:otherwise>
        </c:choose>

        <h2 class="h6 text-uppercase text-muted mt-4">Descripción</h2>
        <p><c:out value="${product.descripcion}"/></p>

        <a href="${ctx}/products" class="btn btn-outline-dark mt-2">&larr; Volver al catálogo</a>
    </div>
</div>