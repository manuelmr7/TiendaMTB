<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tienda MTB</title>
        <link href="/tienda/css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Tienda MTB</h1>
        <h3>Iniciar Sesión</h3>
        <form action="/tienda/auth/login" method="POST">
            <label for="email">Correo:</label>
            <input id="email" type="text" name="email"><br />
            <label for="pwd">Clave: </label>
            <input id="pwd" type="password" name="password"><br />
            <input type="submit" value="Entrar" />
        </form>
        ${requestScope.msg}
        <a href="/tienda/users">Inicio</a>
        <a href="/tienda/user/new">Registrarse</a>
    </body>
</html>