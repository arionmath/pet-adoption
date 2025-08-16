package br.com.arionmathias.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arionmathias.api.model.Abrigo;
import br.com.arionmathias.api.model.Pet;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByAdotadoFalse();

    List<Pet> findByAbrigo(Abrigo abrigo);
}
