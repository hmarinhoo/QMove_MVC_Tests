package br.com.fiap.QMove_MVC.repository;

import br.com.fiap.QMove_MVC.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
    // Exemplo de consulta customizada (opcional)
    Setor findByNome(String nome);
}