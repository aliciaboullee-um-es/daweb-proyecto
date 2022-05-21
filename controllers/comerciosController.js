const Comercio = require('../models/comercios');
const { ComerciosRepository } = require('../persistence/repository');

class ComerciosController {

    static async createComercio(nombre, descripcion, tipo, lat, lng) {
        console.log(lng)
        var c = new Comercio(nombre, lat, lng,descripcion,tipo);
        await ComerciosRepository.add(c);
    }

    static async getComercioById(id) {
        const comercio = await ComerciosRepository.get(id);
        return comercio;
    }

    static async getAllComercios() {

        var comercios = await ComerciosRepository.getAllComercios();
        return comercios;
    }

    static async deleteComercio(nombre) {

        await ComerciosRepository.deleteComercio(nombre);
        
    }

    static async updateComercio(nombre, descripcion, tipo, lat, lng, oldname) {
        var c = new Comercio(nombre, lat, lng,descripcion,tipo);
        await ComerciosRepository.update(c,oldname);
    }
}

module.exports = ComerciosController;