<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="row justify-content-center">
    <div class="col-12 col-md-8 col-lg-5">
        <div class="card border-0 shadow-sm">
            <div class="card-body p-4">
                <h1 class="h3 fw-bold mb-1">Crear cuenta</h1>
                <p class="text-muted mb-4">Regístrate para comprar en TiendaMTB.</p>

                <form id="userRegisterForm" action="${pageContext.request.contextPath}/user/save" method="POST">

                    <div class="mb-3">
                        <label for="name" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="name" name="name" />
                        <span id="nameError" class="text-danger"></span>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="text" class="form-control" id="emailRegister" name="email" />
                        <span id="emailError" class="text-danger"></span>
                        <div id="emailInUse"></div>
                    </div>
                    <div class="mb-3">
                        <label for="password1" class="form-label">Password</label>
                        <input type="password" class="form-control" id="pwd1" name="pwd1" />
                        <span id="pwd1Error" class="text-danger"></span>
                    </div>
                    <div class="mb-3">
                        <label for="password2" class="form-label">Repite Password</label>
                        <input type="password" class="form-control" id="pwd2" name="pwd2" />
                        <span id="pwd2Error" class="text-danger"></span>
                    </div>

                    <button type="submit" class="btn btn-danger w-100">Registrarse</button>
                </form>

                <p class="text-center mt-3 mb-0">
                    ¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/auth/login">Entra aquí</a>
                </p>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/functions.js"></script>