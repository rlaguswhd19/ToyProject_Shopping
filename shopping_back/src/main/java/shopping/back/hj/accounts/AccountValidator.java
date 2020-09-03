package shopping.back.hj.accounts;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class AccountValidator {
	@Autowired
	private AccountRepository accountRepository;
	
	public void validator(AccountDto accountDto, Errors errors) {
//		TODO 
//		email 중복 확인
		Optional<Account> optionalAccount = accountRepository.findByEmail(accountDto.getEmail());
		
		if(!optionalAccount.isEmpty()) {
			errors.rejectValue("email", accountDto.getEmail()+"는 이미 존재합니다.");
			errors.reject("Wrongemail", "email is Wrong");
		}
	}
}
