const axios = require('axios');

const URL = 'http://localhost:8080/sd-bolsa-api/restapi';

const inserirEmpresa = async (nomeEmpresa, qtdAcoes, idCliente) => {
    const data = {
        nome: nomeEmpresa,
        quantidadeTotalAcoes: qtdAcoes,
        idCliente
    };
    await axios.post(`${URL}/empresa/registrar`, data);
};

const listarEmpresas = () =>
    axios.get(`${URL}/empresa`);

const listarEmpresasInteressado = (idCliente) =>
    axios.get(`${URL}/cliente/interesse/${idCliente}`);

exports.inserirEmpresa = inserirEmpresa;
exports.listarEmpresas = listarEmpresas;
exports.listarEmpresasInteressado = listarEmpresasInteressado;
