package br.com.fiap.QMove_MVC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.QMove_MVC.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNome(String nome);
}