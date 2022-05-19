const express = require('express');
const router = express.Router();
const userController = require('../controllers/usersController');
const comerciosController = require('../controllers/comerciosController');


router.get('/comercios/add', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('comercios/gestion-comercio', {title : 'Comercios'});
    }
    else res.redirect('users/singin');
});

router.post('/comercios/add',  async (req, res) => {

    let nombre = req.body.nombre
    let descripcion = req.body.descripcion
    let tipo = req.body.tipo
    let lat = req.body.lat
    let long = req.body.long

    await comerciosController.createProduct(nombre, descripcion, tipo, lat, long);

    res.json({ type: 'ok', alerta: { tipo: 'alert-danger', msg: 'Registro correcto' } });
});

module.exports = router;