const express = require('express');
const router = express.Router();
const userController = require('../controllers/usersController');

router.get('/lorca', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('maps/lorca', {title : 'Lorca'});
    }
    else res.redirect('../users/singin');
});

router.get('/malaga', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('maps/malaga');
    }
    else res.redirect('../users/singin');
});

router.get('/vitoria', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('maps/vitoria', {title : 'Vitoria-Gasteiz'});
    }
    else res.redirect('../users/singin');
});

router.get('/tenerife', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('maps/tenerife', {title : 'Sta Cruz Tenerife'});
    }
    else res.redirect('../users/singin');
});

module.exports = router;