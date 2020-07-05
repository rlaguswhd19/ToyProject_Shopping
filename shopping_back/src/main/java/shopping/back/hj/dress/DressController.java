package shopping.back.hj.dress;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import shopping.back.hj.common.ErrorsModel;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(value = "/api/dress", produces = MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
public class DressController {

	@Autowired
	private DressService dressService;

	@Autowired
	private DressValidator dressValidator;

	@PostMapping
	public ResponseEntity<?> createDress_Multipart(@ModelAttribute @Valid DressDto dressDto, Errors errors,
			@RequestPart("files") MultipartFile[] files) throws IllegalStateException, IOException {
		
		// 확장자 검증하기
		for (MultipartFile file : files) {
			System.out.println(file.getOriginalFilename());
		}

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		dressValidator.validate(dressDto, errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		return dressService.createDress(dressDto, files);
	}

	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsModel(errors));
	}
}