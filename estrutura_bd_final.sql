
-- Script completo e atualizado da base de dados

-- Sequências para compatibilidade com o sistema Spring Boot
CREATE SEQUENCE IF NOT EXISTS idUsuarioSeq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS idMotoristaSeq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS idClienteSeq START WITH 1 INCREMENT BY 1;

-- Tabela: Utilizador (compatível com o sistema de registo)
CREATE TABLE utilizador (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nome VARCHAR(100),
    telefone VARCHAR(20),
    tipo VARCHAR(20) CHECK (tipo IN ('admin', 'motorista', 'cliente')) NOT NULL
);

-- Tabela: Motorista
CREATE TABLE motorista (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tel VARCHAR(15) NOT NULL,
    cartaConducao VARCHAR(20) NOT NULL,
    estado VARCHAR(20),
    dtNascimento DATE NOT NULL,
    idAvaliacao INT REFERENCES avaliacao(id),
);

-- Tabela: Cliente
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    utilizador_id INT UNIQUE REFERENCES utilizador(id)
);

-- Tabela: Usuario (para compatibilidade com o sistema Spring Boot)
CREATE TABLE usuario (
    idUsuario BIGINT PRIMARY KEY DEFAULT nextval('idUsuarioSeq'),
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    idMotorista BIGINT,
    idCliente BIGINT,
    FOREIGN KEY (idMotorista) REFERENCES motorista(id),
    FOREIGN KEY (idCliente) REFERENCES cliente(id)
);

-- Inserir usuário admin padrão
INSERT INTO usuario (email, password, tipo) 
VALUES ('admin@admin.com', 'admin', 'ADMIN')
ON CONFLICT (email) DO NOTHING;

-- Tabela: PedidoInscricao
CREATE TABLE pedido_inscricao (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100),
    telefone VARCHAR(20),
    estado VARCHAR(50)
);

-- Tabela: DocumentoMotorista
CREATE TABLE documento_motorista (
    id SERIAL PRIMARY KEY,
    motorista_id INT REFERENCES motorista(id),
    licenca_tvde BOOLEAN,
    seguro_viagem BOOLEAN,
    documento_veiculo BOOLEAN
);

-- Tabela: Documento
CREATE TABLE documento (
    id SERIAL PRIMARY KEY,
    motorista_id INT REFERENCES motorista(id),
    tipo_documento VARCHAR(100),
    data_expiracao DATE
);

-- Tabela: Saldo
CREATE TABLE saldo (
    id SERIAL PRIMARY KEY,
    motorista_id INT REFERENCES motorista(id),
    saldo DECIMAL(10, 2)
);

-- Tabela: Viatura
CREATE TABLE viatura (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    matricula VARCHAR(20) UNIQUE,
    ano VARCHAR(10),
    cor VARCHAR(30)
);

-- Tabela: EstadoViatura
CREATE TABLE estado_viatura (
    id SERIAL PRIMARY KEY,
    viatura_id INT REFERENCES viatura(id),
    estado VARCHAR(100)
);

-- Tabela: ReservaViagem
CREATE TABLE reserva_viagem (
    id SERIAL PRIMARY KEY,
    cliente_id INT REFERENCES cliente(id),
    motorista_id INT REFERENCES motorista(id),
    partida VARCHAR(100),
    destino VARCHAR(100),
    data_reserva DATE,
    estado VARCHAR(20) CHECK (estado IN ('pendente', 'aceite', 'rejeitada', 'cancelada')) DEFAULT 'pendente',
    data_aceite TIMESTAMP
);

-- Tabela: Viagem (opcionalmente associada a uma reserva aceite)
CREATE TABLE viagem (
    id SERIAL PRIMARY KEY,
    motorista_id INT REFERENCES motorista(id),
    viatura_id INT REFERENCES viatura(id),
    reserva_id INT REFERENCES reserva_viagem(id),
    partida VARCHAR(100),
    destino VARCHAR(100),
    data_viagem DATE,
    estado VARCHAR(50)
);

-- Tabela: Fatura
CREATE TABLE fatura (
    id SERIAL PRIMARY KEY,
    motorista_id INT REFERENCES motorista(id),
    data DATE,
    valor DECIMAL(10, 2),
    descricao TEXT
);
