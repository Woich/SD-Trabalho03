const axios = require('axios');
const URL = 'http://localhost:8080/sd-bolsa-api/restapi';

const login = async (port) => {
    var request = {
        port
    };
    var idCliente;
    await axios.post(`${URL}/cliente/registrar`, request).then((res) => {
        idCliente = res.data.id;
    });
    return idCliente;
};
exports.login = login;
