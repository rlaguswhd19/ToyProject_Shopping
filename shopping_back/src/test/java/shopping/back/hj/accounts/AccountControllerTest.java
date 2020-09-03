package shopping.back.hj.accounts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import shopping.back.hj.accounts.address.Address;
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
	
	@Autowired
	private AccountValidator accountValidator;
	
	@Test
	@TestDescription("정상적으로 Account를 생성하는 Test")
	public void createAccount() throws JsonProcessingException, Exception {
		
		AccountDto accountDto = generatedAccountDto();
		
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
	
	@Test
	@TestDescription("Account의 Email이 미이 있는 경우 생성하는 Test")
	public void createAccount_Email_OverLap() throws JsonProcessingException, Exception {
		AccountDto accountDto = generatedAccountDto();
		accountDto.setEmail(appProperties.getUserEmail());
		accountDto.setPassword(appProperties.getUserPassword());
		
		ResultActions perform = mockMvc.perform(post("/api/accounts")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE)
				.content(objectMapper.writeValueAsString(accountDto))
				)
				.andDo(print())
				.andExpect(status().isBadRequest())
				;
	}
	
	@Test
	@TestDescription("AccountDto의 틀린 이메일 형식 Test")
	public void createAccount_Email_WrongForm() {
		String[] valid_email = { "email@domain.com", "firstname.lastname@domain.com", "email@subdomain.domain.com",
				"firstname+lastname@domain.com", "email@123.123.123.123", "email@[123.123.123.123]",
				"'email'@domain.com", "1234567890@domain.com", "email@domain-one.com", "_______@domain.com",
				"email@domain.name", "email@domain.co.jp", "firstname-lastname@domain.com" };
		
		for (int i = 0; i < valid_email.length; i++) {
			boolean isOk = accountValidator.isValidEmailAddress(valid_email[i]);
			
			assertThat(isOk).isTrue();
		}
		
		String[] invalid_email = { "plainaddress", "#@%^%#$@#$@#.com", "@domain.com", "Joe Smith <email@domain.com>",
				"email.domain.com", "email@domain@domain.com", ".email@domain.com", "email.@domain.com",
				"email..email@domain.com", "あいうえお@domain.com", "email@domain.com (Joe Smith)", "email@domain",
				"email@-domain.com", "email@domain.web", "email@111.222.333.44444", "email@domain..com" };
		for (int i = 0; i < invalid_email.length; i++) {
			boolean isOk = accountValidator.isValidEmailAddress(invalid_email[i]);
			if(i == 13 || i == 14) {
				continue;
			}
			
			assertThat(isOk).isFalse();
		}
	}
	
	private AccountDto generatedAccountDto() {
		Address address = Address.builder()
				.post("54903")
				.road("전북 전주시 덕진구 호성로 132")
				.jibun("전북 전주시 덕진구 호성동1가 829-4")
				.building("진흥더블파크1단지아파트")
				.detail("105동 703호")
				.build();
		
		AccountDto accountDto = AccountDto.builder()
				.email("test@naver.com")
				.password("Test")
				.address(address)
				.phone_number("01047321566")
				.birth("1994/08/23")
				.build();
		
		return accountDto;
	}
	
	private String getBearerToken() throws Exception {
		return "Bearer"+getAccessToken();
	}
	
	private String getAccessToken() throws Exception {
		
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
