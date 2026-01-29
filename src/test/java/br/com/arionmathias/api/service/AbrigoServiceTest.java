package br.com.arionmathias.api.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.arionmathias.api.model.Abrigo;
import br.com.arionmathias.api.repository.AbrigoRepository;
import br.com.arionmathias.api.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
public class AbrigoServiceTest {
	
	@InjectMocks
	private AbrigoService abrigoService;
	
	@Mock
	private AbrigoRepository abrigoRepository;
	@Mock
	private Abrigo abrigo;
	@Mock
	private PetRepository petRepository;
	

	@Test
	public void shouldCallFindAll() {
		//arrange ( not applicable in this case)
		//act
		this.abrigoService.listar();
		//assert
		//how to assert that one method has been called? BDDMockito
		BDDMockito.then( abrigoRepository ).should().findAll();
	}
	
	@Test
	public void shouldCallFindByAbrigo() {
		//arrange
		String name = "name";
		BDDMockito.given( abrigoRepository.findByNome(name) ).willReturn( Optional.of(abrigo) );
		
		//act
		abrigoService.listarPetsDoAbrigo( name );
		
		//assert
		BDDMockito.then( petRepository ).should().findByAbrigo(abrigo);
		
	}

}
