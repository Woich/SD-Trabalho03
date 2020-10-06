const axios = require('axios');
const URL = 'http://localhost:8080/sd-bolsa-api/restapi';

const listarAcoesCliente = async (idCliente) =>
    axios.get(`${URL}/empresa/acoes/${idCliente}`);

const comprarAcao = async (
    codigoEmpresa,
    maxCompra,
    tempo,
    idCliente,
    qtdCompra) => {
    var data = {
        idCliente,
        codigoEmpresa,
        tipoOrdem: 1,
        valorCompra: maxCompra,
        quantidadeAcoesVendida: qtdCompra,
        prazoMin: tempo

    };
    await axios.post(`${URL}/ordens/registrar`, data);
};

const venderAcao = async (
    codigoEmpresa,
    maxVenda,
    tempo,
    idCliente,
    qtdVenda) => {
    var data = {
        idCliente,
        codigoEmpresa,
        tipoOrdem: 2,
        valorCompra: maxVenda,
        quantidadeAcoesVendida: qtdVenda,
        prazoMin: tempo

    };
    await axios.post(`${URL}/ordens/registrar`, data);
};

const insertCotacaoValorizacao = async (valorCotacao) =>
    axios.post(`${URL}/empresa/valorizacao/${valorCotacao}`);

const insertCotacaoDesvalorizacao = async (valorCotacao) =>
    axios.post(`${URL}/empresa/desvalorizacao/${valorCotacao}`);

exports.listarAcoesCliente = listarAcoesCliente;
exports.comprarAcao = comprarAcao;
exports.venderAcao = venderAcao;
exports.insertCotacaoValorizacao = insertCotacaoValorizacao;
exports.insertCotacaoDesvalorizacao = insertCotacaoDesvalorizacao;
