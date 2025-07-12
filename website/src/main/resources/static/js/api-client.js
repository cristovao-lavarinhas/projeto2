// API Client para integração com o backend DriveSmart
class DriveSmartAPI {
    constructor(baseURL = 'http://localhost:8080') {
        this.baseURL = baseURL;
    }

    // Função para requisições autenticadas
    async authenticatedRequest(endpoint, options = {}) {
        const token = localStorage.getItem('jwt_token');
        
        if (!token) {
            throw new Error('Token não encontrado. Faça login primeiro.');
        }
        
        const defaultOptions = {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        };
        
        const finalOptions = { ...defaultOptions, ...options };
        
        const response = await fetch(`${this.baseURL}${endpoint}`, finalOptions);
        
        if (response.status === 401) {
            // Token expirado ou inválido
            localStorage.removeItem('jwt_token');
            localStorage.removeItem('user_data');
            window.location.href = '/login';
            return;
        }
        
        return response;
    }

    // Função para requisições públicas
    async publicRequest(endpoint, options = {}) {
        const defaultOptions = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        
        const finalOptions = { ...defaultOptions, ...options };
        
        return await fetch(`${this.baseURL}${endpoint}`, finalOptions);
    }

    // Autenticação
    async login(email, password) {
        try {
            const response = await this.publicRequest('/auth/login', {
                method: 'POST',
                body: JSON.stringify({ email, password })
            });
            
            const data = await response.json();
            
            if (response.ok) {
                // Armazenar token e dados do utilizador
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

    async registerMotorista(userData) {
        try {
            const response = await this.publicRequest('/auth/register-motorista', {
                method: 'POST',
                body: JSON.stringify(userData)
            });
            
            const data = await response.json();
            
            if (response.ok) {
                return data;
            } else {
                throw new Error(data);
            }
        } catch (error) {
            console.error('Erro no registo de motorista:', error);
            throw error;
        }
    }

    async registerCliente(userData) {
        try {
            const response = await this.publicRequest('/auth/register-cliente', {
                method: 'POST',
                body: JSON.stringify(userData)
            });
            
            const data = await response.json();
            
            if (response.ok) {
                return data;
            } else {
                throw new Error(data);
            }
        } catch (error) {
            console.error('Erro no registo de cliente:', error);
            throw error;
        }
    }

    async getCurrentUser() {
        try {
            const response = await this.authenticatedRequest('/auth/me');
            return await response.json();
        } catch (error) {
            console.error('Erro ao obter dados do utilizador:', error);
            throw error;
        }
    }

    // Clientes
    async getClientes() {
        try {
            const response = await this.authenticatedRequest('/clientes');
            return await response.json();
        } catch (error) {
            console.error('Erro ao obter clientes:', error);
            throw error;
        }
    }

    async getCliente(id) {
        try {
            const response = await this.authenticatedRequest(`/clientes/${id}`);
            return await response.json();
        } catch (error) {
            console.error('Erro ao obter cliente:', error);
            throw error;
        }
    }

    async createCliente(clienteData) {
        try {
            const response = await this.authenticatedRequest('/clientes/add', {
                method: 'POST',
                body: JSON.stringify(clienteData)
            });
            return await response.json();
        } catch (error) {
            console.error('Erro ao criar cliente:', error);
            throw error;
        }
    }

    async updateCliente(id, clienteData) {
        try {
            const response = await this.authenticatedRequest(`/clientes/${id}`, {
                method: 'PUT',
                body: JSON.stringify(clienteData)
            });
            return await response.json();
        } catch (error) {
            console.error('Erro ao atualizar cliente:', error);
            throw error;
        }
    }

    async deleteCliente(id) {
        try {
            const response = await this.authenticatedRequest(`/clientes/${id}`, {
                method: 'DELETE'
            });
            return response.ok;
        } catch (error) {
            console.error('Erro ao apagar cliente:', error);
            throw error;
        }
    }

    // Viagens
    async getViagens() {
        try {
            const response = await this.authenticatedRequest('/viagens');
            return await response.json();
        } catch (error) {
            console.error('Erro ao obter viagens:', error);
            throw error;
        }
    }

    async getViagem(id) {
        try {
            const response = await this.authenticatedRequest(`/viagens/${id}`);
            return await response.json();
        } catch (error) {
            console.error('Erro ao obter viagem:', error);
            throw error;
        }
    }

    async createViagem(viagemData) {
        try {
            const response = await this.authenticatedRequest('/viagens', {
                method: 'POST',
                body: JSON.stringify(viagemData)
            });
            return await response.json();
        } catch (error) {
            console.error('Erro ao criar viagem:', error);
            throw error;
        }
    }

    async updateViagem(id, viagemData) {
        try {
            const response = await this.authenticatedRequest(`/viagens/${id}`, {
                method: 'PUT',
                body: JSON.stringify(viagemData)
            });
            return await response.json();
        } catch (error) {
            console.error('Erro ao atualizar viagem:', error);
            throw error;
        }
    }

    async deleteViagem(id) {
        try {
            const response = await this.authenticatedRequest(`/viagens/${id}`, {
                method: 'DELETE'
            });
            return response.ok;
        } catch (error) {
            console.error('Erro ao apagar viagem:', error);
            throw error;
        }
    }

    // Avaliações
    async getAvaliacoes() {
        try {
            const response = await this.authenticatedRequest('/avaliacoes');
            return await response.json();
        } catch (error) {
            console.error('Erro ao obter avaliações:', error);
            throw error;
        }
    }

    async getAvaliacao(id) {
        try {
            const response = await this.authenticatedRequest(`/avaliacoes/${id}`);
            return await response.json();
        } catch (error) {
            console.error('Erro ao obter avaliação:', error);
            throw error;
        }
    }

    async createAvaliacao(avaliacaoData) {
        try {
            const response = await this.authenticatedRequest('/avaliacoes', {
                method: 'POST',
                body: JSON.stringify(avaliacaoData)
            });
            return await response.json();
        } catch (error) {
            console.error('Erro ao criar avaliação:', error);
            throw error;
        }
    }

    async updateAvaliacao(id, avaliacaoData) {
        try {
            const response = await this.authenticatedRequest(`/avaliacoes/${id}`, {
                method: 'PUT',
                body: JSON.stringify(avaliacaoData)
            });
            return await response.json();
        } catch (error) {
            console.error('Erro ao atualizar avaliação:', error);
            throw error;
        }
    }

    async deleteAvaliacao(id) {
        try {
            const response = await this.authenticatedRequest(`/avaliacoes/${id}`, {
                method: 'DELETE'
            });
            return response.ok;
        } catch (error) {
            console.error('Erro ao apagar avaliação:', error);
            throw error;
        }
    }

    // Utilitários
    isAuthenticated() {
        const token = localStorage.getItem('jwt_token');
        return token !== null;
    }

    logout() {
        localStorage.removeItem('jwt_token');
        localStorage.removeItem('user_data');
        window.location.href = '/auth/login';
    }

    getUserData() {
        const userData = localStorage.getItem('user_data');
        return userData ? JSON.parse(userData) : null;
    }

    getToken() {
        return localStorage.getItem('jwt_token');
    }
}

// Instância global da API
const api = new DriveSmartAPI();

// Exemplo de uso:
/*
// Login
api.login('user@example.com', 'password123')
    .then(data => {
        console.log('Login bem-sucedido:', data);
        // Redirecionar para dashboard
    })
    .catch(error => {
        console.error('Erro no login:', error);
    });

// Obter dados do utilizador
api.getCurrentUser()
    .then(user => {
        console.log('Dados do utilizador:', user);
    })
    .catch(error => {
        console.error('Erro ao obter dados:', error);
    });

// Listar viagens
api.getViagens()
    .then(viagens => {
        console.log('Viagens:', viagens);
    })
    .catch(error => {
        console.error('Erro ao obter viagens:', error);
    });
*/ 