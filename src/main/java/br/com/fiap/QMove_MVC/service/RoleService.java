package br.com.fiap.QMove_MVC.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.QMove_MVC.model.Role;
import br.com.fiap.QMove_MVC.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> listarTodas() {
        return roleRepository.findAll();
    }

    public Role buscarPorNome(String nome) {
        return roleRepository.findByNome(nome);
    }

    public Role salvar(Role role) {
        return roleRepository.save(role);
    }
}
