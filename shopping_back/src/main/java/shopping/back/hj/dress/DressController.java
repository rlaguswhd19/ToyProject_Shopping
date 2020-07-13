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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> createDress(@RequestBody @Valid DressDto dressDto, Errors errors) throws IllegalStateException, IOException {
		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		dressValidator.validate(dressDto, errors);

		if (errors.hasErrors()) {
			return badRequest(errors);
		}

		return dressService.createDress(dressDto);
	}

	@GetMapping
	public ResponseEntity<?> listsDress(Pageable pageable, PagedResourcesAssembler<Dress> assembler) {
		return dressService.listsDress(pageable, assembler);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDress(@PathVariable Long id) {
		return dressService.getDress(id);
	}
	
	
	private ResponseEntity<?> badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsModel(errors));
	}
}