package br.com.arionmathias.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arionmathias.api.model.Adocao;
import br.com.arionmathias.api.model.StatusAdocao;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    boolean existsByPetIdAndStatus(Long idPet, StatusAdocao status);

}
