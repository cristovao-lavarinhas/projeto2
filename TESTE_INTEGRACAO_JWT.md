# Teste da Integração JWT - DriveSmart

## 🔧 Configuração das Portas

- **Backend**: `http://localhost:8080` (API JWT)
- **Website**: `http://localhost:8081` (Interface do cliente)

## 🚀 Passos para Testar

### 1. **Iniciar o Backend**
```bash
cd backend_springboot
./gradlew bootRun
```
- Aguardar até aparecer "Started Application"
- Backend estará disponível em `http://localhost:8080`

### 2. **Iniciar o Website**
```bash
cd website
./gradlew bootRun
```
- Aguardar até aparecer "Started DrivesmartApplication"
- Website estará disponível em `http://localhost:8081`

### 3. **Testar Registo**

#### **Registo de Cliente**
1. Aceder a `http://localhost:8081/register`
2. Selecionar "Cliente"
3. Preencher formulário:
   - Nome: "João Silva"
   - Email: "joao@teste.com"
   - Password: "123456"
   - Telefone: "912345678"
   - NIF: "123456789"
4. Clicar "Registar"
5. Verificar no console do navegador (F12) se há logs de sucesso
6. Deverá redirecionar para login

#### **Registo de Motorista**
1. Aceder a `http://localhost:8081/register`
2. Selecionar "Motorista"
3. Preencher formulário:
   - Nome: "Maria Santos"
   - Email: "maria@teste.com"
   - Password: "123456"
   - Telefone: "912345679"
   - Carta de Condução: "123456789"
4. Clicar "Registar"
5. Verificar logs no console
6. Deverá redirecionar para login

### 4. **Testar Login**

#### **Login com Cliente**
1. Aceder a `http://localhost:8081/login`
2. Preencher:
   - Email: "joao@teste.com"
   - Password: "123456"
3. Clicar "Continuar"
4. Verificar logs no console
5. Deverá redirecionar para dashboard

#### **Login com Admin (já existe)**
1. Aceder a `http://localhost:8081/login`
2. Preencher:
   - Email: "admin@admin.com"
   - Password: "admin"
3. Clicar "Continuar"
4. Deverá redirecionar para dashboard

### 5. **Testar Dashboard**
1. Após login bem-sucedido, deverá ir para `http://localhost:8081/dashboard`
2. Verificar se o conteúdo é específico para o tipo de utilizador
3. Testar o botão "Sair"
4. Verificar se redireciona para login

## 🔍 Debug e Troubleshooting

### **Verificar Console do Navegador**
1. Abrir Developer Tools (F12)
2. Ir para aba "Console"
3. Procurar por:
   - Logs de tentativa de login/registo
   - Erros de CORS
   - Erros de rede

### **Verificar Network Tab**
1. Abrir Developer Tools (F12)
2. Ir para aba "Network"
3. Fazer login/registo
4. Verificar se as requisições para `localhost:8080` são bem-sucedidas

### **Verificar Logs do Backend**
1. No terminal do backend, procurar por:
   - Requisições recebidas
   - Erros de validação
   - Tokens JWT gerados

### **Verificar Logs do Website**
1. No terminal do website, procurar por:
   - Requisições processadas
   - Erros de renderização

## 🐛 Problemas Comuns e Soluções

### **Erro de CORS**
- **Sintoma**: Erro no console sobre CORS
- **Solução**: Verificar se o backend está a rodar na porta 8080

### **Erro de Rede**
- **Sintoma**: "Failed to fetch" no console
- **Solução**: Verificar se ambos os serviços estão a rodar

### **Token Inválido**
- **Sintoma**: Redirecionamento para login após tentativa de acesso
- **Solução**: Fazer logout e login novamente

### **Formulário não submete**
- **Sintoma**: Botão não responde
- **Solução**: Verificar se não há erros JavaScript no console

## 📋 Checklist de Teste

### **Registo**
- [ ] Formulário de registo carrega
- [ ] Seleção de tipo funciona
- [ ] Campos dinâmicos aparecem/desaparecem
- [ ] Registo de cliente funciona
- [ ] Registo de motorista funciona
- [ ] Redirecionamento para login funciona

### **Login**
- [ ] Formulário de login carrega
- [ ] Login com cliente funciona
- [ ] Login com admin funciona
- [ ] Redirecionamento para dashboard funciona

### **Dashboard**
- [ ] Dashboard carrega após login
- [ ] Conteúdo específico por tipo aparece
- [ ] Dados do utilizador são carregados
- [ ] Logout funciona
- [ ] Redirecionamento após logout funciona

### **Segurança**
- [ ] Acesso direto ao dashboard sem login é bloqueado
- [ ] Token JWT é armazenado no localStorage
- [ ] Token expirado causa logout automático

## 🎯 Resultado Esperado

Após todos os testes, deverás ter:
- ✅ Registo funcionando para clientes e motoristas
- ✅ Login funcionando com JWT
- ✅ Dashboard personalizado por tipo de utilizador
- ✅ Logout funcionando
- ✅ Proteção de rotas funcionando

## 📞 Se Algo Não Funcionar

1. **Verificar portas**: Backend na 8080, Website na 8081
2. **Verificar logs**: Console do navegador e terminais
3. **Verificar rede**: Network tab do Developer Tools
4. **Limpar cache**: Ctrl+F5 para refresh completo
5. **Verificar localStorage**: Developer Tools > Application > Local Storage 