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
import shopping.back.hj.accounts.address.Address;
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
//				Address address1 = Address.builder()
//						.post("Test")
//						.road("Test")
//						.jibun("Test")
//						.detail("Test")
//						.extra("Test")
//						.build();
//				
//				AccountDto Admin = AccountDto.builder()
//						.email(appProperties.getAdminEmail())
//						.password(appProperties.getAdminPassword())
//						.address(address1)
//						.phone_number("010-4732-1566")
//						.birth("1994/08/23")
//						.build();
//				
//				Account account = (Account) accountService.createAccount(Admin).getBody();
//				account.setRoles(Set.of(AccountRole.USER, AccountRole.ADMIN));
//				
//				accountRepository.save(account);
//				
//				Address address2 = Address.builder()
//						.post("54903")
//						.road("전북 전주시 덕진구 호성로 132")
//						.jibun("전북 전주시 덕진구 호성동1가 829-4")
//						.detail("진흥더블파크1단지아파트")
//						.extra("105동 703호")
//						.build();
//				
//				AccountDto User = AccountDto.builder()
//						.email(appProperties.getUserEmail())
//						.password(appProperties.getUserPassword())
//						.address(address2)
//						.phone_number("010-4732-1566")
//						.birth("1994/08/23")
//						.build();
//				
//				accountService.createAccount(User);
			}
		};
	}
}
