package shopping.back.hj.accounts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AccountTest {
	
	@Test
	public void builder() {
		Account account = Account.builder().build();
		assertThat(account).isNotNull();
	}
	
}
