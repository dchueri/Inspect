#
# INSPECT
![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge) ![Badge issues](https://img.shields.io/github/issues/dchueri/Inspect?style=for-the-badge) ![Badge licensa](https://img.shields.io/github/license/dchueri/Inspect?style=for-the-badge) ![Badge Versão](https://img.shields.io/badge/VERSION-1.0.0-blue?style=for-the-badge) 

## Índice

* [Descrição](#descrição)
* [Funcionalidades da Aplicação](#funcionalidades-da-aplicação)
* [Acesso ao Projeto](#acesso-ao-projeto)
* [Tecnologias utilizadas](#tecnologias-utilizadas)
* [Contribuidores](#contribuidores)
* [Pessoas Desenvolvedoras do Projeto](#pessoas-desenvolvedoras)
* [Licença](#licença)

## 🚀 Descrição

Projeto desenvolvido para o Alura Challenge 3 - Back-end. O **Inspect** tem o intuito de realizar a importação de transações bancárias. Sua principal função é realizar a análise das transações para identificar possíveis transações suspeitas.

## 🔨 Funcionalidades da aplicação

- `Importação de arquivos de transações`: a aplicação suporta o envio de arquivos CSV e XML. Os dados dos arquivos enviados são inseridos automáticamente ao banco de dados.
- `Listagem de importações realizadas`: o usuário pode visualizar as importações que já foram feitas.
![Imagem de exemplo](https://i.imgur.com/l76V7Jk.png)
- `Cadastro e login`: para utilizar a aplicação deve-se realizar o cadastro. A senha será enviada automaticamente para o email cadastrado para que o usuário possa realizar o login.
![Imagem de exemplo](https://i.imgur.com/YJDhUOy.png)
- `Gerenciamento de usuários`: na página de USUÁRIOS o usuário logado poderá realizar a gestão dos usuários cadastrados (alterações dos dados dos usuários e também exclusão).
![Imagem de exemplo](https://i.imgur.com/DyPnwSv.png)
- `Detalhamento das importações`: é possível visualizar os detalhes de uma importação realizada. Exibindo dados como: usuário que realizou a importação; data que o arquivo foi importado; data de realização das transações e detalhar cada transação importada pelo arquivo. 
![Imagem de exemplo](https://i.imgur.com/63zTAH2.png)
- `Análise das transações do mês`: na página de Análise o usuário escolherá um mês e ano para ser analizado. Será retornado na tela um relátorio das atividades suspeitas encontradas.
![Imagem de exemplo](https://i.imgur.com/500x5Tc.png)

## 🛠️ Construído com

* `Java 8`
* `Springboot`
* `Maven`
* `MariaDB`

## 🖇️ Contribuidores

Seja o primeiro a contribuir!

## 📌 Versão

Nós usamos [SemVer](http://semver.org/) para controle de versão. Para as versões disponíveis, observe as [tags neste repositório](https://github.com/suas/tags/do/projeto). 

## ✒️ Autor

| [<img src="https://avatars.githubusercontent.com/u/84249430?s=400&u=b789830e57ccc23a4d4d758542785461dd656b5f&v=4" width=115><br><sub>Diego  Chueri</sub>](https://github.com/dchueri) | 
| :---: |

## 📄 Licença

Este projeto está sob a [MIT License](https://github.com/dchueri/Inspect/blob/main/LICENSE).
