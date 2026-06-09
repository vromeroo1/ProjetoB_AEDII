var locais = [];
var historico = [];
var ultimaUrlEncontrada = "";
var mapasReserva = ""
    + "Rod Presidente Dutra, 145:https://www.google.com.br/maps/search/Rod+Presidente+Dutra,+145/@-23.275092,-45.9753995,508m/data=!3m2!1e3!4b1\n"
    + "Rod Presidente Dutra, 160:https://www.google.com.br/maps/search/Rod+Presidente+Dutra,+160/@-23.2904937,-46.015075,5233m/data=!3m2!1e3!4b1\n"
    + "Rod Presidente Dutra, 161:https://www.google.com.br/maps/search/Rod+Presidente+Dutra,+161/@-23.2756492,-45.9747189,383m/data=!3m1!1e3\n"
    + "Rod Presidente Dutra, 200:https://www.google.com.br/maps/search/Rod+Presidente+Dutra,+200/@-23.2766949,-45.9771053,387m/data=!3m1!1e3\n"
    + "Rod Presidente Dutra, 300:https://www.google.com.br/maps/place/Rod.+Pres.+Dutra,+300+-+Parque+Meia+Lua,+Jacare%C3%AD+-+SP,+12335-010/@-23.275694,-45.9766883,651m/data=!3m1!1e3!4m5!3m4!1s0x94cc34abe041cf99:0x29a9b51b5df63b53!8m2!3d-23.2762987!4d-45.976437\n"
    + "Rod Presidente Dutra, 120:https://www.google.com.br/maps/search/Rod+Presidente+Dutra,+120/@-23.3006576,-46.0172349,389m/data=!3m1!1e3";

var campoResponsavel = document.getElementById("responsavel");
var campoNumero = document.getElementById("numero");
var resultado = document.getElementById("resultado");
var statusTexto = document.getElementById("status");
var mapa = document.getElementById("mapa");

var btnPesquisar = document.getElementById("btnPesquisar");
var btnAbrirMapa = document.getElementById("btnAbrirMapa");
var btnListar = document.getElementById("btnListar");
var btnHistorico = document.getElementById("btnHistorico");
var btnLimpar = document.getElementById("btnLimpar");

btnAbrirMapa.disabled = true;

carregarMapas();

btnPesquisar.addEventListener("click", function () {
    pesquisar();
});

btnAbrirMapa.addEventListener("click", function () {
    abrirMapa();
});

btnListar.addEventListener("click", function () {
    listarEnderecos();
});

btnHistorico.addEventListener("click", function () {
    mostrarHistorico();
});

btnLimpar.addEventListener("click", function () {
    resultado.value = "";
});

function carregarMapas() {
    fetch("Mapas.txt")
        .then(function (resposta) {
            if (!resposta.ok) {
                throw new Error("Arquivo nao encontrado");
            }
            return resposta.text();
        })
        .then(function (texto) {
            montarLista(texto, "Mapas.txt carregado automaticamente.");
        })
        .catch(function () {
            montarLista(mapasReserva, "Navegador bloqueou a leitura direta do Mapas.txt. Dados locais do projeto carregados automaticamente.");
        });
}

function montarLista(texto, mensagemInicial) {
    var linhas = texto.split(/\r?\n/);
    var ignoradas = 0;

    locais = [];

    for (var i = 0; i < linhas.length; i++) {
        var local = montarLocal(linhas[i]);

        if (local != null) {
            locais[locais.length] = local;
        } else if (linhas[i].trim().length > 0) {
            ignoradas++;
        }
    }

    if (locais.length == 0) {
        statusTexto.textContent = "Mapas.txt foi carregado, mas esta vazio ou sem linhas validas.";
        resultado.value = "Nenhum endereco valido foi encontrado.";
        return;
    }

    ordenarPorNumero();

    if (ignoradas > 0) {
        statusTexto.textContent = mensagemInicial + " Enderecos carregados: " + locais.length + ". Linhas mal formatadas ignoradas: " + ignoradas + ".";
    } else {
        statusTexto.textContent = mensagemInicial + " Enderecos carregados: " + locais.length + ".";
    }
}

function montarLocal(linha) {
    if (linha == null) {
        return null;
    }

    linha = linha.trim();

    if (linha.length == 0) {
        return null;
    }

    var posicaoVirgula = linha.indexOf(",");
    var posicaoDoisPontos = linha.indexOf(":", posicaoVirgula);

    if (posicaoVirgula < 0 || posicaoDoisPontos < 0) {
        return null;
    }

    var numeroTexto = linha.substring(posicaoVirgula + 1, posicaoDoisPontos).trim();
    var endereco = linha.substring(0, posicaoDoisPontos).trim();
    var url = linha.substring(posicaoDoisPontos + 1).trim();
    var numero = parseInt(numeroTexto, 10);

    if (isNaN(numero) || url.length == 0) {
        return null;
    }

    return {
        numero: numero,
        endereco: endereco,
        url: url
    };
}

function ordenarPorNumero() {
    for (var i = 1; i < locais.length; i++) {
        var chave = locais[i];
        var j = i - 1;

        while (j >= 0 && locais[j].numero > chave.numero) {
            locais[j + 1] = locais[j];
            j--;
        }

        locais[j + 1] = chave;
    }
}

function pesquisar() {
    if (locais.length == 0) {
        resultado.value = "Nenhum endereco foi carregado.";
        return;
    }

    var textoNumero = campoNumero.value.trim();

    if (textoNumero.length == 0) {
        resultado.value = "Digite um numero para realizar a pesquisa.";
        return;
    }

    var numeroPesquisado = parseInt(textoNumero, 10);

    if (isNaN(numeroPesquisado)) {
        resultado.value = "Digite apenas numeros inteiros no campo de pesquisa.";
        return;
    }

    var encontrado = buscarMaisProximo(numeroPesquisado);

    if (encontrado == null || encontrado.url.length == 0) {
        resultado.value = "Nenhuma URL foi encontrada para a pesquisa.";
        return;
    }

    var diferenca = Math.abs(numeroPesquisado - encontrado.numero);
    var responsavel = campoResponsavel.value.trim();

    if (responsavel.length == 0) {
        responsavel = "Sem identificacao";
    }

    historico[historico.length] = {
        responsavel: responsavel,
        pesquisado: numeroPesquisado,
        encontrado: encontrado.numero,
        diferenca: diferenca,
        url: encontrado.url
    };

    ultimaUrlEncontrada = encontrado.url;
    btnAbrirMapa.disabled = false;
    mapa.src = "https://www.google.com/maps?q=" + encodeURIComponent(encontrado.endereco) + "&output=embed";

    resultado.value = "Numero pesquisado: " + numeroPesquisado + "\n"
        + "Endereco mais proximo: " + encontrado.endereco + "\n"
        + "URL: " + encontrado.url + "\n"
        + "Diferenca: " + diferenca;
}

function buscarMaisProximo(numeroPesquisado) {
    var anterior = null;

    for (var i = 0; i < locais.length; i++) {
        var atual = locais[i];

        if (atual.numero == numeroPesquisado) {
            return atual;
        }

        if (atual.numero > numeroPesquisado) {
            if (anterior == null) {
                return atual;
            }

            var diferencaAnterior = Math.abs(numeroPesquisado - anterior.numero);
            var diferencaAtual = Math.abs(numeroPesquisado - atual.numero);

            if (diferencaAnterior <= diferencaAtual) {
                return anterior;
            }

            return atual;
        }

        anterior = atual;
    }

    return anterior;
}

function abrirMapa() {
    if (ultimaUrlEncontrada.length == 0) {
        resultado.value = "Realize uma pesquisa antes de abrir o mapa.";
        return;
    }

    window.open(ultimaUrlEncontrada, "_blank");
}

function listarEnderecos() {
    if (locais.length == 0) {
        resultado.value = "Nenhum endereco foi carregado.";
        return;
    }

    var texto = "";

    for (var i = 0; i < locais.length; i++) {
        texto = texto + locais[i].endereco + " -> " + locais[i].url;

        if (i < locais.length - 1) {
            texto = texto + "\n";
        }
    }

    resultado.value = texto;
}

function mostrarHistorico() {
    if (historico.length == 0) {
        resultado.value = "Nenhuma consulta foi realizada.";
        return;
    }

    var texto = "";

    for (var i = 0; i < historico.length; i++) {
        texto = texto + "Responsavel: " + historico[i].responsavel
            + " | Pesquisado: " + historico[i].pesquisado
            + " | Encontrado: " + historico[i].encontrado
            + " | Diferenca: " + historico[i].diferenca
            + " | URL: " + historico[i].url;

        if (i < historico.length - 1) {
            texto = texto + "\n";
        }
    }

    resultado.value = texto;
}
