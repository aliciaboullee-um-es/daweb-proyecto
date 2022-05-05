const express = require('express');
const router = express.Router();
const UsersController = require('../controllers/usersController')

router.get('/singup', (req, res) => {
    res.render('users/singup');
});

router.get('/index', (req, res) => {
    res.render('layouts/index');
});

router.post('/singup', async (req, res) => {
    let name = req.body.name
  let mail = req.body.mail
  let contrasena = req.body.contrasena
  console.log(name);

        const ok = await UsersController.create(name, contrasena,mail);
        if (!ok) {
            res.json({ type: 'no-disponible', alerta: { tipo: 'alert-danger', msg: 'Nombre de usuario no disponible' } });
            return;
        }

    res.json({type: 'ok', alerta: { tipo: 'alert-success', msg: 'Usuario registrado correctamente' } });
});

router.get('/singin', (req ,res) => {
    res.render('users/singin');
})

router.get('/singin/:status', (req, res) => {
    const { status } = req.params;
    if (status === 'success') {
        res.render('users/singin', { alerta: { tipo: 'alert-success', msg: 'Usuario registrado correctamente' } });
    }
    else if (status === 'out') {
        res.render('users/singin', { alerta: { tipo: 'alert-success', msg: 'Sesión cerrada correctamente' } });
    }
    else if (status === 'info'){
        res.render('users/singin', { alerta: { tipo: 'alert-info', msg: 'Antes debe iniciar sesión' } });
    }
    else {
        res.render('users/singin');
    }

});

router.post('/singin', async (req, res) => {
    let mail = req.body.mail
    let contrasena = req.body.contrasena
    

    var user = await UsersController.login(mail, contrasena);
    
    if (user !== undefined) {
        console.log('Hago login')
        res.status(200); 
        res.json({ type: 'ok', alerta: { tipo: 'alert-danger', msg: 'Login correcto' } });
        //res.redirect('index');
    }
    else {
        res.status(400); 
        res.json({ type: 'fail', alerta: { tipo: 'alert-danger', msg: 'Credenciales inválidas' } });
    }
});


router.post('/profile', async (req, res) => {
    const { username, passwd,email } = req.body;
    await UsersController.updateProfile(username, passwd,email)
    res.json({alerta: { tipo: 'alert-success', msg: 'Perfil editado correctamente'}});
});

router.get('/logout', (req, res) => {
    UsersController.logout();
    res.redirect('singin/out');
});

module.exports = router;