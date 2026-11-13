<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="row justify-content-center">
    <div class="col-12 col-md-8 col-lg-5">
        <div class="card border-0 shadow-sm">
            <div class="card-body p-4">
                <h1 class="h3 fw-bold mb-1">Iniciar sesión</h1>
                <p class="text-muted mb-4">Accede a tu cuenta de TiendaMTB.</p>

                <form action="${pageContext.request.contextPath}/auth/login" method="POST">
                    <div class="mb-3">
                        <label for="email" class="form-label">Correo electrónico</label>
                        <input id="email" type="email" name="email" class="form-control" autocomplete="username" required>
                    </div>
                    <div class="mb-3">
                        <label for="pwd" class="form-label">Clave</label>
                        <input id="pwd" type="password" name="password" class="form-control" autocomplete="current-password" required>
                    </div>
                    <button type="submit" class="btn btn-danger w-100">Entrar</button>
                </form>

                <p class="text-center mt-3 mb-0">
                    ¿No tienes cuenta? <a href="${pageContext.request.contextPath}/user/new">Regístrate</a>
                </p>
            </div>
        </div>
    </div>
</div>