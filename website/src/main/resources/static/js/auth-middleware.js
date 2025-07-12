// Middleware de autenticação para o website DriveSmart
class AuthMiddleware {
    constructor() {
        this.api = window.api; // Assumindo que api-client.js está carregado
    }

    // Verificar se o utilizador está autenticado
    isAuthenticated() {
        return this.api && this.api.isAuthenticated();
    }

    // Obter dados do utilizador
    getUserData() {
        return this.api ? this.api.getUserData() : null;
    }

    // Verificar se o utilizador tem um tipo específico
    hasRole(role) {
        const userData = this.getUserData();
        return userData && userData.tipo === role;
    }

    // Verificar se o utilizador tem pelo menos um dos tipos especificados
    hasAnyRole(roles) {
        const userData = this.getUserData();
        return userData && roles.includes(userData.tipo);
    }

    // Redirecionar para login se não autenticado
    requireAuth(redirectUrl = '/login') {
        if (!this.isAuthenticated()) {
            window.location.href = redirectUrl;
            return false;
        }
        return true;
    }

    // Redirecionar se não tiver role específica
    requireRole(role, redirectUrl = '/login') {
        if (!this.requireAuth(redirectUrl)) {
            return false;
        }
        
        if (!this.hasRole(role)) {
            window.location.href = '/unauthorized';
            return false;
        }
        return true;
    }

    // Redirecionar se não tiver pelo menos uma das roles
    requireAnyRole(roles, redirectUrl = '/login') {
        if (!this.requireAuth(redirectUrl)) {
            return false;
        }
        
        if (!this.hasAnyRole(roles)) {
            window.location.href = '/unauthorized';
            return false;
        }
        return true;
    }

    // Redirecionar utilizadores autenticados para dashboard apropriado
    redirectAuthenticatedUsers() {
        if (this.isAuthenticated()) {
            const userData = this.getUserData();
            if (userData) {
                switch(userData.tipo) {
                    case 'ADMIN':
                        window.location.href = '/dashboard';
                        break;
                    case 'MOTORISTA':
                        window.location.href = '/dashboard';
                        break;
                    case 'CLIENTE':
                        window.location.href = '/dashboard';
                        break;
                    default:
                        window.location.href = '/dashboard';
                }
            }
        }
    }

    // Logout
    logout() {
        if (this.api) {
            this.api.logout();
        } else {
            localStorage.removeItem('jwt_token');
            localStorage.removeItem('user_data');
            window.location.href = '/login';
        }
    }

    // Atualizar UI com dados do utilizador
    updateUserUI() {
        const userData = this.getUserData();
        if (userData) {
            // Atualizar nome do utilizador
            const userNameElements = document.querySelectorAll('.user-name');
            userNameElements.forEach(element => {
                element.textContent = userData.nome;
            });

            // Atualizar tipo do utilizador
            const userTypeElements = document.querySelectorAll('.user-type');
            userTypeElements.forEach(element => {
                element.textContent = userData.tipo;
            });

            // Mostrar/esconder elementos baseado no tipo
            this.updateUIByRole(userData.tipo);
        }
    }

    // Atualizar UI baseado no role do utilizador
    updateUIByRole(role) {
        // Esconder todos os elementos específicos por role
        document.querySelectorAll('[data-role]').forEach(element => {
            element.style.display = 'none';
        });

        // Mostrar elementos específicos do role
        document.querySelectorAll(`[data-role="${role}"]`).forEach(element => {
            element.style.display = 'block';
        });

        // Mostrar elementos comuns
        document.querySelectorAll('[data-role="all"]').forEach(element => {
            element.style.display = 'block';
        });
    }

    // Verificar se o token está expirado
    isTokenExpired() {
        const token = localStorage.getItem('jwt_token');
        if (!token) return true;

        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const currentTime = Date.now() / 1000;
            return payload.exp < currentTime;
        } catch (error) {
            return true;
        }
    }

    // Renovar token se necessário
    async refreshTokenIfNeeded() {
        if (this.isTokenExpired()) {
            this.logout();
            return false;
        }
        return true;
    }
}

// Instância global do middleware
const authMiddleware = new AuthMiddleware();

// Função para inicializar o middleware em páginas protegidas
function initAuthMiddleware(options = {}) {
    const {
        requireAuth = false,
        requiredRole = null,
        requiredRoles = null,
        redirectAuthenticated = false
    } = options;

    document.addEventListener('DOMContentLoaded', function() {
        // Verificar se o token está expirado
        if (authMiddleware.isTokenExpired()) {
            authMiddleware.logout();
            return;
        }

        // Redirecionar utilizadores autenticados (para páginas de login/registo)
        if (redirectAuthenticated) {
            authMiddleware.redirectAuthenticatedUsers();
            return;
        }

        // Verificar autenticação se necessário
        if (requireAuth && !authMiddleware.requireAuth()) {
            return;
        }

        // Verificar role específico
        if (requiredRole && !authMiddleware.requireRole(requiredRole)) {
            return;
        }

        // Verificar roles múltiplas
        if (requiredRoles && !authMiddleware.requireAnyRole(requiredRoles)) {
            return;
        }

        // Atualizar UI com dados do utilizador
        authMiddleware.updateUserUI();
    });
}

// Exemplo de uso:
/*
// Para páginas que requerem autenticação
initAuthMiddleware({ requireAuth: true });

// Para páginas que requerem role específico
initAuthMiddleware({ requiredRole: 'ADMIN' });

// Para páginas que requerem uma das roles
initAuthMiddleware({ requiredRoles: ['ADMIN', 'MOTORISTA'] });

// Para páginas de login/registo (redirecionar se já autenticado)
initAuthMiddleware({ redirectAuthenticated: true });
*/ 