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

import shopping.back.hj.common.AppProperties;
import shopping.back.hj.enums.AccountRole;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AppProperties appProperties;
	
	@Test
	public void findByUsername() {

		// When
		UserDetails userDetails = accountService.loadUserByUsername(appProperties.getUserEmail());
		
		// Then
		assertThat(passwordEncoder.matches(appProperties.getUserPassword(), userDetails.getPassword())).isTrue();
	}
	
	@Test
	public void findByUsername_NotFound() {
		
		try {
			accountService.loadUserByUsername("test");
			fail("findByUsername_NotFound Test Fail");
		}catch (Exception e) {
			assertThat(e instanceof UsernameNotFoundException).isTrue();
			assertThat(e.getMessage()).contains("test");
		}
		
	}
}
