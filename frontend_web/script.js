var ultimaUrlEncontrada = "";

var campoResponsavel = document.getElementById("responsavel");
var campoNumero = document.getElementById("numero");
var resultado = document.getElementById("resultado");
var mapa = document.getElementById("mapa");

var btnPesquisar = document.getElementById("btnPesquisar");
var btnAbrirMapa = document.getElementById("btnAbrirMapa");
var btnListar = document.getElementById("btnListar");
var btnHistorico = document.getElementById("btnHistorico");
var btnLimpar = document.getElementById("btnLimpar");

btnAbrirMapa.disabled = true;

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

function pesquisar() {
    var textoNumero = campoNumero.value.trim();
    var responsavel = campoResponsavel.value.trim();

    if (textoNumero.length == 0) {
        resultado.value = "Digite um numero para realizar a pesquisa.";
        return;
    }

    if (responsavel.length == 0) {
        responsavel = "Sem identificacao";
    }

    fetch("/buscar?numero=" + encodeURIComponent(textoNumero) + "&responsavel=" + encodeURIComponent(responsavel))
        .then(function (resposta) {
            return resposta.json();
        })
        .then(function (dados) {
            if (dados.erro) {
                resultado.value = dados.erro;
                return;
            }

            ultimaUrlEncontrada = dados.url;
            btnAbrirMapa.disabled = false;
            mapa.src = "https://www.google.com/maps?q=" + encodeURIComponent(dados.enderecoEncontrado) + "&output=embed";

            resultado.value = "Numero pesquisado: " + dados.numeroPesquisado + "\n"
                + "Endereco mais proximo: " + dados.enderecoEncontrado + "\n"
                + "URL: " + dados.url + "\n"
                + "Diferenca: " + dados.diferenca;
        })
        .catch(function () {
            resultado.value = "Nao foi possivel chamar o servidor Java. Execute: java ServidorWeb";
        });
}

function abrirMapa() {
    if (ultimaUrlEncontrada.length == 0) {
        resultado.value = "Realize uma pesquisa antes de abrir o mapa.";
        return;
    }

    window.open(ultimaUrlEncontrada, "_blank");
}

function listarEnderecos() {
    fetch("/enderecos")
        .then(function (resposta) {
            return resposta.json();
        })
        .then(function (enderecos) {
            if (enderecos.length == 0) {
                resultado.value = "Nenhum endereco foi carregado.";
                return;
            }

            var texto = "";

            for (var i = 0; i < enderecos.length; i++) {
                texto = texto + enderecos[i].endereco + " -> " + enderecos[i].url;

                if (i < enderecos.length - 1) {
                    texto = texto + "\n";
                }
            }

            resultado.value = texto;
        })
        .catch(function () {
            resultado.value = "Nao foi possivel listar os enderecos pelo servidor Java.";
        });
}

function mostrarHistorico() {
    fetch("/historico")
        .then(function (resposta) {
            return resposta.json();
        })
        .then(function (historico) {
            if (historico.length == 0) {
                resultado.value = "Nenhuma consulta foi realizada.";
                return;
            }

            var texto = "";

            for (var i = 0; i < historico.length; i++) {
                texto = texto + "Responsavel: " + historico[i].responsavel
                    + " | Pesquisado: " + historico[i].numeroPesquisado
                    + " | Encontrado: " + historico[i].numeroEncontrado
                    + " | Diferenca: " + historico[i].diferenca
                    + " | URL: " + historico[i].url;

                if (i < historico.length - 1) {
                    texto = texto + "\n";
                }
            }

            resultado.value = texto;
        })
        .catch(function () {
            resultado.value = "Nao foi possivel carregar o historico pelo servidor Java.";
        });
}
