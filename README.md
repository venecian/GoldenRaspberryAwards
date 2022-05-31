# Golden Raspberry Awards
API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme

<h4>Requisitos e tecnologias utilizadas</h4>

<ul>
  <li>Java 11 e Maven</l1>
  <li>Spring boot 2.5.5</li>
  <li>Banco de dados H2</li>
  <li>lombok, jpa, opencsv, swagger, junit</li>
</ul>

<h4>Instruções para arquivo de carga em arquivos CSV</h4>

<ul>
  <li>O arquivo está salvo no pacote "src\main\resources" com o nome "movielist.csv"</li>
  <li>Contém as seguintes colunas delimitadas por ; na seguinte ordem:<br>
  year;title;studios;producers;winner</li>
  <li>Observação: Os campos studios e producers podem conter mais de uma informação: nestes casos utilize delimitadores , ou and</li>
</ul>  

Exemplo
<br>year;title;studios;producers;winner
<br>1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes
<br>1980;Cruising;Lorimar Productions, United Artists;Jerry Weintraub;

<h4>Execução da aplicação</h4>

<ul>
  <li>Para construir e executar, utilize o comando <b>mvn spring-boot:run</b></li>
</ul> 

<h4>Documentação da API</h4>

Para ver a lista de chamadas REST disponíveis, seus parametros, códigos de resposta HTTP, e tipo de retorno, inicie a aplicação e acesse:
<br>http://localhost:8080/swagger-ui.html

<h4>Acesso ao banco de dados</h4>

http://localhost:8080/h2

<ul>
  <li>Driver: org.h2.Driver</li>
  <li>Url JDBC: jdbc:h2:mem:banco</li>
  <li>Usuario: admin</li>
  <li>Senha: 123</li>
</ul>  


<h4>Execução dos testes de integração</h4>

<ul>
	<li>Navegue até o pacote "src\test\java\br.com.filmes\controller"</li>
	<li>Clique em "Run" na classe <b>"MovieControllerTest.java"</b> </li>
	<li>Será executado o teste "validaMinMaxIntervalorTest_returnStatusCode200" que possui a finalidade de validar a execução da consulta de vencedores 
    com maior e menor intervalo entre os prêmios de acordo com os requisitos da API</li>
</ul>
