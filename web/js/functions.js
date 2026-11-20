// Chequear userRegisterForm
let userRegisterForm = document.getElementById("userRegisterForm");
if (userRegisterForm) {
    userRegisterForm.addEventListener("submit", checkUserRegisterForm);
}

function checkUserRegisterForm(event) {
    let ok = true;
    let errorName = document.getElementById("nameError");
    let errorEmail = document.getElementById("emailError");
    let errorPwd1 = document.getElementById("pwd1Error");
    let errorPwd2 = document.getElementById("pwd2Error");
    let name = document.getElementById("name");
    let email = document.getElementById("emailRegister");
    let pwd1 = document.getElementById("pwd1");
    let pwd2 = document.getElementById("pwd2");
    let emailRegEx = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;


    if (name.value.trim().length === 0) {
        errorName.textContent = "El nombre es obligatorio, inserta un nombre.";
        name.setAttribute("class", "form-control error");
        event.preventDefault();
        ok = false;
    } else {
        name.setAttribute("class", "form-control ok");
        errorName.textContent = "";
    }

    if (email.value.length === 0) {
        errorEmail.textContent = "El email es obligatorio, inserta un email.";
        email.setAttribute("class", "form-control error");
        event.preventDefault();
        ok = false;
    } else if (!emailRegEx.test(email.value)) {
        errorEmail.textContent = "Email incorrecto, inserta un email válido.";
        email.setAttribute("class", "form-control error");
        event.preventDefault();
        ok = false;
    } else {
        email.setAttribute("class", "form-control ok");
        errorEmail.textContent = "";
    }

    if (pwd1.value.length < 8) {
        errorPwd1.textContent = "Debes introducir una contraseña de al menos 8 caracteres.";
        pwd1.setAttribute("class", "form-control error");
        event.preventDefault();
        ok = false;
    } else {
        pwd1.setAttribute("class", "form-control ok");
        errorPwd1.textContent = "";
    }

    if (pwd2.value.length < 8) {
        errorPwd2.textContent = "Debes introducir una contraseña de al menos 8 caracteres.";
        pwd2.setAttribute("class", "form-control error");
        event.preventDefault();
        ok = false;
    } else if (pwd1.value !== pwd2.value) {
        errorPwd2.textContent = "Las contraseñas no coindicen.";
        pwd2.setAttribute("class", "form-control error");
        event.preventDefault();
        ok = false;
    } else {
        pwd2.setAttribute("class", "form-control ok");
        errorPwd2.textContent = "";
    }

    return ok;
}
// Confirmación antes de borrar (formularios con class="js-confirm")
document.querySelectorAll("form.js-confirm").forEach(function (form) {
    form.addEventListener("submit", function (event) {
        if (!confirm(form.dataset.confirm)) {
            event.preventDefault();
        }
    });
});

// ---------- Fetch: comprobar si el email ya está en uso ----------
let emailRegister = document.getElementById("emailRegister");
if (emailRegister) {
    emailRegister.addEventListener("change", emailDuplicadoFetch);
}

async function emailDuplicadoFetch() {
    let email = document.getElementById("emailRegister");
    let duplicado = document.getElementById("emailInUse");
    duplicado.textContent = "";   // se borra el mensaje anterior

    // Si el formato no es válido, no se pregunta al servidor
    let emailRegEx = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegEx.test(email.value)) {
        return;
    }

    try {
        // URLSearchParams codifica el valor (por ejemplo, "+" o "&")
        let datos = new URLSearchParams({email: email.value});
        let response = await fetch(email.dataset.url, {
            method: "POST",
            body: datos
        });

        if (response.ok) {   // si el estado HTTP es 200-299
            let respuesta = await response.text();
            let libre = respuesta.includes("Ok");

            let span = document.createElement("span");
            span.setAttribute("class", libre ? "text-success" : "text-danger");
            span.textContent = libre ? "Email disponible" : respuesta;
            duplicado.appendChild(span);
        } else {
            console.log("Error al comprobar el email");
        }
    } catch (error) {
        console.log("No se ha podido comprobar el email", error);
    }
}