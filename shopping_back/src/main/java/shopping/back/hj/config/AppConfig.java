package shopping.back.hj.config;

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
import shopping.back.hj.accounts.AccountService;

@Configuration
public class AppConfig {
	
	@Autowired
	private AccountService accountService;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
//	@Bean
//	public ApplicationRunner applicationRunner() {
//		return new ApplicationRunner() {
//			
//			@Override
//			public void run(ApplicationArguments args) throws Exception {
//				AccountDto accountDto = AccountDto.builder()
//						.email("test@naver.com")
//						.password("test")
//						.address("test")
//						.phone_number("010-4732-1566")
//						.birth("1994/08/23")
//						.build();
//				
//				accountService.createAccount(accountDto);
//			}
//		};
//	}
}
