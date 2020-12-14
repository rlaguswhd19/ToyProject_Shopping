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

import shopping.back.hj.common.ErrorsModel;

@Controller
@RequestMapping(value = "/api/accounts", produces = MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountValidator accountValidator;

	@PostMapping
	public ResponseEntity<?> createAccount(@RequestBody @Valid AccountDto accountDto, Errors errors) {
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		accountValidator.validator(accountDto, errors);
		
		// email 중복 확인
		accountValidator.isExistEmail(accountDto, errors);
		
		// password 정규식 확인
		accountValidator.isValidPass(accountDto, errors);
		
		if (errors.hasErrors()) {
			return badRequest(errors);
		}
		System.out.println(accountDto);
		return accountService.createAccount(accountDto);
	}

	@PutMapping
	public ResponseEntity<?> updateAccount(@RequestBody @Valid AccountDto accountDto, Errors errors) {
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		accountValidator.validator(accountDto, errors);
		
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		return accountService.updateAccount(accountDto);
	}

	@GetMapping("/{email}")
	public ResponseEntity<?> findByEmail(@PathVariable String email) {
		return accountService.findByEmail(email);
	}

	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsModel(errors));
	}
}
