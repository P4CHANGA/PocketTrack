ALTER TABLE cuenta
    ADD CONSTRAINT uc_cuenta_nombre UNIQUE (nombre);