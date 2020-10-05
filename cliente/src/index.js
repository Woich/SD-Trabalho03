const authService = require('./service/authService.js');
const empresaService = require('./service/empresaService.js');
const acaoService = require('./service/acaoService.js');
const notificacaoService = require('./service/notificacaoService.js');

const prompt = require('prompt-sync')();

const main = async () => {
    var escolha = '0';

    var idCliente = await authService.login();
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
                var response = await empresaService.inserirEmpresa(
                    nomeEmpresa,
                    qteAcoes,
                    idCliente
                );
                if (!response) {
                    console.log('Nao foi possivel incluir a empresa');
                }
                break;

            case '2':
                console.log('-----------------------------------------');
                console.log('MINHAS ACOES');
                console.log('-----------------------------------------');
                console.log('CODIGO | VALOR COMPRA');
                var listaMinhasAcoes = [];
                listaMinhasAcoes = await acaoService.listarAcoesCliente(idCliente);

                listaMinhasAcoes.forEach((item) => {
                    console.log('-----------------------------------------');
                    console.log(`${item.codigo} | ${item.precoDeCompra}`);
                });

                console.log('-----------------------------------------');
                console.log('');
                break;
            case '3':
                console.log('-----------------------------------------');
                console.log('EMPRESAS DISPONIVEIS');
                console.log('-----------------------------------------');
                console.log('CODIGO | NOME | VALOR EMPRESA');
                await empresaService.listarEmpresas().then((res) => {
                    const empresas = [{
                        codigo: 'asdasd',
                        nome: 'Empresa Lucão',
                        valorEmpresa: '300'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Lucão Ltda.',
                        valorEmpresa: '200'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Luca$ Transportes',
                        valorEmpresa: '200'
                    }];
                    empresas.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.nome} | ${item.valorEmpresa}`);
                    });
                });

                console.log('-----------------------------------------');
                console.log('');
                var codigoEmpresaCompra = prompt(
                    'De qual empresa deseja comprar?(Informar Codigo): '
                );
                var maxPagar = prompt('Qual o valor maximo que deseja pagar?: ');
                var qtdCompra = prompt('Quantas Acoes deseja compar?: ');
                var minCompra = prompt(
                    'Qual o tempo que essa ordem vai ficar ativa em minutos?: '
                );
                await acaoService.comprarAcao(
                    codigoEmpresaCompra,
                    maxPagar,
                    minCompra,
                    idCliente,
                    qtdCompra
                );
                break;
            case '4':
                console.log('-----------------------------------------');
                console.log('MINHAS ACOES');
                console.log('-----------------------------------------');
                console.log('COD EMPRESA | CODIGO | VALOR COMPRA');
                var listaAcoes = [];
                listaAcoes = await acaoService.listarAcoesCliente(idCliente);
                listaAcoes.forEach((item) => {
                    console.log('-----------------------------------------');
                    console.log(
                        `${item.empresa.codigo} | ${item.codigo} | ${item.precoDeCompra}`
                    );
                });

                console.log('-----------------------------------------');
                console.log('');

                var codigoAcaoVenda = prompt(
                    'De qual empresa deseja vender?(Informar Codigo da Empresa): '
                );
                var minReceber = prompt('Qual o valor minimo que deseja receber?: ');
                var qtdVenda = prompt('Quantas Acoes deseja vender?: ');
                var minVenda = prompt(
                    'Qual o tempo que essa ordem vai ficar ativa em minutos?: '
                );
                await acaoService.venderAcao(
                    codigoAcaoVenda,
                    minReceber,
                    minVenda,
                    idCliente,
                    qtdVenda
                );
                break;
            case '5':
                console.log('-----------------------------------------');
                console.log('EMPRESAS DISPONIVEIS');
                console.log('-----------------------------------------');
                console.log('CODIGO | NOME | VALOR EMPRESA');
                await empresaService.listarEmpresas().then((res) => {
                    const empresas = [{
                        codigo: 'asdasd',
                        nome: 'Empresa Lucão',
                        valorEmpresa: '300'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Lucão Ltda.',
                        valorEmpresa: '200'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Luca$ Transportes',
                        valorEmpresa: '200'
                    }];
                    empresas.forEach((item) => {
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
                console.log('CODIGO | NOME');
                await empresaService.listarEmpresasInteressado(idCliente).then((res) => {
                    const empresas = [{
                        codigo: 'asdasd',
                        nome: 'Empresa Lucão',
                        valorEmpresa: '300'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Lucão Ltda.',
                        valorEmpresa: '200'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Luca$ Transportes',
                        valorEmpresa: '200'
                    }];
                    empresas.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.nome}`);
                    });
                });

                console.log('-----------------------------------------');
                console.log('');
                var codEmpresaInteresse = prompt(
                    'Qual a empresa desejada (Informar Codigo): '
                );
                await notificacaoService.removeInteresse(idCliente, codEmpresaInteresse);
                break;
            case '7':
                console.log('-----------------------------------------');
                console.log('EMPRESAS COM INTERESSE');
                console.log('-----------------------------------------');
                console.log('CODIGO | NOME');
                await empresaService.listarEmpresas().then((res) => {
                    const empresas = [{
                        codigo: 'asdasd',
                        nome: 'Empresa Lucão',
                        valorEmpresa: '300'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Lucão Ltda.',
                        valorEmpresa: '200'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Luca$ Transportes',
                        valorEmpresa: '200'
                    }];
                    empresas.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.nome} | ${item.valorEmpresa}`);
                    });
                });

                console.log('-----------------------------------------');
                console.log('');
                break;
            case '8':
                var valorCotacao = prompt('De quanto sera o aumento?(R$): ');
                await acaoService.insertCotacao(valorCotacao);
                break;
            case '9':
                var valorCotacaoReducao = prompt('De quanto sera a reducao?(R$): ');
                await acaoService.insertCotacao(valorCotacaoReducao);
                break;
            case '10':
                console.log('-----------------------------------------');
                console.log('CODIGO | NOME | VALOR EMPRESA');
                await empresaService.listarEmpresas().then((res) => {
                    const empresas = [{
                        codigo: 'asdasd',
                        nome: 'Empresa Lucão',
                        valorEmpresa: '300'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Lucão Ltda.',
                        valorEmpresa: '200'
                    },
                    {
                        codigo: 'asdasd2',
                        nome: 'Luca$ Transportes',
                        valorEmpresa: '200'
                    }];
                    empresas.forEach((item) => {
                        console.log('-----------------------------------------');
                        console.log(`${item.codigo} | ${item.nome} | ${item.valorEmpresa}`);
                    });
                });

                console.log('-----------------------------------------');
                break;
            case '11':
                await notificacaoService.listarNotificacoes();
                break;
            case '12':
                await notificacaoService.teste();
                break;
            default:
                break;
        }
    }
};

main();
