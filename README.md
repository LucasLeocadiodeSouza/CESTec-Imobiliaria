# CESTec Imobiliária

Sistema web de gestão imobiliária desenvolvido com **Spring Boot 3.4.4** e **Thymeleaf**.

## 📋 Descrição do Projeto

CESTec Imobiliária é uma aplicação completa para gerenciamento de imóveis, oferecendo funcionalidades para:

- **Cadastro e gestão de imóveis** (CRI)
- **Gestão de contratos** (GDC)
- **Gerenciamento de manutenção de imóveis** (MRB)
- **Operações** (OPR)
- **Gestão de pagamentos** (PGA)
- **Relatórios** (REL)

## 🛠️ Tecnologias

### Backend
- **Java 17**
- **Spring Boot 3.4.4**
  - Spring Data JPA
  - Spring Web
  - Spring DevTools
- **Thymeleaf** - Motor de templates
- **MySQL 8.0** - Banco de dados
- **Hibernate** - ORM

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript (ES Modules)**
- **Bootstrap/Custom CSS**

### Ferramentas
- **Maven** - Gerenciador de dependências
- **Git** - Controle de versão

## 📁 Estrutura do Projeto

```
CESTec-Imobiliaria/
├── src/
│   ├── main/
│   │   ├── java/com/cestec/
│   │   │   └── cestec/           # Código Java da aplicação
│   │   │       ├── controller/    # Controladores MVC
│   │   │       ├── service/       # Lógica de negócios
│   │   │       ├── repository/    # Acesso a dados
│   │   │       ├── model/         # Entidades JPA
│   │   │       ├── infra/         # Configurações da infraestrutura
│   │   │       └── util/          # Utilitários
│   │   └── resources/
│   │       ├── application.properties  # Configurações da aplicação
│   │       ├── static/
│   │       │   ├── css/          # Estilos CSS
│   │       │   ├── js/           # Scripts JavaScript
│   │       │   │   ├── cri/      # Imóveis
│   │       │   │   ├── gdc/      # Contratos
│   │       │   │   ├── mrb/      # Manutenção
│   │       │   │   ├── opr/      # Operações
│   │       │   │   ├── pga/      # Pagamentos
│   │       │   │   └── rel/      # Relatórios
│   │       │   └── icons/        # Ícones
│   │       └── templates/        # Templates Thymeleaf
│   └── test/
│       └── java/                 # Testes unitários
├── pom.xml                        # Configuração Maven
├── package.json                   # Configuração Node.js
├── mvnw / mvnw.cmd               # Maven Wrapper
└── target/                        # Artefatos compilados

```

## 🚀 Como Executar

### Pré-requisitos
- **Java 17+** instalado
- **MySQL 8.0+** instalado e em execução
- **Maven 3.6+** ou usar Maven Wrapper

### Configuração do Banco de Dados

1. Crie um banco de dados MySQL:
```sql
CREATE DATABASE cestec;
```

2. Configure as credenciais em `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cestec
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Execução

#### Windows (usando Maven Wrapper):
```powershell
mvnw.cmd spring-boot:run
```

#### Linux/Mac (usando Maven Wrapper):
```bash
./mvnw spring-boot:run
```

#### Com Maven instalado:
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📝 Configurações Principais

### application.properties
- **Porta**: 8080 (padrão)
- **Banco de Dados**: MySQL configurado remotamente
- **JPA/Hibernate**: Modo validação (ddl-auto=validate)
- **Email**: Integração com Mailtrap para testes
- **Segurança**: JWT token secret configurável por variável de ambiente

## 🔐 Segurança

- Autenticação via **JWT Token**
- Token secret configurável via variável de ambiente `JWT_SECRET`
- Headers de segurança para proxy reverso

## 📬 Funcionalidades por Módulo

| Módulo | Sigla | Descrição |
|--------|-------|-----------|
| Imóveis | CRI | Cadastro e gestão de propriedades imobiliárias |
| Contratos | GDC | Gerenciamento de contratos de locação/venda |
| Manutenção | MRB | Registro e acompanhamento de manutenção |
| Operações | OPR | Operações gerais do sistema |
| Pagamentos | PGA | Gestão de pagamentos e faturas |
| Relatórios | REL | Geração de relatórios financeiros e operacionais |

## 📊 Banco de Dados

- **Tipo**: MySQL 8.0
- **Host Remoto**: 31.97.95.40:3306
- **Dialect Hibernate**: MySQL8Dialect
- **Modo DDL**: validate (tabelas devem existir)

## 🔄 Recursos de Desenvolvimento

- **Spring DevTools**: Recompilação automática e reload de página
- **SQL Query**: Arquivo `DB_CESTEC_server.session.sql` com queries de desenvolvimento
- **Logs**: Configuração de debug para Spring Web e Hibernate

## 📦 Build

Para gerar o artefato compilado:

```bash
mvn clean package
```

O JAR gerado estará em `target/cestec-0.0.1-SNAPSHOT.jar`

## 👨‍💻 Desenvolvedor

**Lucas Leocadio de Souza**
- GitHub: [LucasLeocadiodeSouza](https://github.com/LucasLeocadiodeSouza)

## 📄 Licença

ISC

## 📞 Suporte

Para dúvidas ou problemas, abra uma [issue](https://github.com/LucasLeocadiodeSouza/CESTec-Imobiliaria/issues) no repositório GitHub.

---

**Última atualização**: Dezembro de 2025
