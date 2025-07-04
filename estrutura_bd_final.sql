
-- Script completo e atualizado da base de dados

-- Tabela: Utilizador
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
    utilizador_id INT UNIQUE REFERENCES utilizador(id),
    licenca VARCHAR(50),
    estado VARCHAR(50)
);

-- Tabela: Cliente
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    utilizador_id INT UNIQUE REFERENCES utilizador(id)
);

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
