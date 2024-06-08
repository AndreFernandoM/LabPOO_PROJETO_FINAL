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

---------------------------------------------CRIAÇÃO PRODUTOS-------------------------------------------------------------------------------

INSERT INTO public.tb_produto (quant,disponivel,preco,nome,descricao) VALUES
	 (50,true,29.99,'Racao para Cachorro Adulto','Racao premium para cees adultos de todas as raças. Enriquecida com vitaminas e minerais.'),
	 (30,true,19.99,'Racao para Gato Adulto','Racao sabor salmeo para gatos adultos. Ajuda na saúde dos dentes e pele.'),
	 (40,true,15.99,'Brinquedo de Morder para Cees','Brinquedo resistente para cees de todas as idades. Ideal para manter o seu pet entretido.'),
	 (25,true,49.99,'Arranhador para Gatos','Arranhador com verias plataformas para gatos. Ajuda a manter as unhas saudeveis.'),
	 (60,true,9.99,'Petisco para Cees','Petisco sabor carne para cees. Ótimo para adestramento.'),
	 (35,true,8.99,'Petisco para Gatos','Petisco sabor frango para gatos. Ajuda na saúde do trato urinerio.'),
	 (20,true,79.99,'Cama para Cachorro','Cama macia e confortevel para cees de porte medio.'),
	 (15,true,89.99,'Cama para Gato','Cama estilo iglu para gatos. Proporciona conforto e segurança.'),
	 (50,true,4.99,'Bola de Borracha para Cachorro','Bola de borracha resistente e colorida para cees.'),
	 (30,true,24.99,'Areia Higienica para Gatos','Areia higienica com controle de odores.');
INSERT INTO public.tb_produto (quant,disponivel,preco,nome,descricao) VALUES
	 (20,true,14.99,'Shampoo para Cachorros','Shampoo suave para cees, indicado para todos os tipos de pelagem.'),
	 (25,true,14.99,'Shampoo para Gatos','Shampoo suave para gatos, ideal para manter a pelagem brilhante e saudevel.'),
	 (40,true,39.99,'Coleira para Cachorro','Coleira ajustevel e resistente para cees de medio porte.'),
	 (35,true,29.99,'Coleira para Gato','Coleira ajustevel com sininho para gatos.'),
	 (45,true,19.99,'Pote de Racao para Cachorro','Pote de racao em aço inoxidevel para cees. Capacidade de 1 litro.'),
	 (30,true,59.99,'Casinha para Cachorro','Casinha de plestico para cees de pequeno porte.'),
	 (20,true,69.99,'Transportadora para Gatos','Transportadora segura e confortevel para gatos.'),
	 (50,true,5.99,'Osso de Nylon para Cachorro','Osso de nylon durevel para cees de todos os tamanhos.'),
	 (60,true,11.99,'Brinquedo de Pelúcia para Gatos','Brinquedo de pelúcia com catnip para gatos.'),
	 (99,false,99.99,'Produto D','Descrição do Produto D');
INSERT INTO public.tb_produto (quant,disponivel,preco,nome,descricao) VALUES
	 (9,false,20.99,'Produto A','Descrição do Produto A'),
	 (0,false,49.99,'ESGOTADO','ESGOTADO ESGOTADO.'),
	 (31,true,19.99,'Pote de Racao para Gato','Pote de racao em aço inoxidevel para gatos. Capacidade de 500 ml.');




