
CREATE DATABASE IF NOT EXISTS cadastro_clientes
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE cadastro_clientes;

CREATE TABLE IF NOT EXISTS cliente (
    id               INT          AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(30)  NOT NULL,
    cpf              VARCHAR(11)  NOT NULL UNIQUE,
    data_nascimento  DATE,
    telefone         VARCHAR(20),
    endereco         VARCHAR(150),
    bairro           VARCHAR(100),
    cidade           VARCHAR(100),
    estado           VARCHAR(2),
    cep              VARCHAR(10),
    status           BOOLEAN DEFAULT TRUE
);
INSERT IGNORE INTO cliente (nome, cpf, data_nascimento, telefone, endereco, bairro, cidade, estado, cep)
VALUES
  ('João Silva',  '12345678901', '1990-05-15', '(12) 99999-0001', 'Rua das Flores, 10',  'Centro',    'São José dos Campos', 'SP', '12210-000'),
  ('Maria Souza', '98765432100', '1985-11-20', '(12) 99999-0002', 'Av. Brasil, 200',      'Jardim',    'Taubaté',             'SP', '12030-000'),
  ('Carlos Lima', '11122233344', '2000-03-08', '(12) 99999-0003', 'Rua das Palmeiras, 5', 'Vila Nova', 'Jacareí',             'SP', '12300-000');

  

CREATE TABLE IF NOT EXISTS raca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_raca VARCHAR(100) NOT NULL,
    tipo_animal VARCHAR(20) NOT NULL,
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS animal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    sexo CHAR(1),
    cor VARCHAR(50),
    observacoes VARCHAR(255),

    id_cliente INT NOT NULL,
    id_raca INT NOT NULL,

    status BOOLEAN DEFAULT TRUE,

    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_raca) REFERENCES raca(id)
);

