Gestão de Contactos e Oportunidades

🌐 Aplicação online

A aplicação está disponível publicamente através do seguinte endereço:

[Aceder à aplicação — Gestão de Contactos e Oportunidades](https://gestao-contatos-oportunidades.onrender.com/)

Para aceder, utilize as credenciais de demonstração fornecidas pelo autor do projeto.

Aplicação web desenvolvida no âmbito de um projeto académico de Análise
e Desenvolvimento de Sistemas, com o objetivo de apoiar a organização da
atividade de um trabalhador independente da área da construção civil.

A solução permite centralizar contactos, pedidos de trabalho e respetivo
acompanhamento, reduzindo a dispersão de informação que pode ocorrer
quando os pedidos são recebidos através de chamadas, mensagens e
contactos pessoais.
Objetivos
- Centralizar os contactos e as oportunidades de trabalho.
- Acompanhar o estado, a prioridade e os prazos de cada oportunidade.
- Registar e consultar o histórico de interações.
- Consultar informação relevante para a gestão da atividade, incluindo
  tempo estimado e valores a receber.
- Disponibilizar uma interface responsiva e um modo de alto contraste.
O projeto está alinhado com o ODS 8 --- Trabalho Digno e Crescimento
Económico, em particular com a meta 8.3, através do apoio à
organização e ao empreendedorismo de pequenos negócios.
Funcionalidades
- Autenticação de utilizador.
- Painel principal com indicadores e informação resumida.
- Registo, consulta, edição e eliminação segura de contactos.
- Registo, consulta, edição e acompanhamento de oportunidades.
- Associação de cada oportunidade a um contacto.
- Gestão do estado e da prioridade das oportunidades.
- Registo de prazo de resposta, tempo estimado e valor a receber.
- Registo e consulta do histórico de interações (chamadas, mensagens e
  reuniões).
- Eliminação de interações.
- Proteção do histórico: uma oportunidade com interações associadas
  não deve ser eliminada.
- Modo de alto contraste para melhorar a legibilidade.
- Interface adaptável a computador, tablet e telemóvel.
Âmbito do MVP: a aplicação não inclui integração automática com o
WhatsApp, faturação, processamento de pagamentos, ligação bancária ou
funcionalidades de contabilidade.

Tecnologias
- Java 21
- Spring Boot
- Spring MVC e Thymeleaf
- Spring Data JPA / Hibernate
- Spring Security
- Bean Validation
- PostgreSQL
- Bootstrap 5.3
- Maven
- JUnit 5 e Mockito
- JaCoCo para análise da cobertura de testes
- GitHub Actions para integração contínua
Arquitetura
A aplicação segue uma arquitetura monolítica organizada por camadas:
- Model: entidades e tipos de domínio.
- Repository: acesso e persistência dos dados através do Spring
  Data JPA.
- Service: regras de negócio e validações.
- Controller: tratamento dos pedidos HTTP e navegação.
- Templates: páginas HTML renderizadas com Thymeleaf.
Pré-requisitos
Antes de executar o projeto, é necessário ter instalado:
- Java 21
- Maven 3.9 ou superior
- Docker e Docker Compose
- Git
Configuração e execução
1. Clonar o repositório
git clone https://github.com/Andreycosta-code/gestao-contatos-oportunidades.git
cd gestao-contatos-oportunidades
2. Configurar as variáveis de ambiente
Criar um ficheiro .env na raiz do projeto. Este ficheiro contém
credenciais locais e não deve ser enviado para o Git.

Exemplo de estrutura:
DB_PASSWORD=colocar_uma_password_forte
APP_USERNAME=eurico
APP_PASSWORD=colocar_uma_password_forte_e_unica
Utilizar palavras-passe fortes e diferentes para a base de dados e para
a aplicação. Não partilhar nem publicar os valores reais.
3. Iniciar a base de dados
Na raiz do projeto, executar:
docker compose up -d
O serviço PostgreSQL é iniciado com a configuração definida em
compose.yaml.
4. Carregar as variáveis de ambiente
Em Linux ou GitHub Codespaces, executar no mesmo terminal em que a
aplicação será iniciada:
set -a
source .env
set +a
export DB_USERNAME=gestor
export DB_URL=jdbc:postgresql://localhost:5432/gestao_contatos
Se o terminal ou a sessão do Codespaces for reiniciada, repetir este
passo.
5. Executar os testes
mvn clean verify
Este comando compila o projeto, executa os testes automatizados e,
quando configurado, gera o relatório de cobertura JaCoCo em:
target/site/jacoco/index.html
6. Iniciar a aplicação
mvn spring-boot:run
Abrir no navegador:
http://localhost:8080/
A aplicação solicita autenticação. Utilizar o nome de utilizador e a
palavra-passe definidos em APP_USERNAME e APP_PASSWORD no ficheiro
.env.
Testes e integração contínua
O projeto inclui testes automatizados com JUnit 5 e Mockito. O workflow
de GitHub Actions executa a verificação Maven (mvn clean verify) em
eventos de push e pull request configurados no repositório.

Para consultar a cobertura de testes localmente, executar
mvn clean verify e abrir o relatório JaCoCo indicado acima.
Segurança e dados
- As páginas da aplicação requerem autenticação.
- As palavras-passe são tratadas com BCrypt.
- As credenciais são fornecidas através de variáveis de ambiente.
- O ficheiro .env está excluído do controlo de versões.
- A eliminação de registos deve preservar a integridade das relações e
  o histórico de interações.
Não colocar credenciais, dados pessoais reais ou informação sensível no
repositório.
Estrutura geral do projeto
src/
├── main/
│   ├── java/
│   │   └── pt/gestaocontatos/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── resources/
│       ├── static/
│       ├── templates/
│       └── application.properties
└── test/
    └── java/
A estrutura concreta pode incluir subpacotes adicionais, conforme a
evolução do projeto.
Limitações e evolução futura
Possíveis melhorias, fora do âmbito atual do MVP:
- Integração com WhatsApp ou outros canais de comunicação.
- Notificações automáticas de prazos e seguimentos.
- Exportação de dados e relatórios.
- Gestão de vários utilizadores e diferentes níveis de acesso.
- Funcionalidades de faturação e acompanhamento de pagamentos.
Estas possibilidades constituem trabalho futuro e não devem ser
consideradas funcionalidades já implementadas.
Estado do projeto
Versão MVP desenvolvida para fins académicos, com foco na gestão de
contactos e oportunidades de trabalho de um profissional independente.
