package br.com.fiap.QMove_MVC.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.QMove_MVC.model.Setor;
import br.com.fiap.QMove_MVC.repository.SetorRepository;
import br.com.fiap.QMove_MVC.repository.MotoRepository;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final MotoRepository motoRepository;

    public SetorService(SetorRepository setorRepository, MotoRepository motoRepository) {
        this.setorRepository = setorRepository;
        this.motoRepository = motoRepository;
    }

    public List<Setor> listarTodos() {
        return setorRepository.findAll();
    }

    public Optional<Setor> buscarPorId(Long id) {
        return setorRepository.findById(id);
    }

    public Setor salvar(Setor setor) {
        return setorRepository.save(setor);
    }

    public void excluir(Long id) {
        // Verifica se existe alguma moto vinculada a este setor
        if (motoRepository.existsBySetorId(id)) {
            throw new IllegalStateException("Não é possível excluir este setor porque existem motos vinculadas a ele.");
        }
        setorRepository.deleteById(id);
    }

    public Setor buscarPorNome(String nome) {
        return setorRepository.findByNome(nome);
    }

    public long contarMotosPorSetorId(Long setorId) {
        return motoRepository.countBySetorId(setorId);
    }
}