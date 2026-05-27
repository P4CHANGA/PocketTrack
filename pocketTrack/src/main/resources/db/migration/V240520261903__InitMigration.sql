CREATE TABLE cuenta
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    divisa     VARCHAR(255) NOT NULL,
    cantidad DOUBLE NOT NULL,
    nombre     VARCHAR(255) NOT NULL,
    usuario_id BIGINT NULL,
    CONSTRAINT pk_cuenta PRIMARY KEY (id)
);

CREATE TABLE gastos
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    nombre     VARCHAR(255) NOT NULL,
    cantidad DOUBLE NOT NULL,
    tipo_gasto VARCHAR(255) NOT NULL,
    cuenta_id  BIGINT NULL,
    CONSTRAINT pk_gastos PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id     INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    role_id     INT    NOT NULL,
    user_codigo BIGINT NOT NULL
);

CREATE TABLE usuario
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    creation_date datetime     NOT NULL,
    email         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

ALTER TABLE cuenta
    ADD CONSTRAINT FK_CUENTA_ON_USUARIO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE gastos
    ADD CONSTRAINT FK_GASTOS_ON_CUENTA FOREIGN KEY (cuenta_id) REFERENCES cuenta (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_usuario FOREIGN KEY (user_codigo) REFERENCES usuario (id);