var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  session=req.session;
    if(session.mail!=null){
        res.send("Welcome User <a href=\'/logout'>click to logout</a>");
    }else
    res.redirect('../login')
});

module.exports = router;