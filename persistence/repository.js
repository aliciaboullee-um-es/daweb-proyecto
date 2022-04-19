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
      'INSERT INTO users (username, passwd, mail) VALUES (?,?,?)',
      [
        user.username,
        user.passwd,
        user.mail,
      ]
    );
  }

  static async update(user) {
    try {
      await pool.promise()
        .query(
          "UPDATE users SET passwd = ?, mail = ? WHERE username = ?",
          [
            user.passwd,
            user.mail,
            user.username,
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


module.exports.UsersRepository = UsersRepository;

