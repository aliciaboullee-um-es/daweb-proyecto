const Comercio = require('../models/comercios');
const { ComerciosRepository } = require('../persistence/repository');

class ComerciosController {

    static async createComercio(nombre, descripcion, tipo, lat, long) {
        var c = new Comercio(nombre, descripcion, tipo, lat, long);
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
}

module.exports = ComerciosController;