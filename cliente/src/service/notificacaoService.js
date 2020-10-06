const axios = require('axios');
const URL = 'http://localhost:8080/sd-bolsa-api/restapi';

const registrarInteresse = async (
    idCliente,
    codEmpresa,
    valGanho,
    valPerda
) => {
    const data = {
        idCliente,
        codEmpresa,
        valGanho,
        valPerda
    };
    await axios.post(`${URL}/cliente/interesse/registrar`, data);
};

const removeInteresse = async (idCliente, codEmpresaInteresse) => {
    const data = {
        idCliente,
        codEmpresaInteresse
    };
    await axios.delete(`${URL}/cliente/interesse/remover`, data);
};

exports.registrarInteresse = registrarInteresse;
exports.removeInteresse = removeInteresse;
