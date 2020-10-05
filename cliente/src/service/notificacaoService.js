const axios = require('axios');

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
    await axios.post('https://testeendpontq.free.beeceptor.com', data);
};

const removeInteresse = async (idCliente, codEmpresaInteresse) => {
    const data = {
        idCliente,
        codEmpresaInteresse
    };
    await axios.post('https://testeendpontq.free.beeceptor.com', data);
};

const listarNotificacoes = async () => {
    console.log('notificação 1');
    console.log('notificação 2');
    console.log('notificação 3');
};

exports.registrarInteresse = registrarInteresse;
exports.listarNotificacoes = listarNotificacoes;
exports.removeInteresse = removeInteresse;
