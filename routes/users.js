var express = require('express');
var router = express.Router();

var app = express()
var bodyParser = require('../src/assets/js/helperMYSQL');
//app.use(bodyParser.urlencoded({ extended: false }));
/* POST: registrar contactos */
router.post('/register', function(req, res, next) {
  let name = req.body.name;
  let mail = req.body.mail;
  let contrasena = req.body.contrasena;

  let connection = mysql.getConnection()
    .then(con => {return mysql.registerUsuario(con, name, mail, contrasena)})
    .then( result => {
      if(result[0].affectedRows > 0){
        res.send('Usuario registrado')
      }
      else{
        res.send('Error al registrar el usuario')
      }
    })
    .catch(error => {res.send(error)})
});

module.exports = router;

