package shopping.back.hj.config;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import shopping.back.hj.accounts.Account;
import shopping.back.hj.accounts.AccountDto;
import shopping.back.hj.accounts.AccountService;
import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class AuthServerConfigTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private AccountService accountService;
	
	@Test
	@TestDescription("인증 토큰을 발급 받는 테스트")
	public void getAuthToken() throws Exception {
		String useremail = "test@naver.com";
		String password = "test";
		
		AccountDto accountDto = AccountDto.builder()
				.email(useremail)
				.password(password)
				.address("test")
				.phone_number("010-4732-1566")
				.birth("1994/08/23")
				.build();
		
		Account account = (Account) accountService.createAccount(accountDto).getBody();
		
		
		String clientId = "hjapp";
		String clientSecret = "hjpass";
		
		mockMvc.perform(post("/oauth/token")
				.with(httpBasic(clientId, clientSecret))
				.param("username", useremail)
				.param("password", password)
				.param("grant_type", "password")
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("access_token").exists())
			;
	}
}
