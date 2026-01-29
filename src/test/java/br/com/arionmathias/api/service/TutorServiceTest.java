package br.com.arionmathias.api.service;

import static org.mockito.Mockito.never;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.arionmathias.api.dto.CadastroTutorDto;
import br.com.arionmathias.api.exception.ValidacaoException;
import br.com.arionmathias.api.repository.TutorRepository;

@ExtendWith(MockitoExtension.class)
public class TutorServiceTest {

	@InjectMocks
	private TutorService tutorService;

	@Mock
	private CadastroTutorDto cadastroTutorDto;
	@Mock
	private TutorRepository tutorRepository;

	@Test
	public void shouldThrowsException_whenExistsPhoneOrEmail() {
		// arrange
		BDDMockito.given(tutorRepository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email()))
				.willReturn(true);

		// assert + act
		Assertions.assertThrows(ValidacaoException.class, () -> tutorService.cadastrar(cadastroTutorDto));
		BDDMockito.then(tutorRepository).should(never()).save(BDDMockito.any());
	}

	
	
	@Test
	public void shouldCallSave() {
		// arrange
		BDDMockito
			.given(tutorRepository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email()))
			.willReturn(false);
		
		tutorService.cadastrar(cadastroTutorDto);
		
		//verify if method being called?
		BDDMockito.then( tutorRepository ).should().save( BDDMockito.any() );
	}

}
