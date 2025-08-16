package br.com.arionmathias.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.arionmathias.api.dto.SolicitacaoAdocaoDto;
import br.com.arionmathias.api.model.Abrigo;
import br.com.arionmathias.api.model.Adocao;
import br.com.arionmathias.api.model.Pet;
import br.com.arionmathias.api.model.Tutor;
import br.com.arionmathias.api.repository.AdocaoRepository;
import br.com.arionmathias.api.repository.PetRepository;
import br.com.arionmathias.api.repository.TutorRepository;
import br.com.arionmathias.api.validacoes.ValidacaoSolicitacaoAdocao;

@ExtendWith(MockitoExtension.class)
public class AdocaoServiceTest {
	
	@InjectMocks
	private AdocaoService adocaoService;
	
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private EmailService emailService;
    
//    @Mock //we want to use something that has more approach on the simulated object
    //so, lets use the @Spy to use a real object, but we can controll it overridind some routine
    @Spy
    private List<ValidacaoSolicitacaoAdocao> validatorList = new ArrayList<>(); //and we want to use java list rather than any list that junit can create
    
    //Now we need to create validacoes itens now we uses mock
    @Mock
    private ValidacaoSolicitacaoAdocao validator1;
    @Mock
    private ValidacaoSolicitacaoAdocao validator2;
    

	private SolicitacaoAdocaoDto dto;
	
	@Captor
	private ArgumentCaptor<Adocao> argumentCaptor; 
	

	@Mock 
	private Pet pet;	
	@Mock
	private Tutor tutor;
	@Mock
	private Abrigo abrigo;
	
    @Test
	void shouldSaveAdocaoOnRequest() {
    	
		// ARRANGE
    	// Inside the tested method exists a mock call that we need to tell what to return 
    	// when mock's method was called
    	// or it will return null
    	// this prevents a npe too
    	this.dto = new SolicitacaoAdocaoDto((long) 123, (long) 123, "Junit is nice");
    	BDDMockito.given( petRepository.getReferenceById( dto.idPet() )).willReturn( pet );
    	BDDMockito.given( tutorRepository.getReferenceById( dto.idTutor() )).willReturn( tutor );
    	BDDMockito.given( pet.getAbrigo() ).willReturn( abrigo );
    	
		//ACT
		this.adocaoService.solicitar( this.dto );
	
		//ASSURE
		//Verify if petRepository.save has been called with any parameter as argument
		BDDMockito.then( adocaoRepository ).should().save( BDDMockito.any() );
		

		 // If we need to inspect an object that is created inside the method (and not returned),
	    // we can use ArgumentCaptor to capture it.		
		BDDMockito.then( adocaoRepository ).should().save( argumentCaptor.capture() );
		Adocao valuePassedToSave = argumentCaptor.getValue();
		
		// Here, we asserting if the object passed to be saved (inside a method that we are testing)
		// has the same atributes that we are mocking
		// because inside adocaoService.solicitar exists a routine that creates one Adocao with the return of petRepository.getReferenceById and  tutorRepository.getReferenceById
		assertEquals( pet, valuePassedToSave.getPet()  );
		assertEquals( tutor, valuePassedToSave.getTutor()  );
		assertEquals( dto.motivo(), valuePassedToSave.getMotivo()  );
		
	}
    
    
    /**
     * And also there is another call into adocaoService.solicitar( this.dto )
     * To validators and to email sender
     * Lets test then
     */

    @Test
    void shouldCallValidatorsAdocaoOnRequest() {
    	
    	// ARRANGE
    	// Inside the tested method exists a mock call that we need to tell what to return 
    	// when mock's method was called
    	// or it will return null
    	// this prevents a npe too
    	this.dto = new SolicitacaoAdocaoDto((long) 123, (long) 123, "Junit is nice");
    	BDDMockito.given( petRepository.getReferenceById( dto.idPet() )).willReturn( pet );
    	BDDMockito.given( tutorRepository.getReferenceById( dto.idTutor() )).willReturn( tutor );
    	BDDMockito.given( pet.getAbrigo() ).willReturn( abrigo );
    	
    	this.validatorList.add(validator1);
    	this.validatorList.add(validator2);
    	
    	//ACT
    	this.adocaoService.solicitar( this.dto );
    	
    	//ASSURE assert
    	
    	//Verify if the validators list has been iterate and each value has it own function called
    	BDDMockito.then( validator1 ).should().validar( dto );
    	BDDMockito.then( validator2 ).should().validar( dto );
    	// Will work because validatorList has the @Spy anottation
    	// then we can track this method invocation
    }
    
    
}
