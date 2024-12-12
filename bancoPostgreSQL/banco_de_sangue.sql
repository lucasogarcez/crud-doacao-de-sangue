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
    tipoERhCorretos BOOLEAN DEFAULT false,
    tipoSanguineo TipoSanguineo,
    rh RH,
    situacao Situacao DEFAULT 'ATIVO'
);

-- Criação da tabela Doacao
CREATE TABLE Doacao (
    codigo SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    volume DOUBLE PRECISION NOT NULL,
    doador_id INT REFERENCES Doador(codigo),
    situacao Situacao DEFAULT 'ATIVO'
);

SELECT * FROM Doador;

SELECT * FROM Doacao;