<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="row justify-content-center">
    <div class="col-12 col-lg-8">
        <div class="card border-0 shadow-sm">
            <div class="card-body p-4">
                <h1 class="h3 fw-bold mb-4">${empty product.id ? 'Nuevo producto' : 'Editar producto'}</h1>

                <form action="${ctx}/admin/products/save" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${product.id}">

                    <div class="mb-3">
                        <label for="name" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="name" name="name" maxlength="255"
                               value="<c:out value='${product.name}'/>">
                    </div>

                    <div class="mb-3">
                        <label for="categoryId" class="form-label">Categoría</label>
                        <select class="form-select" id="categoryId" name="categoryId">
                            <option value="">Elige una categoría</option>
                            <c:forEach var="cat" items="${requestScope.categories}">
                                <option value="${cat.id}" ${product.category.id == cat.id ? 'selected' : ''}>
                                    <c:out value="${cat.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="row">
                        <div class="col-6 mb-3">
                            <label for="price" class="form-label">Precio (€)</label>
                            <input type="text" class="form-control" id="price" name="price"
                                   value="<c:out value='${requestScope.priceText}'/>">
                        </div>
                        <div class="col-6 mb-3">
                            <label for="stock" class="form-label">Stock</label>
                            <input type="text" class="form-control" id="stock" name="stock"
                                   value="<c:out value='${requestScope.stockText}'/>">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="descripcion" class="form-label">Descripción</label>
                        <textarea class="form-control" id="descripcion" name="descripcion" rows="5" maxlength="1000"><c:out value="${product.descripcion}"/></textarea>
                    </div>

                    <div class="mb-4">
                        <label for="image" class="form-label">Imagen</label>
                        <input type="file" class="form-control" id="image" name="image"
                               accept="image/jpeg,image/png,image/webp">
                        <div class="form-text">JPG, PNG o WEBP, máximo 2 MB. Si no eliges ninguna, se conserva la actual.</div>
                        <c:if test="${!empty product.imageFile}">
                            <img src="${ctx}/img/${product.imageFile}" alt="Imagen actual" height="90" class="rounded border mt-2">
                        </c:if>
                    </div>

                    <button type="submit" class="btn btn-danger">Guardar</button>
                    <a href="${ctx}/admin?tab=products" class="btn btn-outline-secondary">Cancelar</a>
                </form>
            </div>
        </div>
    </div>
</div>