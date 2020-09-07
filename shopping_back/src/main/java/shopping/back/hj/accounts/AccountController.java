package shopping.back.hj.accounts;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<?> createAccount(@RequestBody @Valid AccountDto accountDto, Errors errors){
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		accountValidator.validator(accountDto, errors);
		
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		return accountService.createAccount(accountDto);
	}
	
	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsModel(errors));
	}
}
