package br.com.fiap.QMove_MVC.repository;

import br.com.fiap.QMove_MVC.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
	boolean existsByQrcode(String qrcode);
	java.util.Optional<Moto> findByQrcode(String qrcode);

	// Verifica se existe ao menos uma moto vinculada a um setor (usa setor.id internamente)
	boolean existsBySetorId(Long setorId);

	// Conta quantas motos existem vinculadas a um setor
	long countBySetorId(Long setorId);
}
