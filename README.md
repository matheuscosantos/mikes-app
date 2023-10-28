# Mikes

Onde a baguncinha é o tempero da casa!

# 1. Configurando

Nossa solução tem como pré requisito a ferramenta ```docker``` e todos os comandos aqui mencionados devem ser executados na raiz do projeto (```/mikes```).

## 1.1 Configurando com docker compose (Recomendado)

Antes de configurarmos nossa aplicação via docker compose, devemos avaliar a possibilidade de modificar alguns parâmetros via variáveis de ambiente:

| ENVIROMENT    | Valor padrão  | Descrição                         |
|---------------|---------------|-----------------------------------|
| DB_NAME       | mikes-db      | Nome da base de dados             |
| DB_USER       | root          | Usuário da base de dados          |
| DB_PASSWORD   | change_it     | Senha da base de dados            |
| APP_BIND_PORT | 8080          | Porta de bind da aplicação        |
| DB_BIND_PORT  | 5432          | Porta de bind do banco de dados   |

Comando:
> docker compose up

> **Nota**
> Caso venha a alterar a aplicação, você poderá estar adicionando o sufixo --build ao comando p/ atualizar o artefato sendo configurado.

## 1.2 Configurando com docker file (Alternativa)

Nossa solução está separada em dois dockerfiles, sendo o ```Dockerfile``` responsável pela construção e execução da aplicacão e o ```Dockerfile.db``` responsável pela construção e execução do banco dados.

### 1.2.1. Criando uma rede

Como nossa solução está separada em dois dockerfiles, criaremos uma rede, com o proposito de viabilizar a comunicação entre os containers que criaremos:

Comando:
> docker network create ```nome-da-rede```

ex:
> docker network create ```mikes-network```

### 1.2.2. Criando a imagem do banco de dados

Antes de criarmos a imagem do banco de dados, devemos avaliar a possibilidade de modificar alguns argumentos:

| Argumento   | Valor padrão | Descrição                |
|-------------|--------------|--------------------------|
| DB_NAME     | mikes-db     | Nome da base de dados    |
| DB_USER     | root         | Usuário da base de dados |
| DB_PASSWORD | change_it    | Senha da base de dados   |

Comando:
> docker build -f ./Dockerfile.db -t ```nome-da-imagem-do-danco-de-dados``` .

Ex utilizando argumentos com valores padrões:
> docker build -f ./Dockerfile.db -t ```mikes-db-image``` .

Ex utilizando argumentos com valores modificados:
> docker build ```--build-arg DB_USER=user``` ```--build-arg DB_PASSWORD=secret``` -f ./Dockerfile.db -t ```mikes-db-image``` .

### 1.2.3. Executando container do banco de dados

Comando:
> docker run --name=```nome-do-container-do-banco-de-dados``` --network=```nome-da-rede``` -p ```DB_BIND_PORT```:5432 ```nome-da-imagem-do-danco-de-dados```

Exemplo:
> > docker run --name=```mikes-db-container``` --network=```mikes-network``` -p ```5432```:5432 ```mikes-db-image```

> **Nota**
> A porta ```DB_BIND_PORT``` servirá p/ você acessar o banco de dados de fora do container.

> **Nota**
> Executando desta forma, o terminal ficará 'preso' a execução do container. Caso prefira, você pode executar o container de forma 'solta' utilizando o argumento -d: ```docker run -d ....```

### 1.2.4. Criando a imagem da aplicação

Antes de criarmos a imagem da aplicação, devemos avaliar a possibilidade de modificar alguns argumentos:

| Argumento   | Valor padrão        | Descrição                |
|-------------|---------------------|--------------------------|
| DB_HOST     | mikes-db-container  | Host da base de dados    |
| DB_PORT     | 5432                | Porta da base de dados   |
| DB_NAME     | mikes-db            | Nome da base de dados    |
| DB_USER     | root                | Usuário da base de dados |
| DB_PASSWORD | change_it           | Senha da base de dados   |

Comando:
> docker build -t ```nome-da-imagem-da-aplicação``` .

Ex utilizando argumentos com valores padrões:
> docker build -t ```mikes-image``` .

Ex utilizando argumentos com valores modificados:
> docker build ```--build-arg DB_USER=user``` ```--build-arg DB_PASSWORD=secret``` -t ```mikes-image``` .

> **Nota**
> Caso você tenha modificado algum argumento na criação da imagem do banco de dados, lembre de refletir seus valores na criação da imagem da aplicação.

> **Nota**
> Tenha em mente que o argumento ```DB_PORT``` é independente do```DB_BIND_PORT```.

> **Nota**
> Tenha em mente que o argumento ```DB_HOST``` deve conter o ```nome-do-container-do-banco-de-dados```.

### 1.2.5. Executando a aplicação

Comando:
> docker run --name=```nome-do-container-da-aplicação``` --network=```nome-da-rede``` -p ```APP_BIND_PORT```:8080 ```nome-da-imagem-da-aplicação```

Exemplo:
> docker run --name=```mikes-container``` --network=```mikes-network``` -p ```8080```:8080 ```mikes-image```

> **Nota**
> A porta ```APP_BIND_PORT``` servirá p/ você acessar a aplicação de fora do container.

> **Nota**
> Executando desta forma, o terminal ficará 'preso' a execução do container. Caso prefira, você pode executar o container de forma 'solta' utilizando o argumento -d: ```docker run -d ....```

# Kubernetes
Os arquivos para a subir a aplicação e o banco de dados usando o Kubernetes estão no diretório <strong>kubernetes</strong>.

Dentro do diretório kubernetes execute os comandos:

```
kubectl apply -f db-configmap.yaml
kubectl apply -f db-credentials.yaml
kubectl apply -f dp-mikes-db.yaml
kubectl apply -f dp-mikes-app.yaml
```

Obs: Os pods da aplicação devem subir depois que os pods do banco de dados já estiverem instânciados. 
