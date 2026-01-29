package br.com.arionmathias.api.controller;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

@SpringBootTest //up the spring context
@AutoConfigureMockMvc //configure the mock mvc
public class AbrigoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private AbrigoController abrigoController;
	
	private final String urlBase = "/abrigos";
	
	@Test
	public void shouldReturn200_listShelter() throws Exception {
//		var res = this.mockMvc.perform( get(url) ).andReturn();
//		assertEquals( HttpStatus.OK.value(), res.getResponse().getStatus() );
		
		this.mockMvc.perform( MockMvcRequestBuilders.get(urlBase) )
			.andExpect(  MockMvcResultMatchers.status().isOk()  )
			.andDo( new Print() );
	}
	
	@Test
	public void shouldNotInsertShelterDueInvalidJson() throws Exception {
		String json = "{}";
				
		this.mockMvc.perform( MockMvcRequestBuilders.post(urlBase).content(json).contentType( MediaType.APPLICATION_JSON ) )
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andDo( new Print() );
	}
	@Test
	public void shouldInsertShelter() throws Exception {
		String json = """
				{
					"nome":"Bob",
					"telefone":"8599999-9999",
					"email": "email@notExists.com"
				}
				""";
		
		this.mockMvc.perform( MockMvcRequestBuilders.post(urlBase).content(json).contentType( MediaType.APPLICATION_JSON ) )
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo( new Print() );
	}

	public static class Print implements ResultHandler{

		@Override
		public void handle(MvcResult result) throws Exception {
			String msg = "";
			String requestUrl = result.getRequest().getRequestURL().toString();
			String contentStr = result.getResponse().getContentAsString();
			int statusCode = result.getResponse().getStatus();
			if (statusCode < 300) {
				msg = String.format("Response from request to %s is \n %s", requestUrl, contentStr); 				
			}else {
				msg = String.format("Request to %s has FAILED with the status %d and the body %s",
						requestUrl, statusCode, contentStr); 							
			}
			System.out.println(msg);
		}
		
	}

}
