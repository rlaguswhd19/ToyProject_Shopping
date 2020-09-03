package shopping.back.hj.accounts;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class AccountValidator {
	@Autowired
	private AccountRepository accountRepository;
	
	public void validator(AccountDto accountDto, Errors errors) {
//		TODO 
		
//		email 검증하기
		System.out.println(accountDto.getEmail()+" : "+isValidEmailAddress(accountDto.getEmail()));
		
		if(!isValidEmailAddress(accountDto.getEmail())) {
			errors.rejectValue("email", accountDto.getEmail()+"의 형식이 잘못되었습니다.");
			errors.reject("Wrongemail", "wrong email form");
		}
//		email 중복 확인
		Optional<Account> optionalAccount = accountRepository.findByEmail(accountDto.getEmail());
		
		if(!optionalAccount.isEmpty()) {
			errors.rejectValue("email", accountDto.getEmail()+"는 이미 존재합니다.");
			errors.reject("Wrongemail", "email overlap");
		}
	}
	
	// 아파치꺼
	public boolean commonEmailValidator(String email) {
		return EmailValidator.getInstance().isValid(email);
	}
	
	// 정규식 사용 잘모르겠다~
	public boolean isValidEmailAddress(String email) {
		String ePattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(email);
		return m.matches();
 }
}
