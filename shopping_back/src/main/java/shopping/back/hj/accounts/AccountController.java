package shopping.back.hj.accounts;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import shopping.back.hj.accounts.changepass.ChangePass;
import shopping.back.hj.common.ErrorsModel;

@Controller
@RequestMapping(value = "/api/accounts", produces = MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountValidator accountValidator;

	@Autowired
	private AccountRepository accountRepository;

	@PostMapping
	public ResponseEntity<?> createAccount(@RequestBody @Valid AccountDto accountDto, Errors errors) {
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		accountValidator.validator(accountDto, errors);

		// email 중복 확인
		accountValidator.isExistEmail(accountDto.getEmail(), errors);

		// password 정규식 확인
		accountValidator.isValidPass(accountDto.getPassword(), errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		return accountService.createAccount(accountDto);
	}

	@PutMapping
	public ResponseEntity<?> updateAccount(@RequestBody @Valid AccountDto accountDto, Errors errors) {
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		accountValidator.validator(accountDto, errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		return accountService.updateAccount(accountDto);
	}

	@PutMapping("/password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePass changePass, Errors errors) {
		
		accountValidator.isNotExistEmail(changePass.getEmail(), errors);
		accountValidator.isValidPass(changePass.getNewPassword(), errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		Account account = accountRepository.findByEmail(changePass.getEmail()).get();
		accountValidator.passwordCheck(changePass.getPassword(), account, errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		return accountService.changePassword(changePass.getNewPassword(), account);
	}

	@GetMapping("/{email}")
	public ResponseEntity<?> findByEmail(@PathVariable String email) {
		return accountService.findByEmail(email);
	}

	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsModel(errors));
	}
}
