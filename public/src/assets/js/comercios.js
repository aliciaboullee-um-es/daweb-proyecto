
function addComercio(e) {
    let name = $('#nombre-registrar-comercio').val()
    let descripcion = $('#descripcion-registrar-comercio').val()
    let tipo = $('#tipo-registrar-comercio').val()
    let lat = $('#lat-comercio').val()
    let lng = $('#long-comercio').val()
    
    console.log(tipo)

    fetch('/comercios/add', {
        method:'post',                    
        redirect : 'follow',
        headers : new Headers({'Content-Type':'application/json'}),
        body: JSON.stringify({'nombre': name, 'descripcion':descripcion, 'tipo':tipo, 'lat':lat, 'lng':lng })})
        .then(resultado => {return resultado.json()})
        .then(fjson => {
            if(fjson['type'] == 'ok')
                return swal("Operación exitosa", "Se ha completado el registro correctamente.", "success");
            else 
                return swal("Operación fallida", "El registro se ha interrumpido.", "error");
        })

}

window.onload = function () {

    fetch('/comercios/check', {
        method:'post',                    
        redirect : 'follow',
        headers : new Headers({'Content-Type':'application/json'}),
        body: JSON.stringify({'mail': window.localStorage.getItem("mail") })})
        .then(resultado => {return resultado.json()})
        .then(fjson => {
            if(fjson['type'] != 'ok')
            {
                swal("Operación fallida", "Necesita ser administrador.", "error");
                setTimeout( function() { window.location.href = "/comercios/error"; }, 3000 );
            }
                
            
        })

    let btn_comercio = document.querySelector('#btn-register')
    btn_comercio.addEventListener('click', addComercio)

}
