function sendUser(e){
    let name = $('#user-name').val();
    let mail = $('#user-mail').val();
    let contrasena = $('#user-contrasena').val();

    fetch('users/register', {
        method : 'post',
        redirect : 'follow',
        headers : new Headers({'Content-Type': 'application/json'}),
        body : JSON.stringify({'name' : name, 'mail' : mail, 'contrasena' : contrasena})
    })
    .then(respuesta => {return respuesta.text()})
    .then(resultado => {
        $('#user-result').text(resultado);
    })
}

window.onload = function () {
    let btn_user = document.querySelector('#btn-register')
    btn_user.addEventListener('click', sendUser)
}