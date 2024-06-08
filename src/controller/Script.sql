-----------------------------------------------TB_USUARIO------------------------------------------------------------------------------------
CREATE TABLE tb_usuario (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100),
    senha VARCHAR(32),
    role BOOLEAN DEFAULT FALSE
);

-- USUARIO ADMINISTRADOR NECESSARIO PARA ACESSAR À PARTE ADMINISTRATIVA

insert into tb_usuario(email,senha,role) values ('plotze', MD5('plotze'), true);
-----------------------------------------------TB_PRODUTO-----------------------------------------------------------------------------------

CREATE TABLE tb_produto (
    id SERIAL PRIMARY KEY,
    quant INTEGER NOT NULL,
    disponivel BOOLEAN NOT NULL,
    preco DOUBLE PRECISION NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT
);

-----------------------------------------------TB_CARRINHO-----------------------------------------------------------------------------------
CREATE TABLE tb_carrinho (
    id_usuario INTEGER,
    id_produto INTEGER,
    quantidade INTEGER NOT NULL,
    total DOUBLE PRECISION,
    PRIMARY KEY (id_usuario, id_produto),
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id),
    FOREIGN KEY (id_produto) REFERENCES tb_produto(id)
);

-----------------------------------------------TB_VENDAS------------------------------------------------------------------------------------
CREATE TABLE tb_vendas (
    id SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_produto INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    data_venda TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id),
    FOREIGN KEY (id_produto) REFERENCES tb_produto(id)
);


SELECT * FROM tb_usuario;
SELECT * FROM tb_produto;
SELECT * FROM tb_carrinho;
SELECT * FROM tb_vendas;






