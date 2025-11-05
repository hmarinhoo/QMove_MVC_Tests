-- Dados iniciais de exemplo para teste

INSERT INTO setor (nome, codigo) VALUES
('Disponível', 'Verde'),
('Manutenção', 'Amarelo'),
('Pendências', 'Azul');

INSERT INTO moto (placa, modelo, status, setor_id) VALUES
('XYZ1234', 'Mottu Pop', 'Disponível', 1),
('ABC5678', 'Mottu-E', 'Manutenção', 2),
('DEF9012', 'Mottu Sport', 'Pendências', 3),
('GHI3456', 'Mottu Sport', 'Disponível', 1),
('JKL7890', 'Mottu Pop', 'Disponível', 1),
('MNO2345', 'Mottu Pop', 'Manutenção', 2);

