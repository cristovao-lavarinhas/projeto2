# Configuração de Email

## Problema
O sistema de recuperação de password e registo de motoristas não está a funcionar porque as credenciais de email não estão configuradas corretamente.

## Solução

### 1. Configurar Gmail

Para usar Gmail como servidor de email, siga estes passos:

1. **Ativar autenticação de 2 fatores** na sua conta Google:
   - Vá para https://myaccount.google.com/security
   - Ative "Verificação em 2 passos"

2. **Gerar password de app**:
   - Vá para https://myaccount.google.com/apppasswords
   - Selecione "Email" como app
   - Copie a password gerada (16 caracteres)

### 2. Configurar no Código

Edite o arquivo `EmailConfig.java` e altere estas linhas:

```java
email = "seu-email@gmail.com"; // <-- ALTERA para o teu email
password = "sua-password-app"; // <-- ALTERA para a password de app gerada
```

### 3. Alternativa: Usar Variáveis de Ambiente

Se preferir usar variáveis de ambiente, configure:

```bash
# Windows (PowerShell)
$env:EMAIL_USERNAME="seu-email@gmail.com"
$env:EMAIL_PASSWORD="sua-password-app"

# Linux/Mac
export EMAIL_USERNAME="seu-email@gmail.com"
export EMAIL_PASSWORD="sua-password-app"
```

### 4. Testar

Após configurar, reinicie o servidor Spring Boot e teste:

1. **Registo de motorista**: Deve enviar email de confirmação
2. **Recuperação de password**: Deve enviar código por email
3. **Login 2FA**: Deve enviar código por email

### 5. Troubleshooting

- **Erro "Invalid credentials"**: Verifique se a password de app está correta
- **Erro "Access denied"**: Verifique se a autenticação de 2 fatores está ativada
- **Email não chega**: Verifique a pasta de spam

### Nota de Segurança

- Nunca commite credenciais reais no código
- Use variáveis de ambiente em produção
- A password de app é mais segura que a password normal da conta 