Front-end web complementar - Projeto B AED II

Esta versao web foi feita apenas como demonstracao complementar para a
apresentacao. O projeto oficial da disciplina continua sendo o Java em Swing.

Agora a pagina web usa a logica real do projeto Java. O JavaScript nao faz a
busca sozinho. Ele chama o servidor local criado pela classe ServidorWeb.

Como executar:
1. Volte para a pasta principal do projeto.
2. Compile:
   javac *.java
3. Execute:
   java ServidorWeb
4. Abra no navegador:
   http://localhost:8181

O servidor Java carrega o Mapas.txt usando LeitorMapas, guarda os dados na
ListaDuplamenteLigada e usa BuscadorMapa para fazer a busca.

Rotas usadas:
/buscar?numero=170&responsavel=Victor
/enderecos
/historico

Mapa:
A tela possui um mapa embutido por iframe.
Ao abrir a pagina, o iframe mostra um mapa geral da Rodovia Presidente
Dutra. Quando uma pesquisa e realizada, o iframe e atualizado para o
endereco mais proximo encontrado.

Google Maps:
Nao foi usada Google Maps API com chave.
Nao foi usada API externa de calculo.
Nao foi usado Node.js, banco de dados ou framework.

Como funciona a busca:
1. O navegador envia o numero para /buscar.
2. O servidor Java recebe o numero.
3. O servidor chama BuscadorMapa.
4. BuscadorMapa pesquisa na ListaDuplamenteLigada.
5. O servidor retorna o resultado em JSON.
6. A pagina mostra o resultado e atualiza o mapa.
