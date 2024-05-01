CREATE TABLE metaDeGastos
(
  id bigserial NOT NULL PRIMARY KEY ,
  metaGastos numeric(16,2),
  id_usuario bigint REFERENCES usuario (id)
);
