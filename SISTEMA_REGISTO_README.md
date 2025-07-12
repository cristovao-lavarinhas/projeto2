# Sistema de Registo - Projeto2

## Descrição
Este sistema implementa um sistema completo de registo e login para motoristas, seguindo a mesma estrutura do sistema de login existente.

## Funcionalidades Implementadas

### 1. Sistema de Registo
- **Interface**: `Register.fxml` - Interface gráfica para registo de motoristas
- **Controller**: `RegisterController.java` - Lógica de controlo do registo
- **Campos obrigatórios**:
  - Nome completo
  - E-mail
  - Password
  - Telefone
  - Carta de condução

### 2. Sistema de Autenticação Backend
- **Modelo**: `Usuario.java` - Entidade de usuário
- **Repository**: `UsuarioRepository.java` - Acesso aos dados
- **Service**: `UsuarioService.java` - Lógica de negócio
- **Controller**: `UsuarioController.java` - Endpoints REST

### 3. Endpoints da API
- `POST /usuarios/registar` - Registo de novo motorista
- `POST /usuarios/login` - Autenticação de usuário
- `GET /usuarios` - Listar todos os usuários
- `GET /usuarios/{id}` - Buscar usuário por ID

### 4. Integração com Login Existente
- O sistema de login foi atualizado para suportar tanto admin quanto motoristas registados
- Navegação entre login e registo implementada

## Como Usar

### 1. Iniciar o Backend
```bash
cd backend_springboot
./gradlew bootRun
```

### 2. Iniciar o Frontend
```bash
cd javafx
./gradlew run
```

### 3. Registo de Motorista
1. Na tela de login, clicar em "Criar conta"
2. Preencher todos os campos obrigatórios
3. Clicar em "Criar Conta"
4. Após sucesso, será redirecionado para o login

### 4. Login
1. Usar as credenciais criadas no registo
2. O sistema irá redirecionar para o dashboard apropriado

## Estrutura de Arquivos

### Frontend (JavaFX)
```
javafx/src/main/java/com/example/projetojavafx/Controller/
├── LoginController.java (atualizado)
└── RegisterController.java (novo)

javafx/src/main/resources/com/example/projetojavafx/
├── Login.fxml (atualizado)
├── Register.fxml (novo)
└── style.css (atualizado com estilos para DatePicker)
```

### Backend (Spring Boot)
```
backend_springboot/src/main/java/com/example/projeto2/base/
├── model/
│   └── Usuario.java (novo)
├── repository/
│   └── UsuarioRepository.java (novo)
├── service/
│   └── UsuarioService.java (novo)
├── controller/
│   └── UsuarioController.java (novo)
└── resources/
    └── schema.sql (novo)
```

## Banco de Dados

### Tabela Usuario
```sql
CREATE TABLE usuario (
    idUsuario BIGINT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    idMotorista BIGINT,
    idCliente BIGINT
);
```

### Tipos de Usuário
- `ADMIN` - Administrador do sistema
- `MOTORISTA` - Motorista registado
- `CLIENTE` - Cliente (futuro)

## Segurança

**Nota**: O sistema atual não implementa encriptação de passwords por simplicidade. Para produção, recomenda-se:

1. Adicionar Spring Security ao projeto
2. Implementar BCrypt para encriptação de passwords
3. Implementar JWT para autenticação
4. Adicionar validação de dados mais robusta

## Próximos Passos

1. Implementar encriptação de passwords
2. Adicionar validação de e-mail
3. Implementar recuperação de password
4. Adicionar sistema de roles e permissões
5. Implementar registo de clientes
6. Adicionar testes unitários

## Troubleshooting

### Erro de Conexão
- Verificar se o backend está a correr na porta 8080
- Verificar se o banco de dados PostgreSQL está acessível

### Erro de Registo
- Verificar se todos os campos estão preenchidos
- Verificar se o e-mail não já existe no sistema

### Erro de Login
- Verificar se as credenciais estão corretas
- Verificar se o usuário foi registado com sucesso 