package br.com.arionmathias.api.validacoes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.arionmathias.api.dto.SolicitacaoAdocaoDto;
import br.com.arionmathias.api.exception.ValidacaoException;
import br.com.arionmathias.api.model.Pet;
import br.com.arionmathias.api.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
public class ValidacaoPetDisponivelTest {
	
	@InjectMocks
	private ValidacaoPetDisponivel validacaoPetDisponivel;
	
	@Mock
    private PetRepository petRepository;//stuntman
	
	@Mock
	private Pet pet;//stuntman
	@Mock
	private SolicitacaoAdocaoDto adocaoDto;//stuntman
	
	@Test
	public void shouldAllowPetRequest(){
		BDDMockito.given( petRepository.getReferenceById( pet.getId() ) ).willReturn( pet );
		BDDMockito.given( pet.getAdotado() ).willReturn( false );
		Assertions.assertDoesNotThrow( ()-> validacaoPetDisponivel.validar(adocaoDto) );
	}
	
	@Test
	public void shouldNotAllowPetRequest(){  	
		BDDMockito.given( petRepository.getReferenceById( pet.getId() )).willReturn( pet );
		BDDMockito.given( pet.getAdotado() ).willReturn( true );
		Assertions.assertThrows( ValidacaoException.class, ()-> validacaoPetDisponivel.validar( adocaoDto ) );
	}

}
