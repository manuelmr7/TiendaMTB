<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Web App</title>
        <link href="/tienda/css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Web App</h1>
        <h3>Alta Usuario</h3>
        <form id="formulario" action="/tienda/user/save" method="POST">
            <label for="name">Nombre:</label>
            <input id="name" type="text" name="name"><br />
            <label for="email">Correo:</label>
            <input id="email" type="text" name="email"><br />
            <label for="pwd">Clave: </label>
            <input id="pwd" type="password" name="password"><br />
            <input type="submit" value="Guardar" />
        </form>
        <a href="/tienda/users">Inicio</a>
        <script src="/tienda/js/functions.js"></script>
    </body>
</html>