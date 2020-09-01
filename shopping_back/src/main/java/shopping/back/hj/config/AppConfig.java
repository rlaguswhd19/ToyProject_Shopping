package shopping.back.hj.config;


import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import shopping.back.hj.accounts.Account;
import shopping.back.hj.accounts.AccountDto;
import shopping.back.hj.accounts.AccountRepository;
import shopping.back.hj.accounts.AccountService;
import shopping.back.hj.common.AppProperties;
import shopping.back.hj.enums.AccountRole;

@Configuration
public class AppConfig {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AppProperties appProperties;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			
			@Override
			public void run(ApplicationArguments args) throws Exception {
				AccountDto Admin = AccountDto.builder()
						.email(appProperties.getAdminEmail())
						.password(appProperties.getAdminPassword())
						.address("admin")
						.phone_number("010-4732-1566")
						.birth("1994/08/23")
						.build();
				
				Account account = (Account) accountService.createAccount(Admin).getBody();
//				account.getRoles().add(AccountRole.ADMIN);
				account.setRoles(Set.of(AccountRole.USER, AccountRole.ADMIN));
				System.out.println(account.getRoles());
				
				accountRepository.save(account);
				
				AccountDto User = AccountDto.builder()
						.email(appProperties.getUserEmail())
						.password(appProperties.getUserPassword())
						.address("user")
						.phone_number("010-4732-1566")
						.birth("1994/08/23")
						.build();
				
				accountService.createAccount(User);
			}
		};
	}
}
