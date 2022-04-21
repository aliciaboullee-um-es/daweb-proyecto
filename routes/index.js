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

module.exports = router;
