package shopping.back.hj.accounts;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
		System.out.println(accountDto);
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		accountValidator.validator(accountDto, errors); 
		
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		return accountService.createAccount(accountDto);
	}
	
	@GetMapping("/signin")
	public void Test(HttpServletResponse response) {
		try {
			response.sendRedirect("http://localhost:3000/account/signin");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsModel(errors));
	}
}
