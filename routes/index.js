const express = require('express');
const router = express.Router();
const userController = require('../controllers/usersController');

router.get('/', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('layouts/index', {title : 'Inicio'});
    }
    else res.redirect('users/singin');
});

router.get('/sitiosinteres', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('layouts/sitiosinteres', {title : 'Sitios de interés'});
    }
    else res.redirect('users/singin');
});

router.get('/aparcamientos', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('layouts/aparcamientos', {title : 'Aparcamientos'});
    }
    else res.redirect('users/singin');
});

router.get('/comercios/add', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('comercios/gestion-comercio', {title : 'Comercios'});
    }
    else res.redirect('users/singin');
});

module.exports = router;
