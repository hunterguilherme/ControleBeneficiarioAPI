
# Controle-Beneficiario-Api
Api  de controle de beneficiarios e seus relacionamentos, implementando autenticação via  Jwt. A aplicação é versionada com jdk11.0.8, usando db mysql e executando testes no h2.
## API 
- [x] Cadastro de beneficiario com documentos
- [x] Busca lista de beneficiarios
- [x] Atualização de beneficiario
- [x] Remoção beneficiario e seus documentos
- [x] Atualização de documento
- [x] Remocao de documento
- [x] Busca lista de beneficiarios
- [x] Atualizacao de Documento

## Instalação
Rode o comando abaixo para instalar as dependecnias

``` mvn clean package install ```

Rode o comando abaixo para rodar os testes

``` mvn verify ``` 

execute o comando abaixo no terminal na raiz do projeto

```java-jar target/beneficiari-api.jar```

## Documentação
Acesse o link abaixo para verificar a documentação
http://localhost:8080/swagger-ui/#