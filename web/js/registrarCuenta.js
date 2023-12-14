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
    }
}