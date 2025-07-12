# API JWT Documentation - DriveSmart Backend

## Base URL
```
http://localhost:8080
```

## Autenticação JWT

### 1. Login
**POST** `/auth/login`

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (Sucesso - 200):**
```json
{
  "message": "Login realizado com sucesso",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "nome": "João Silva",
  "tipo": "MOTORISTA",
  "userId": 1
}
```

**Response (Erro - 401):**
```json
"Credenciais inválidas"
```

### 2. Registo de Motorista
**POST** `/auth/register-motorista`

**Request Body:**
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "password": "password123",
  "telefone": "912345678",
  "cartaConducao": "123456789"
}
```

**Response (Sucesso - 200):**
```json
"Motorista registado com sucesso. Aguarde aprovação."
```

### 3. Registo de Cliente
**POST** `/auth/register-cliente`

**Request Body:**
```json
{
  "nome": "Maria Santos",
  "email": "maria@example.com",
  "password": "password123",
  "telefone": "912345678",
  "nif": "123456789"
}
```

**Response (Sucesso - 200):**
```json
"Cliente registado com sucesso."
```

### 4. Obter Dados do Utilizador Autenticado
**GET** `/auth/me`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response (Sucesso - 200):**
```json
{
  "id": 1,
  "email": "joao@example.com",
  "tipo": "MOTORISTA",
  "nome": "João Silva",
  "motorista": {
    "id": 1,
    "nome": "João Silva",
    "estado": "APROVADO",
    "telefone": "912345678"
  }
}
```

## Endpoints Protegidos

### Clientes
- **GET** `/clientes` - Listar todos os clientes
- **GET** `/clientes/{id}` - Obter cliente por ID
- **POST** `/clientes/add` - Criar novo cliente
- **PUT** `/clientes/{id}` - Atualizar cliente
- **DELETE** `/clientes/{id}` - Apagar cliente

### Viagens
- **GET** `/viagens` - Listar todas as viagens
- **GET** `/viagens/{id}` - Obter viagem por ID
- **POST** `/viagens` - Criar nova viagem
- **PUT** `/viagens/{id}` - Atualizar viagem
- **DELETE** `/viagens/{id}` - Apagar viagem

### Avaliações
- **GET** `/avaliacoes` - Listar todas as avaliações
- **GET** `/avaliacoes/{id}` - Obter avaliação por ID
- **POST** `/avaliacoes` - Criar nova avaliação
- **PUT** `/avaliacoes/{id}` - Atualizar avaliação
- **DELETE** `/avaliacoes/{id}` - Apagar avaliação

## Como Usar no Frontend

### 1. Login e Armazenamento do Token
```javascript
async function login(email, password) {
  try {
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password })
    });
    
    const data = await response.json();
    
    if (response.ok) {
      // Armazenar token no localStorage
      localStorage.setItem('jwt_token', data.token);
      localStorage.setItem('user_data', JSON.stringify({
        nome: data.nome,
        tipo: data.tipo,
        userId: data.userId
      }));
      return data;
    } else {
      throw new Error(data);
    }
  } catch (error) {
    console.error('Erro no login:', error);
    throw error;
  }
}
```

### 2. Função para Requisições Autenticadas
```javascript
async function authenticatedRequest(url, options = {}) {
  const token = localStorage.getItem('jwt_token');
  
  if (!token) {
    throw new Error('Token não encontrado');
  }
  
  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }
  };
  
  const finalOptions = { ...defaultOptions, ...options };
  
  const response = await fetch(url, finalOptions);
  
  if (response.status === 401) {
    // Token expirado ou inválido
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('user_data');
    window.location.href = '/login';
    return;
  }
  
  return response;
}
```

### 3. Exemplo de Uso
```javascript
// Obter dados do utilizador
async function getCurrentUser() {
  const response = await authenticatedRequest('http://localhost:8080/auth/me');
  return await response.json();
}

// Listar viagens
async function getViagens() {
  const response = await authenticatedRequest('http://localhost:8080/viagens');
  return await response.json();
}

// Criar nova viagem
async function createViagem(viagemData) {
  const response = await authenticatedRequest('http://localhost:8080/viagens', {
    method: 'POST',
    body: JSON.stringify(viagemData)
  });
  return await response.json();
}
```

### 4. Verificação de Autenticação
```javascript
function isAuthenticated() {
  const token = localStorage.getItem('jwt_token');
  return token !== null;
}

function logout() {
  localStorage.removeItem('jwt_token');
  localStorage.removeItem('user_data');
  window.location.href = '/login';
}
```

## Configuração CORS

O backend já está configurado para aceitar requisições de qualquer origem (`*`), então não há necessidade de configuração adicional no frontend.

## Tipos de Utilizador

- **ADMIN**: Administrador do sistema
- **MOTORISTA**: Motorista registado (requer aprovação)
- **CLIENTE**: Cliente registado

## Notas Importantes

1. **Token Expiração**: Os tokens JWT expiram em 24 horas por padrão
2. **Segurança**: Sempre use HTTPS em produção
3. **Armazenamento**: O token é armazenado no localStorage do navegador
4. **Refresh**: Implementar refresh token para melhor segurança em produção 