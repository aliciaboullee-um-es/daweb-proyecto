const pool = require("./helperMYSQL");

class UsersRepository {

  static async get(email) {
    var res = await pool.promise()
      .query("SELECT * FROM users u WHERE u.mail = ? ", [email]);
    return res[0][0];
  }

  static async getById(id){
    var res = await pool.promise()
      .query("SELECT * FROM users u WHERE u.id = ? ", [id]);
      return res[0][0];
  }

  static async add(user) {
    await pool.promise().query(
      'INSERT INTO users (username, passwd, mail,admin) VALUES (?,?,?,?)',
      [
        user.username,
        user.passwd,
        user.mail,
        user.admin,
      ]
    );
  }

  static async update(user) {
    try {
      await pool.promise()
        .query(
          "UPDATE users SET passwd = ?, mail = ?,admin = ? WHERE username = ?",
          [
            user.passwd,
            user.mail,
            user.username,
            user.admin,
          ]
        );
    } catch (error) {
      throw error;
    }
  }

  static async delete(username) {
    try {
      await pool.promise()
        .query("DELETE FROM users u WHERE u.username = ?", username);
    } catch (error) {
      throw error;
    }
  }

  static async login(username, password) {
    var res = await pool.promise().query(
      "SELECT * FROM users WHERE (mail = ? AND passwd = ?)", [username, password]
    );
    return res[0][0];
  }
}


class ComerciosRepository {
  static async get(id) {
    try {
      let p = await pool
        .promise()
        .query("SELECT * FROM comercios WHERE id = ? ", [id]);
      return p[0][0];
    } catch (error) {
      throw error;
    }
  }

  static async add(comercio) {
    try {
      await pool
        .promise()
        .query(
          `INSERT INTO comercios (nombre, descripcion, tipo, lat, long) VALUES (?,?,?,?,?)`,
          [
            comercio.nombre,
            comercio.descripcion,
            comercio.tipo,
            comercio.lat,
            comercio.long,
          ]
        );
    } catch (error) {
      throw error;
    }
  }

  static async update(comercio) {
    try {
      await pool
        .promise()
        .query(
          "UPDATE comercios SET nombre = ?, descripcion = ?, tipo = ?, lat = ?, long = ? WHERE nombre = ?",
          [
            comercio.nombre,
            comercio.descripcion,
            comercio.tipo,
            comercio.lat,
            comercio.long
          ]
        );
    } catch (error) {
      throw error;
    }
  }

  static async delete(nombre) {
    try {
      await pool
        .promise()
        .query("DELETE FROM comercios p WHERE p.nombre = ?", nombre);
    } catch (error) {
      throw error;
    }
  }


  static async customQuery(queryString) {
    try {
      const comercios = await pool.promise().query(queryString);
      return comercios[0];
    } catch (error) {
      throw error;
    }
  }

}

module.exports.UsersRepository = UsersRepository;
module.exports.ComerciosRepository = ComerciosRepository;

