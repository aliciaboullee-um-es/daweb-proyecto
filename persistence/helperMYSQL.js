const mysql = require('mysql2'); 

const pool = mysql.createPool({
    host: 'localhost',
    port: '3306',
    user: 'root',
    password: 'alex',//'gD7.abjhBj98',
    database: 'daweb'
});


pool.getConnection((error, connection) => {
    if(error){
        console.error('ERROR AL CONECTAR CON LA BD: ', error.code);
    }

    if(connection){
        connection.release();
        console.log('CONEXIÓN CON ÉXITO A LA BD');
    }
})

pool.query(
    "CREATE TABLE users (id INT NOT NULL AUTO_INCREMENT, username VARCHAR(45) NOT NULL, passwd VARCHAR(45) NOT NULL, mail VARCHAR(45) NOT NULL,admin VARCHAR(7) NOT NULL, PRIMARY KEY (id));", (err, res) => {});

    pool.query(
        "CREATE TABLE comercios (id INT NOT NULL AUTO_INCREMENT, nombre VARCHAR(45) NOT NULL, descripcion VARCHAR(45) NOT NULL, tipo VARCHAR(45) NOT NULL,lat VARCHAR(45) NOT NULL,long VARCHAR(45) NOT NULL, PRIMARY KEY (id));", (err, res) => {});
    

module.exports = pool;
