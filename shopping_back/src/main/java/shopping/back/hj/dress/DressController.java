package shopping.back.hj.dress;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import shopping.back.hj.common.ErrorsModel;
import shopping.back.hj.common.ImageValidator;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(value = "/api/dress", produces = MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
public class DressController {

	@Autowired
	private DressService dressService;

	@Autowired
	private DressValidator dressValidator;

	@Autowired
	private ImageValidator imageValidator;

	@PostMapping
	public ResponseEntity<?> createDress(@ModelAttribute @Valid DressDto dressDto, Errors errors,
			@RequestPart("files") MultipartFile[] files) throws IllegalStateException, IOException {
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		dressValidator.validate(dressDto, errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		// file이 있으면 없을경우 page 등록을 못하게 하자.
		if (files.length != 0) {
			imageValidator.validate(files, errors);

			if (errors.hasErrors()) {
				return badRequest(errors);
			}
		}

		return dressService.createDress(dressDto, files);
	}

	@GetMapping
	public ResponseEntity<?> queryDress(Pageable pageable, PagedResourcesAssembler<Dress> assembler) {
		return dressService.queryDress(pageable, assembler);
	}

	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsModel(errors));
	}
}