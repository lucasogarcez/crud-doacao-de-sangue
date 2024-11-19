-- Criação das enumerações
CREATE TYPE Situacao AS ENUM ('ATIVO', 'INATIVO');
CREATE TYPE TipoSanguineo AS ENUM ('A', 'B', 'AB', 'O', 'DESCONHECIDO');
CREATE TYPE RH AS ENUM ('POSITIVO', 'NEGATIVO', 'DESCONHECIDO');

-- Criação da tabela Doador
CREATE TABLE Doador (
    codigo SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    contato VARCHAR(15),
    tipoERCorretos BOOLEAN DEFAULT false,
    tipoSanguineo TipoSanguineo,
    rh RH,
    situacao Situacao
);

-- Criação da tabela Doacao
CREATE TABLE Doacao (
    codigo SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    volume DOUBLE PRECISION NOT NULL,
    doador_id INTEGER REFERENCES Doador(codigo),
    situacao Situacao
);

--VALORES NÃO INSERIDOS (A INSERIR ATRAVÉS DO CÓDIGO POOV)

-- Inserindo valores na tabela Doador
INSERT INTO Doador (nome, cpf, contato, tipoERCorretos, tipoSanguineo, rh, situacao) VALUES
('Carlos Silva', '12345678901', '5599112233', true, 'O', 'POSITIVO', 'ATIVO'),
('Ana Costa', '98765432100', '5599445566', false, 'A', 'NEGATIVO', 'INATIVO'),
('Lucas Souza', '12312312399', '5599887766', true, 'AB', 'POSITIVO', 'ATIVO'),
('Mariana Lima', '32132132188', '5599334455', true, 'B', 'DESCONHECIDO', 'ATIVO');

-- Inserindo valores na tabela Doacao
INSERT INTO Doacao (data, hora, volume, doador_id, situacao) VALUES
('2024-11-10', '08:30:00', 450.0, 1, 'ATIVO'),
('2024-11-12', '10:15:00', 500.0, 2, 'INATIVO'),
('2024-11-14', '14:00:00', 470.0, 3, 'ATIVO'),
('2024-11-16', '09:45:00', 480.0, 4, 'ATIVO');
