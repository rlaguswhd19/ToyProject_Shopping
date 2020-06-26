package shopping.back.hj.dress;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shopping.back.hj.common.ErrorsModel;

@RestController
@RequestMapping(value = "/api/dress", produces = MediaTypes.HAL_JSON_VALUE+";charset=UTF-8")
public class DressController {
	
	@Autowired
	private DressService dressService;
	
	@Autowired
	private DressValidator dressValidator;
	
	@PostMapping
	public ResponseEntity<?> createDress(@RequestBody @Valid DressDto dressDto, Errors errors) {
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		dressValidator.validate(dressDto, errors);
		
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		return dressService.createDress(dressDto);
	}
	
	private ResponseEntity<?> badRequest(Errors errors){
		return ResponseEntity.badRequest().body(new ErrorsModel(errors)); 
	}
}
