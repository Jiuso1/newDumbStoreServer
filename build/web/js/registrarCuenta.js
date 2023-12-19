let formulario = document.getElementById('registrarCuentaForm');

formulario.addEventListener("submit", function (e) {
    validateForm(e);
}, false);

function validateForm(e) {
    /*Comprueba que los campos introducidos por el formulario
     tengan sentido con nuestra lógica de negocio:*/

    //Recogemos el texto de todos los campos:
    let correo = formulario["correo"].value;
    let contrasenha = formulario["contrasenha"].value;
    let repetirContrasenha = formulario["repetirContrasenha"].value;
    let nombre = formulario["nombre"].value;
    let apellidos = formulario["apellidos"].value;

    //Rechazamos lo que no esté bien:
    if (!(contrasenha == repetirContrasenha)) {
        alert('Las contrasenhas difieren.')
        e.preventDefault();/*Hecho con ayuda de José Luis, con tal de no hacerlo intrusivo.*/
        return;//Acabamos la función.
    }
    
    if(contrasenha.length < 12){
        alert('La contraseña es débil, introduzca al menos 12 caracteres.');
        e.preventDefault();
        return;
    }

    if (!validateEmail(correo)) {
        alert('El correo introducido no es válido.');
        e.preventDefault();
        return;
    }

    if(!validarNombre(nombre)){
        alert('El nombre introducido no es válido.');
        e.preventDefault();
        return;
    }
    
    if(!validarApellidos(apellidos)){
        alert('Los apellidos introducidos no son válidos.');
        e.preventDefault();
        return;
    }

}

function validateEmail(email) {
    return String(email)
            .toLowerCase()
            .match(
                    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                    );
}
;

function validarNombre(nombre) {
    return String(nombre).length > 0;//Solo aceptaremos nombres con una cadena mayor que 0. Regex no se puede utilizar para validar nombres, dada la enorme variabilidad de estos en el español.
}
;

function validarApellidos(apellidos){
    return String(apellidos).length > 0;
}