package br.com.arionmathias.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.arionmathias.api.dto.CadastroAbrigoDto;
import br.com.arionmathias.api.dto.CadastroPetDto;
import br.com.arionmathias.api.model.Abrigo;
import br.com.arionmathias.api.model.Pet;
import br.com.arionmathias.api.model.ProbabilidadeAdocao;
import br.com.arionmathias.api.model.TipoPet;

public class CalculadoraProbabilidadeAdocaoTest {

	@Test
	@DisplayName(value = "This code will test an method")
	public void shouldReturnHighProbWhenLowAgeAndLowWeight() {
		//arrange
		Abrigo shelter = new Abrigo(new CadastroAbrigoDto("Peace Shelter", "854 987 987", "email@exameple.com"));

		Pet pet = new Pet(new CadastroPetDto(TipoPet.CACHORRO, "Teddy", "Doberman", 8, "black", 15f), shelter);

		//act
		ProbabilidadeAdocao result = new CalculadoraProbabilidadeAdocao().calcular(pet);
		
		//assert
		assertEquals(ProbabilidadeAdocao.ALTA, result);
	}
	@Test
	@DisplayName(value = "This code will test another method")
	public void shouldReturnMediumProbWhenLowAgeAndLowWeight() {
		Abrigo shelter = new Abrigo(new CadastroAbrigoDto("Peace Shelter", "854 987 987", "email@exameple.com"));
		
		Pet pet = new Pet(new CadastroPetDto(TipoPet.GATO, "Fully", "Persa", 15, "black", 10f), shelter);
		
		ProbabilidadeAdocao result = new CalculadoraProbabilidadeAdocao().calcular(pet);
		assertEquals(ProbabilidadeAdocao.MEDIA, result);
	}

}
