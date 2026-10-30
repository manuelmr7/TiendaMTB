<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h1 class="h2 fw-bold mb-4">Catálogo</h1>

<c:if test="${!empty requestScope.products}">
    <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4">
        <c:forEach var="p" items="${requestScope.products}">
            <div class="col">
                <div class="card product-card h-100 border-0 shadow-sm">

                    <!-- Foto del producto -->
                    <div class="product-img-wrap">
                        <c:choose>
                            <c:when test="${!empty p.imageFile}">
                                <img src="${ctx}/img/${p.imageFile}" class="product-img" alt="<c:out value='${p.name}'/>">
                            </c:when>
                            <c:otherwise>
                                <span class="product-img-empty">Sin imagen</span>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="card-body d-flex flex-column">
                        <c:if test="${!empty p.category}">
                            <span class="badge text-bg-secondary align-self-start mb-2"><c:out value="${p.category.name}"/></span>
                        </c:if>

                        <h2 class="h6 card-title"><c:out value="${p.name}"/></h2>

                        <p class="fs-4 fw-bold text-danger mt-auto mb-1">
                            <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="€"/>
                        </p>

                        <c:choose>
                            <c:when test="${p.stock > 0}">
                                <p class="small text-success mb-3">En stock</p>
                            </c:when>
                            <c:otherwise>
                                <p class="small text-danger mb-3">Agotado</p>
                            </c:otherwise>
                        </c:choose>

                        <a href="${ctx}/product?id=${p.id}" class="btn btn-dark stretched-link">Ver detalle</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>

<c:if test="${empty requestScope.products}">
    <p class="text-muted">No hay productos disponibles en el catálogo en este momento.</p>
</c:if>