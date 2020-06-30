package shopping.back.hj.dress;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import shopping.back.hj.common.ErrorsModel;

@RestController
@CrossOrigin(origins = { "*" })
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
	
	@PostMapping("/multipart")
	public ResponseEntity<?> createDress_Multipart(@RequestPart("DressDto") @Valid DressDto dressDto, @RequestPart("file") MultipartFile file, Errors errors) {
		System.out.println(dressDto);
		System.out.println(file.getName());
		
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
			
		dressValidator.validate(dressDto, errors);
		
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		return dressService.createDress(dressDto);
	}

	@PostMapping("/test")
	public void ImageTest(@RequestPart MultipartFile file) {
		System.out.println(file.getName());
	}
	
	private ResponseEntity<?> badRequest(Errors errors){
		return ResponseEntity.badRequest().body(new ErrorsModel(errors)); 
	}
}
