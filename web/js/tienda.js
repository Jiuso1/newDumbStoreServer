document.getElementById('barraSeleccionTelefonos').addEventListener('click', function() {
    let barraSeleccionTodo = document.getElementById('barraSeleccionTodo');
    let barraSeleccionAccesorios = document.getElementById('barraSeleccionAccesorios');
    let barraSeleccionTelefonos = document.getElementById('barraSeleccionTelefonos');
    
    barraSeleccionTodo.removeAttribute('class');
    barraSeleccionAccesorios.removeAttribute('class');
    barraSeleccionTelefonos.classList.add('barraSeleccionSubrayado');
});
document.getElementById('barraSeleccionAccesorios').addEventListener('click', function() {
    let barraSeleccionTodo = document.getElementById('barraSeleccionTodo');
    let barraSeleccionTelefonos = document.getElementById('barraSeleccionTelefonos');
    let barraSeleccionAccesorios = document.getElementById('barraSeleccionAccesorios');
    
    barraSeleccionTodo.removeAttribute('class');
    barraSeleccionTelefonos.removeAttribute('class');
    barraSeleccionAccesorios.classList.add('barraSeleccionSubrayado');
});
document.getElementById('barraSeleccionTodo').addEventListener('click', function() {
    let barraSeleccionTelefonos = document.getElementById('barraSeleccionTelefonos');
    let barraSeleccionAccesorios = document.getElementById('barraSeleccionAccesorios');
    let barraSeleccionTodo = document.getElementById('barraSeleccionTodo');
    
    barraSeleccionAccesorios.removeAttribute('class');
    barraSeleccionTelefonos.removeAttribute('class');
    barraSeleccionTodo.classList.add('barraSeleccionSubrayado');
});
