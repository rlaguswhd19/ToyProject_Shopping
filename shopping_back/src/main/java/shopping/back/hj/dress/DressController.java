package shopping.back.hj.dress;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import shopping.back.hj.common.ErrorsModel;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(value = "/api/dress", produces = MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8", consumes = {"multipart/form-data"})
public class DressController {

	@Autowired
	private DressService dressService;

	@Autowired
	private DressValidator dressValidator;

	@PostMapping
	public ResponseEntity<?> createDress(@RequestBody @Valid DressDto dressDto, Errors errors) {

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		dressValidator.validate(dressDto, errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		return dressService.createDress(dressDto);
	}

	@PostMapping(value = "/multipart")
	public ResponseEntity<?> createDress_Multipart(@ModelAttribute("dressDto") DressDto dressDto,
			@RequestPart("files") MultipartFile[] files, Errors errors) {

		for(MultipartFile file : files) {
			System.out.println(file.getOriginalFilename());
		}

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		dressValidator.validate(dressDto, errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		return dressService.createDress(dressDto);
	}

	@PostMapping("/test")
	public ResponseEntity<?> ImageTest(@RequestPart MultipartFile[] files) {
		
		System.out.println("#############################################");
//		System.out.println(files.getOriginalFilename());
		for(MultipartFile file : files) {
			System.out.println(file.getOriginalFilename());
		}
		
		return ResponseEntity.ok().build();
	}

	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsModel(errors));
	}
}
