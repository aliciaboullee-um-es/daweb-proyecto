const User = require('../models/users');
const { UsersRepository } = require('../persistence/repository');

var currentUser = undefined;

class UsersController {

    static async create(username, passwd,email) {
        var res = await UsersRepository.get(email);
        if(res !== undefined) 
            return false
        
        var u = new User(username, passwd,email);
        console.log(username)
        console.log(passwd)
        console.log(email)
        await UsersRepository.add(u);
        return true;
    }

    static async login(username, password) {
        var aux = await UsersRepository.login(username, password);
        if(aux === undefined) return undefined;
        currentUser = new User(aux.username, aux.passwd, aux.mail);
        currentUser.id = aux.id;
        return currentUser;
    }

    static getCurrentUser(){
        return currentUser;
    }

    static async updateProfile(username, passwd,email){
        currentUser.username = username;
        currentUser.passwd = passwd;
        currentUser.mail = email;

        await UsersRepository.update(currentUser);
        return currentUser;
    }

    static logout(){
        currentUser = undefined;
    }


}

module.exports = UsersController;