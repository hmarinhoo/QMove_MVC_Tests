-- Adiciona coluna qrcode à tabela moto e popula valores existentes

ALTER TABLE moto ADD COLUMN qrcode varchar(50);

-- Para registros já existentes, gera um qrcode legível baseado no id (Moto01, Moto02...)
UPDATE moto SET qrcode = 'Moto' || LPAD(id::text, 2, '0') WHERE qrcode IS NULL;

-- Marca a coluna como not null e cria índice único
ALTER TABLE moto ALTER COLUMN qrcode SET NOT NULL;
CREATE UNIQUE INDEX ux_moto_qrcode ON moto(qrcode);
