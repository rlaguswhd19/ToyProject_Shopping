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

import shopping.back.hj.accounts.Account;
import shopping.back.hj.accounts.AccountDto;
import shopping.back.hj.accounts.AccountService;
import shopping.back.hj.common.AppProperties;
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
	private AccountService accountService;
	
	@Autowired
	private AppProperties appProperties;
	
	@Test
	@TestDescription("인증 토큰을 발급 받는 테스트")
	public void getAuthToken() throws Exception {
		
		AccountDto accountDto = AccountDto.builder()
				.email(appProperties.getUserEmail())
				.password(appProperties.getUserPassword())
				.address("random")
				.phone_number("010-4732-1566")
				.birth("1994/08/23")
				.build();
		
		Account account = (Account)accountService.createAccount(accountDto).getBody();
		
		
		mockMvc.perform(post("/oauth/token")
				.with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
				.param("username", appProperties.getUserEmail())
				.param("password", appProperties.getUserPassword())
				.param("grant_type", "password")
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("access_token").exists())
			;
	}
}
