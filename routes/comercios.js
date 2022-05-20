const express = require('express');
const router = express.Router();
const userController = require('../controllers/usersController');
const comerciosController = require('../controllers/comerciosController');


router.get('/add', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('comercios/crear-comercio', {title : 'Comercios'});
    }
    else res.redirect('users/singin');
});

router.get('/all', async (req, res) => {
    const comercios = await comerciosController.getAllComercios();
    res.json({'comercios': comercios});
});

router.post('/check',  async (req, res) => {
    let mail = req.body.mail
    var admin = await userController.isAdmin(mail);
    if (admin['admin'] == '1'){
        res.json({ type: 'ok', alerta: { tipo: 'alert-danger', msg: 'Es administrador' } });
    }else{
        res.json({ type: 'fail', alerta: { tipo: 'alert-danger', msg: 'Credenciales inválidas' } });
    }
});

router.post('/delete',  async (req, res) => {
    let comercio = req.body.comercio
    await comerciosController.deleteComercio(comercio)
    res.json({ type: 'ok', alerta: { tipo: 'alert-danger', msg: 'Eliminado correctamente' } });
});

router.get('/error',  async (req, res) => {
    res.render('comercios/error', {title : 'Comercios'});
});

router.post('/add',  async (req, res) => {

    let nombre = req.body.nombre
    let descripcion = req.body.descripcion
    let tipo = req.body.tipo
    let lat = req.body.lat
    let lng = req.body.lng

    console.log("Entro")

    await comerciosController.createComercio(nombre, descripcion, tipo, lat, lng);

    res.json({ type: 'ok', alerta: { tipo: 'alert-danger', msg: 'Registro correcto' } });
});

router.get('/list', async (req, res) => {
    const comercios = await comerciosController.getAllComercios();

    console.log(comercios[0].nombre)

    res.render('comercios/gestionar-comercio', {
        comercios,
        listname: 'Comercios'
    });

});

router.get('/', async (req, res) => {
    const comercios = await comerciosController.getAllComercios();

    console.log(comercios[0].nombre)

    res.render('comercios/consulta-comercio', {
        comercios,
        listname: 'Comercios'
    });

});


module.exports = router;