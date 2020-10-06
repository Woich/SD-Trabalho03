const axios = require('axios');
const URL = 'http://localhost:8080/sd-bolsa-api/restapi';

const login = async () => {
    var idCliente;
    await axios.post(`${URL}/cliente/registrar`).then((res) => {
        idCliente = res.data.id;
    });
    return idCliente;
};
exports.login = login;
