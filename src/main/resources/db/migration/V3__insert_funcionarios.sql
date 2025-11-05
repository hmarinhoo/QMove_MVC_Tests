-- Usuário administrador inicial
-- Senha: admin123 (vai ser criptografada na aplicação com BCrypt na inicialização)

INSERT INTO funcionario (nome, email, senha, ativo)
VALUES ('Administrador', 'admin@mottu.com', '$2a$12$n4tzLB0CygZ16Q2.I5Hs1eZEn9DDt5Q4O7tFSCugJ5fCJQQSssx3y', TRUE);

-- Atribui o papel ADMIN ao usuário administrador
INSERT INTO funcionario_roles (funcionario_id, role_id)
SELECT f.id, r.id FROM funcionario f, role r
WHERE f.email = 'admin@mottu.com' AND r.nome = 'ROLE_ADMIN';

-- Usuário funcionário
-- Senha: func123 (armazenada como BCrypt)
INSERT INTO funcionario (nome, email, senha, ativo)
VALUES ('Funcionário', 'funcionario@mottu.com', '$2a$12$5EavvOW7anUEZICXzMipV.vMuacJXXDc71.PUSCX7gGVu/Xh.a/7O', TRUE);

-- Atribui o papel USER ao usuário funcionário
INSERT INTO funcionario_roles (funcionario_id, role_id)
SELECT f.id, r.id FROM funcionario f, role r
WHERE f.email = 'funcionario@mottu.com' AND r.nome = 'ROLE_USER';
