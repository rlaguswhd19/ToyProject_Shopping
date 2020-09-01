package shopping.back.hj.accounts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import shopping.back.hj.enums.AccountRole;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void findByUsername() {
		// Given
		AccountDto accountDto = AccountDto.builder()
				.email("test@naver.com")
				.password("test")
				.address("test")
				.phone_number("010-4732-1566")
				.birth("1994/08/23")
				.build();
		
		Account account = (Account) accountService.createAccount(accountDto).getBody();

		// When
		UserDetails userDetails = accountService.loadUserByUsername(account.getEmail());
		
		// Then
		assertThat(passwordEncoder.matches(accountDto.getPassword(), userDetails.getPassword())).isTrue();
	}
	
	@Test
	public void findByUsername_NotFound() {
		String Username = "random@naver.com";
		
		try {
			accountService.loadUserByUsername(Username);
			fail("findByUsername_NotFound Test Fail");
		}catch (Exception e) {
			assertThat(e instanceof UsernameNotFoundException).isTrue();
			assertThat(e.getMessage()).contains(Username);
		}
		
	}
}
