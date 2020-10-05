const axios = require('axios');

const inserirEmpresa = async (nomeEmpresa, qtdAcoes, idCliente) => {
    const data = {
        nomeEmpresa,
        qtdAcoes,
        idCliente
    };
    await axios.post('https://testeendpontq.free.beeceptor.com', data);
};

const listarEmpresas = () =>
    axios.get('https://testeendpontq.free.beeceptor.com');

const listarEmpresasInteressado = (idCliente) =>
    axios.get('https://testeendpontq.free.beeceptor.com');

exports.inserirEmpresa = inserirEmpresa;
exports.listarEmpresas = listarEmpresas;
exports.listarEmpresasInteressado = listarEmpresasInteressado;
