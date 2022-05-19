
function addComercio(e) {
    let name = $('#nombre-registrar-comercio').val()
    let descripcion = $('#descripcion-registrar-comercio').val()
    let tipo = $('#tipo-registrar-comercio').val()
    let lat = $('#lat-comercio').val()
    let long = $('#long-comercio').val()
    
    console.log(name)

    fetch('/comercios/add', {
        method:'post',                    
        redirect : 'follow',
        headers : new Headers({'Content-Type':'application/json'}),
        body: JSON.stringify({'name': name, 'descripcion':descripcion, 'tipo':tipo, 'lat':lat, 'long':long })})
        .then(resultado => {return resultado.json()})
        .then(fjson => {
            if(fjson['type'] == 'ok')
                return swal("Operación exitosa", "Se ha completado el registro correctamente.", "success");
            else 
                return swal("Operación fallida", "El registro se ha interrumpido.", "error");
        })

}

window.onload = function () {

    let btn_comercio = document.querySelector('#btn-register')
    btn_comercio.addEventListener('click', addComercio)

}
