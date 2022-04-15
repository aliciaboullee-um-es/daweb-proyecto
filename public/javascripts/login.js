const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});

function login(e) {
    let mail = $('#login-mail').val()
    let pass = $('#login-pass').val()

    fetch('/users/singin', {
        method:'post',                    
        redirect : 'follow',
        headers : new Headers({'Content-Type':'application/json'}),
        body: JSON.stringify({'mail':mail, 'contrasena':pass })})
        .then(function(response) {
            console.log(response.status);
            if(response.status === 200) {
                location.href = "/users/index"
            }
        else{
            alert('¡Credenciales inválidas!')
        }})
        .then(resultado => {return resultado.text()})
        

}

function sendContact(e) {
    let name = $('#user-name').val()
    let mail = $('#user-mail').val()
    let pass = $('#user-contrasena').val()

    console.log(name)

    fetch('/users/singup', {
        method:'post',                    
        redirect : 'follow',
        headers : new Headers({'Content-Type':'application/json'}),
        body: JSON.stringify({'name': name, 'mail':mail, 'contrasena':pass })})
        .then(resultado => {return resultado.text()})

}

window.onload = function () {

    let btn_contact = document.querySelector('#btn-register')
    btn_contact.addEventListener('click', sendContact)

	let btnsingin = document.querySelector('#btn-singin')
    btnsingin.addEventListener('click', login)
}

 