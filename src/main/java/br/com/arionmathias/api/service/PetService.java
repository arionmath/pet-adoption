package br.com.arionmathias.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arionmathias.api.dto.CadastroPetDto;
import br.com.arionmathias.api.dto.PetDto;
import br.com.arionmathias.api.model.Abrigo;
import br.com.arionmathias.api.model.Pet;
import br.com.arionmathias.api.repository.PetRepository;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<PetDto> buscarPetsDisponiveis() {
        return repository
                .findAllByAdotadoFalse()
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public void cadastrarPet(Abrigo abrigo, CadastroPetDto dto) {
        repository.save(new Pet(dto, abrigo));
    }
}
