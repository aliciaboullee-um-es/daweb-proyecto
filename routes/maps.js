const express = require('express');
const router = express.Router();
const userController = require('../controllers/usersController');

router.get('/lorca', (req, res) => {
    if(userController.getCurrentUser() != undefined){
        res.render('maps/lorca', {title : 'Lorca'});
    }
    else res.redirect('../users/singin');
});

module.exports = router;