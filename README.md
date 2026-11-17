Thymeleaf

Bootstrap 5

PostgreSQL

Maven

JUnit 5 e Mockito

JaCoCo

Git e GitHub Actions

Funcionalidades

Gestão de contactos.

Registo e consulta de oportunidades.

Atualização de estados e prioridades.

Definição de prazos e valores.

Registo de interações.

Autenticação de utilizadores.

Modo de alto contraste com preferência guardada no navegador.

Arquitetura

O projeto utiliza uma arquitetura monolítica organizada em camadas:

Model: representação das entidades e regras de validação.

Repository: acesso e persistência dos dados.

Service: implementação das regras de negócio.

Controller: gestão dos pedidos e navegação.

Templates: apresentação das páginas através do Thymeleaf.

Como executar o projeto

Pré-requisitos

Java 21

Maven

Docker e Docker Compose

Git

1. Clonar o repositório

git clone https://github.com/Andreycosta-code/gestao-contatos-oportunidades.git
cd gestao-contatos-oportunidades

2. Configurar as variáveis de ambiente

Criar um ficheiro .env na raiz do projeto:

DB_PASSWORD=defina_uma_senha_segura

O ficheiro .env não deve ser enviado para o GitHub.

3. Iniciar a base de dados

docker compose up -d

4. Configurar as variáveis para o Maven

No terminal Bash:

set -a
source .env
set +a

export DB_USERNAME=gestor
export DB_URL=jdbc:postgresql://localhost:5432/gestao_contatos

5. Executar a aplicação

mvn spring-boot:run

A aplicação ficará disponível em:

http://localhost:8080

Testes e qualidade

O projeto utiliza JUnit 5 e Mockito para testes automatizados.

Para executar os testes e gerar o relatório de cobertura:

mvn clean verify

O relatório JaCoCo pode ser consultado em:

target/site/jacoco/index.html

Também foi configurado um pipeline de integração contínua com GitHub Actions, responsável por executar a compilação e os testes automaticamente.

Acessibilidade

Foi implementado um modo de alto contraste, permitindo melhorar a visualização dos conteúdos através de cores contrastantes. A preferência selecionada é armazenada no navegador através do localStorage.

Âmbito do MVP

Esta versão contempla as funcionalidades essenciais de gestão de contactos e oportunidades. Integrações automáticas com WhatsApp, faturação, pagamentos e notificações avançadas não fazem parte do âmbito atual.

Objetivo de Desenvolvimento Sustentável

O projeto relaciona-se com o ODS 8 — Trabalho Digno e Crescimento Económico, especialmente com a meta 8.3, através da utilização de ferramentas digitais para apoiar a organização da atividade profissional e o empreendedorismo.

Autor

Andrey Pontes

Projeto académico —