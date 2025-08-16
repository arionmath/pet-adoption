package br.com.arionmathias.api.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arionmathias.api.dto.SolicitacaoAdocaoDto;
import br.com.arionmathias.api.exception.ValidacaoException;
import br.com.arionmathias.api.model.Pet;
import br.com.arionmathias.api.repository.PetRepository;

@Component
public class ValidacaoPetDisponivel implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private PetRepository petRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());
        if (pet.getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }

}
