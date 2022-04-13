var express = require('express');
var router = express.Router();
var helper_mysql = require("../js/helperMYSQL");

var app = express()
/* POST: registrar contactos */
router.post('/register', function(req, res, next) {
  let name = req.body.name;
  let mail = req.body.mail;
  let contrasena = req.body.contrasena;

  let connection = helper_mysql.getConnection()
    .then(con => {return helper_mysql.registerUsuario(con, name, mail, contrasena)})
    .then( result => {
      if(result[0].affectedRows > 0){
        res.send('Usuario registrado')
      }
      else{
        res.send('Error al registrar el usuario')
      }
    })
    .catch(error => {
      console.log(error);
      res.send(error);
    })
});

module.exports = router;

