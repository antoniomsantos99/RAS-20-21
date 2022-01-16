-- drop schema rasbet

create schema rasbet;
use rasbet;

CREATE TABLE `Utilizador` (
  `email` varchar(255) PRIMARY KEY,
  `username` varchar(255),
  `password` varchar(255),
  `carteira` float
);

CREATE TABLE `Aposta` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `idUtilizador` varchar(255),
  `valorApostado` float,
  `estado` varchar(255)
);

CREATE TABLE `ApostaJogo` (
  `idAposta` int,
  `idJogo` varchar(255),
  `opcao` int
);

CREATE TABLE `Jogo` (
  `id` varchar(255) PRIMARY KEY,
  `Competicao` varchar(255),
  `Participante1` varchar(255),
  `Participante2` varchar(255),
  `Odd1` float,
  `Odd2` float,
  `Odd3` float,
  `resultado` varchar(255),
  `data` datetime,
  `localizacao` varchar(255),
  `estado` varchar(255)
);

CREATE TABLE `Participante` (
  `nome` varchar(255) PRIMARY KEY
);

CREATE TABLE `Competicao` (
  `id` varchar(255) PRIMARY KEY,
  `nome` varchar(255)
);

CREATE TABLE `Carteira` (
  `user` varchar(255) PRIMARY KEY,
  `moeda` varchar(255),
  `valor` double
);

CREATE TABLE `moeda` (
  `nome` varchar(255) PRIMARY KEY,
  `exchangeComEuro` double
);

ALTER TABLE `Aposta` ADD FOREIGN KEY (`idUtilizador`) REFERENCES `Utilizador` (`email`);

ALTER TABLE `ApostaJogo` ADD FOREIGN KEY (`idAposta`) REFERENCES `Aposta` (`id`);

ALTER TABLE `ApostaJogo` ADD FOREIGN KEY (`idJogo`) REFERENCES `Jogo` (`id`);

ALTER TABLE `Jogo` ADD FOREIGN KEY (`Competicao`) REFERENCES `Competicao` (`id`);

ALTER TABLE `Jogo` ADD FOREIGN KEY (`Participante1`) REFERENCES `Participante` (`nome`);

ALTER TABLE `Jogo` ADD FOREIGN KEY (`Participante2`) REFERENCES `Participante` (`nome`);

ALTER TABLE `Carteira` ADD FOREIGN KEY (`user`) REFERENCES `Utilizador` (`email`);

ALTER TABLE `Carteira` ADD FOREIGN KEY (`moeda`) REFERENCES `moeda` (`nome`);
