const mysql = require('mysql2/promise')

async function getConnection() {
    const connection = mysql.createConnection({
        host : 'localhost:3306',
        user : 'root',
        password : 'gD7.abjhBj98',
        database : 'daweb'
    })
    return connection;
}

async function registrarUsuario(connection, nombre, mail, contrasena){
    let result = connection.execute('INSERT INTO `Usuarios` SET `nombre` = ?, `mail` = ?, `contrasena` = ?',[nombre, mail, contrasena]);
    return result;
}

async function getAllUsuarios(connection){
    const [rows, fields] = await connection.execute('SELECT * FROM `Usuarios`');
    return rows;
}

exports.getConnection=getConnection
exports.registrarUsuario=registrarUsuario
exports.getAllUsuarios=getAllUsuarios