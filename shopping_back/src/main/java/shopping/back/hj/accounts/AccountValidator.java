package shopping.back.hj.accounts;

import java.time.LocalDate;
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

	// 중복 email 검사
	public void isExistEmail(AccountDto accountDto, Errors errors) {
		Optional<Account> optionalAccount = accountRepository.findByEmail(accountDto.getEmail());

		if (!optionalAccount.isEmpty()) {
			errors.rejectValue("email", accountDto.getEmail() + "는 이미 존재합니다.");
			errors.reject("Wrongemail", "email overlap");
		}
	}

	public void isValidPass(AccountDto accountDto, Errors errors) {
		
		// TODO Password 검증
		String pwPattern = "^(?=.*[0-9])(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{8,16}$";
		Pattern p = Pattern.compile(pwPattern);
		Matcher m = p.matcher(accountDto.getPassword());
		
		if (!m.matches()) {
			errors.rejectValue("password", "비밀번호의 형식이 잘못되었습니다.");
			errors.reject("WrongPassword", "wrong password form");
		}
	}

	public void validator(AccountDto accountDto, Errors errors) {

		// TODO email 검증하기
		if (!isValidEmailAddress(accountDto.getEmail())) {
			errors.rejectValue("email", accountDto.getEmail() + "의 형식이 잘못되었습니다.");
			errors.reject("Wrongemail", "wrong email form");
		}

		// TODO birth 검증
		if (!isValidBirth(accountDto.getBirth())) {
			errors.rejectValue("birth", "날짜가 잘못되었습니다.");
			errors.reject("WrongBirth", "wrong birth");
		}

		// TODO phone 검증
		if (!isValidPhone(accountDto.getPhone_number())) {
			errors.rejectValue("phone_number", "전화번호가 잘못되었습니다.");
			errors.reject("WrongPhone_Number", "wrong phone_number");
		}
	}

	public boolean isValidPhone(String phone) {
		String pnPattern = "^01(?:0|1|[6-9])(\\d{8})$";
		Pattern p = Pattern.compile(pnPattern);
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	public boolean isValidBirth(String birth) {
		String[] temp = birth.split("/");
		Integer[] date = { Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]) };
		LocalDate current = LocalDate.now();

		if (current.getYear() < date[0] || current.getYear() - 100 > date[0]) {
			return false;
		}

		if (0 >= date[1] || 12 < date[1]) {
			return false;
		}

		LocalDate check = LocalDate.of(date[0], date[1], 1);
		if (date[2] > check.lengthOfMonth() || date[2] < 1) {
			return false;
		}

		LocalDate input_date = LocalDate.of(date[0], date[1], date[2]);

		if (input_date.isBefore(current.minusYears(100)) || input_date.isAfter(current)) {
			return false;
		}

		return true;
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
	
	public boolean isValidPassword(String password) {
		String pwPattern = "^(?=.*[0-9])(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{8,16}$";
		Pattern p = Pattern.compile(pwPattern);
		Matcher m = p.matcher(password);
		return m.matches();
	}
}
