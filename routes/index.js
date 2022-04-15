const express = require('express');
const User = require('../models/users');
const router = express.Router();
const {UsersRepository} = require('../persistence/repository');

router.get('/', (req, res) => {
    res.redirect('users/singin');
});

module.exports = router;
