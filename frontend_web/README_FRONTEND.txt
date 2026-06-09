Front-end web complementar - Projeto B AED II

Esta versao web foi feita apenas como demonstracao complementar para a
apresentacao. O projeto oficial da disciplina continua sendo o Java em Swing.

O arquivo Mapas.txt fica armazenado dentro da pasta frontend_web.
A pagina carrega esse arquivo automaticamente usando:

fetch("Mapas.txt")

O usuario nao precisa selecionar arquivo manualmente.
Para a pagina funcionar tambem quando for aberta direto pelo index.html, os
mesmos dados foram colocados como reserva dentro do script.js.

Mapa:
A tela possui um mapa embutido por iframe.
Ao abrir a pagina, o iframe mostra um mapa geral da Rodovia Presidente
Dutra. Quando uma pesquisa e realizada, o iframe e atualizado para o
endereco mais proximo encontrado.

Google Maps:
Nao foi usada Google Maps API com chave.
Nao foi usada API externa de calculo.
Nao foi usado Node.js, banco de dados ou framework.

Como executar abrindo direto:
1. Abra a pasta frontend_web.
2. Clique duas vezes no arquivo index.html.
3. Pesquise o numero da rodovia na tela.

Como executar com Python:
1. Abra o terminal na pasta frontend_web.
2. Execute:
   python -m http.server 8000
3. Abra no navegador:
   http://localhost:8000

Como funciona a busca:
1. A pagina carrega as linhas do Mapas.txt.
2. Cada linha e separada pela virgula e pelos dois pontos.
3. O numero do endereco fica entre a virgula e os dois pontos.
4. A URL fica depois dos dois pontos.
5. Os enderecos sao ordenados manualmente pelo numero usando insertion sort.
6. A busca percorre os dados em ordem crescente.
7. Quando passa do numero pesquisado, compara o endereco anterior com o atual.
8. O endereco de menor diferenca e escolhido.
9. Em caso de empate, o menor numero e retornado.
