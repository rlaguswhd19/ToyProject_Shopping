package shopping.back.hj.accounts;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shopping.back.hj.common.AppProperties;
import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class AccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AppProperties appProperties;
	
	@Before
	public void setUp() {
		accountRepository.deleteAll();
	}
	
	@Test
	@TestDescription("createAccount")
	public void createAccount() throws JsonProcessingException, Exception {
		AccountDto accountDto = AccountDto.builder().build();
		
		ResultActions perform = mockMvc.perform(post("/api/accounts")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE)
				.content(objectMapper.writeValueAsString(accountDto))
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").exists())
				;
	}
	
	private String getBearerToken() throws Exception {
		return "Bearer"+getAccessToken();
	}
	
	private String getAccessToken() throws Exception {
		
		AccountDto accountDto = AccountDto.builder()
				.email(appProperties.getUserEmail())
				.password(appProperties.getUserPassword())
				.address("random")
				.phone_number("010-4732-1566")
				.birth("1994/08/23")
				.build();
		
		Account account = (Account)accountService.createAccount(accountDto).getBody();
		
		ResultActions perform = mockMvc.perform(post("/oauth/token")
				.with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
				.param("username", appProperties.getUserEmail())
				.param("password", appProperties.getUserPassword())
				.param("grant_type", "password")
				);
		
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();
		return parser.parseMap(responseBody).get("access_token").toString();
	}
	
}
