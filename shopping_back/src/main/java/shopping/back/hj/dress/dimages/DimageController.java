package shopping.back.hj.dress.dimages;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/dimages", produces = MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
public class DimageController {
	
	@Autowired
	private DimageValidator dimageValidator;
	
	@Autowired
	private DimageService dimageService;
	
	@Autowired
	private DimageRepository dimageRepository;
	
	@PostMapping
	public ResponseEntity<?> createDimage(@RequestPart("files") MultipartFile[] files, @RequestParam("idx") String idx) throws IllegalStateException, IOException {
		
		int repIdx = Integer.parseInt(idx);
		if(repIdx > files.length) {
			return ResponseEntity.badRequest().build();
		}
		
		// file이 있으면 없을경우 page 등록을 못하게 하자.
		if (files.length != 0) {
			boolean isOk = true;
			isOk = dimageValidator.validate(files);

			if (!isOk) {
				return ResponseEntity.badRequest().build();
			}
		}
		
		return dimageService.createDimage(files, repIdx);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDimage(@PathVariable Long id) {
		
		Optional<Dimage> optionalDimage = dimageRepository.findById(id);
		
		if(optionalDimage.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Dimage dimage = optionalDimage.get();
		
		return dimageService.deleteDimage(dimage);
	}
	
}
