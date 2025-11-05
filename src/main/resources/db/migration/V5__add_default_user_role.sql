-- Script para adicionar ROLE_USER a todos os funcionários que não têm nenhuma role
-- Execute isso manualmente no banco de dados se necessário

-- Verifica quais funcionários não têm role
SELECT f.id, f.nome, f.email 
FROM funcionario f
LEFT JOIN funcionario_roles fr ON f.id = fr.funcionario_id
WHERE fr.role_id IS NULL;

-- Adiciona ROLE_USER (id=2) para todos os funcionários sem role
INSERT INTO funcionario_roles (funcionario_id, role_id)
SELECT f.id, 2
FROM funcionario f
LEFT JOIN funcionario_roles fr ON f.id = fr.funcionario_id
WHERE fr.role_id IS NULL;
