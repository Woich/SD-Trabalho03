const authService = require('./service/authService.js');
const empresaService = require('./service/empresaService.js');
const acaoService = require('./service/acaoService.js');
const notificacaoService = require('./service/notificacaoService.js');

var EventSource = require('eventsource');

const port = 1234;

const prompt = require('prompt-sync')();

var messages = [];

const main = async () => {
    const evtSource = new EventSource('http://localhost:8080/sd-bolsa-api/restapi/notificacao');
    evtSource.onmessage = function (event) {
        var res = JSON.parse(event.data);
        if (res.idCliente === this.idCliente) {
            messages = [...messages, res.message];
        }
    };
    var escolha = '0';

    var idCliente = await authService.login(port);
    while (escolha !== '-1') {
        console.log('O que deseja fazer?\n\n');

        console.log('1 - Inserir uma nova empresa');
        console.log('2 - Listar minhas ações');
        console.log('3 - Registrar ordem de compra de ação');
        console.log('4 - Registrar ordem de venda de ação');
        console.log('5 - Registrar interesse em notificações');
        console.log('6 - Remover interesse em notificações');
        console.log('7 - Listar interesses');
        console.log('8 - Registrar aumento geral de preço');
        console.log('9 - Registrar diminuição geral de preço');
        console.log('10 - Mostrar lista de Empresas');
        console.log('11 - Listar Notificações');
        console.log('-1 - Sair');

        escolha = prompt();
        switch (escolha) {
            case '1':
                var nomeEmpresa = prompt('Digite o nome da empresa: ');
                var qteAcoes = prompt('Digite o numero de acoes da empresa: ');
                // insere empresa após recuperar informações do usuário
                await empresaService.inserirEmpresa(
                    nomeEmpresa,
                    qteAcoes,
                    idCliente
                ).catch((e) => {
                    console.log('Nao foi possivel incluir a empresa');
                });
                break;

            case '2':
                console.log('-----------------------------------------');
                console.log('MINHAS ACOES');
                console.log('-----------------------------------------');
                console.log('CODIGO | VALOR COMPRA');
                // lista todas as ações que pertencem ao usuário
                await acaoService.listarAcoesCliente(idCliente).then((res) => {
                    res.data.acoes.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.precoDeCompra}`);
                    });
                });

                console.log('-----------------------------------------');
                console.log('');
                break;
            case '3':
                console.log('-----------------------------------------');
                console.log('EMPRESAS DISPONIVEIS');
                console.log('-----------------------------------------');
                console.log('CODIGO | NOME | VALOR EMPRESA');
                // lista empresas cadastradas
                await empresaService.listarEmpresas().then((res) => {
                    res.data.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.nome} | ${item.valorEmpresa}`);
                    });
                });

                console.log('-----------------------------------------');
                console.log('');
                var codigoEmpresaCompra = prompt(
                    'De qual empresa deseja comprar?(Informar Codigo): '
                );
                var maxCompra = prompt('Qual o valor maximo que deseja pagar?: ');
                var qtdCompra = prompt('Quantas Acoes deseja compar?: ');
                var tempo = prompt(
                    'Qual o tempo que essa ordem vai ficar ativa em minutos?: '
                );
                // realiza chamada de compra de ação após recuperar informações do usuário
                await acaoService.comprarAcao(
                    codigoEmpresaCompra,
                    maxCompra,
                    tempo,
                    idCliente,
                    qtdCompra
                );
                break;
            case '4':
                console.log('-----------------------------------------');
                console.log('MINHAS ACOES');
                console.log('-----------------------------------------');
                console.log('COD EMPRESA | CODIGO | VALOR COMPRA');
                // lista as ações do usuário
                await acaoService.listarAcoesCliente(idCliente).then((res) => {
                    res.data.acoes.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.precoDeCompra}`);
                    });
                });

                console.log('-----------------------------------------');
                console.log('');

                var codigoAcaoVenda = prompt(
                    'De qual empresa deseja vender?(Informar Codigo da Empresa): '
                );
                var minVenda = prompt('Qual o valor minimo que deseja receber?: ');
                var qtdVenda = prompt('Quantas Acoes deseja vender?: ');
                var prazo = prompt(
                    'Qual o tempo que essa ordem vai ficar ativa em minutos?: '
                );
                // realiza requisição de vender ação após recuperar dados do usuario
                await acaoService.venderAcao(
                    codigoAcaoVenda,
                    minVenda,
                    prazo,
                    idCliente,
                    qtdVenda
                );
                break;
            case '5':
                console.log('-----------------------------------------');
                console.log('EMPRESAS DISPONIVEIS');
                console.log('-----------------------------------------');
                console.log('CODIGO | NOME | VALOR EMPRESA');
                // lista empresas registradas
                await empresaService.listarEmpresas().then((res) => {
                    res.data.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.nome} | ${item.valorEmpresa}`);
                    });
                });
                console.log('-----------------------------------------');
                console.log('');
                console.log('Qual a empresa desejada (Informar Codigo): ');
                var codEmpresa = prompt();
                console.log('Qual o valor maximo de ganho da empresa?(R$): ');
                var valGanho = prompt();
                console.log('Qual o valor maximo de perda da empresa?(R$): ');
                var valPerda = prompt();

                // chamada do endpoint de registrar interesse após recuperar dados do usuário
                await notificacaoService.registrarInteresse(
                    idCliente,
                    codEmpresa,
                    valGanho,
                    valPerda
                );
                break;
            case '6':
                console.log('-----------------------------------------');
                console.log('EMPRESAS COM INTERESSE');
                console.log('-----------------------------------------');
                console.log('CODIGO | VALOR DE GANHO | VALOR DE PERDA');
                // lista empresas nas quais o cliente tem interesse
                await empresaService.listarEmpresasInteressado(idCliente).then((res) => {
                    res.data.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigoEmpresa} | ${item.valGanho} | ${item.valPerda}`);
                    });
                });

                console.log('-----------------------------------------');
                console.log('');
                var codEmpresaInteresse = prompt(
                    'Qual a empresa desejada (Informar Codigo): '
                );

                // chamada do endpoint que remove interesse na empresa especificada pelo cliente
                await notificacaoService.removeInteresse(
                    idCliente,
                    codEmpresaInteresse
                );
                break;
            case '7':
                console.log('-----------------------------------------');
                console.log('EMPRESAS COM INTERESSE');
                console.log('-----------------------------------------');
                console.log('CODIGO | VALOR DE GANHO | VALOR DE PERDA');
                // lista empresas nas quais o cliente tem interesse
                await empresaService.listarEmpresasInteressado(idCliente).then((res) => {
                    res.data.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigoEmpresa} | ${item.valGanho} | ${item.valPerda}`);
                    });
                });

                console.log('-----------------------------------------');
                console.log('');
                break;
            case '8':
                var valorCotacao = prompt('De quanto sera o aumento?(R$): ');

                // chamada do endpoint de registrar aumento geral nos preços
                await acaoService.insertCotacaoValorizacao(valorCotacao);
                break;
            case '9':
                var valorCotacaoReducao = prompt('De quanto sera a reducao?(R$): ');

                // chamada do endpoint de registrar diminuição geral nos preços
                await acaoService.insertCotacaoDesvalorizacao(valorCotacaoReducao);
                break;
            case '10':
                console.log('-----------------------------------------');
                console.log('CODIGO | NOME | VALOR EMPRESA');

                // lista todas as empresas registradas
                await empresaService.listarEmpresas().then((res) => {
                    res.data.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.nome} | ${item.valorEmpresa}`);
                    });
                });
                console.log('-----------------------------------------');
                break;
            case '11':
                // lista mensagens recebidas
                messages.forEach((m) => {
                    console.log(m);
                });
                break;
            default:
                break;
        }
    }
};
main();
