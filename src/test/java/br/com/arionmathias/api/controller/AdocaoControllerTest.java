package br.com.arionmathias.api.controller;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.arionmathias.api.dto.SolicitacaoAdocaoDto;
import br.com.arionmathias.api.service.AdocaoService;


//Will load the spring boot context and, because this, it will be a integration test not unit test!
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)//need to specify port to use restTemplate! 
@AutoConfigureMockMvc
@AutoConfigureJsonTesters //we need to use jackson tester
public class AdocaoControllerTest {
	
	@Autowired
	private MockMvc springMockMvc;

    @Autowired
    private TestRestTemplate restTemplate; // we can use restTemplate insted MockMvc to perform real http requests 
	
	@Autowired
	private JacksonTester<Object> jacksonTester;
	
	/**
	 * Notice that we're using MockBean from springframework, not the Mock from mockito 
	 */
	@MockBean 
	private AdocaoService adocaoService;
	
	@Test
	public void shouldReturnBAD_REQUEST() throws Exception {
		//ARRANGE
		String invalidJson = "{}";
		
		//ACT
		//how to send a request in spring?
		
		//its a controller, so we can trigger a request to a API (api must be up) or forge a request with mockMvc
		
		MvcResult requestReturn = springMockMvc.perform(
				MockMvcRequestBuilders.post("/adocoes").content(invalidJson).contentType(MediaType.APPLICATION_JSON)
		).andReturn();
		
		
		//ASSERT
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), requestReturn.getResponse().getStatus());
	}
	@Test
	public void shouldReturnOK() throws Exception {
		//ARRANGE
		String json = getValidJson();
		
		//ACT
		MvcResult requestReturn = springMockMvc.perform(
				MockMvcRequestBuilders.post("/adocoes").content(json).contentType(MediaType.APPLICATION_JSON)
				).andReturn();
		
		
		//ASSERT
		Assertions.assertEquals(HttpStatus.OK.value(), requestReturn.getResponse().getStatus());
	}
	@Test
	public void shouldReturnOKRealHttpRequest() throws Exception {
		//ARRANGE
		String json = getValidJson();
		
		//ACT
//		var requestReturn = this.restTemplate.postForObject("/adocoes", json, ResponseEntity.class); //[org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'application/x-www-form-urlencoded;charset=UTF-8' is not supported]
//		var requestReturn = this.restTemplate.exchange("/adocoes", HttpMethod.POST,new HttpEntity<>(json),Void.class);//[org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'application/x-www-form-urlencoded;charset=UTF-8' is not supported]

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(json, httpHeaders );
		var requestReturn = this.restTemplate.postForEntity("/adocoes", httpEntity, ResponseEntity.class);
		
		
		//ASSERT
		Assertions.assertEquals(HttpStatus.OK.value(), requestReturn.getStatusCode().value());
	}
	private String getValidJson() throws IOException {
//		var return_ = """
//	            {
//	                "idPet": 1,
//	                "idTutor": 1,
//	                "motivo": "Motivo qualquer"
//	            }
//	            """;
//		
		//we can also use JacksonTester
		SolicitacaoAdocaoDto solicitacaoAdocaoDto = new SolicitacaoAdocaoDto(1l, 1l, "motivo qualquer");
		
		return this.jacksonTester.write(solicitacaoAdocaoDto).getJson();
	}

}
