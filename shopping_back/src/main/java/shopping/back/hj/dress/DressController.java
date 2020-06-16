package shopping.back.hj.dress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/dress", produces = MediaTypes.HAL_JSON_VALUE+";charset=UTF-8")
public class DressController {
	
	@Autowired
	DressService dressService;
	
	@PostMapping
	public ResponseEntity<Dress> createDress(@RequestBody Dress dress) {
		return dressService.createDress(dress);
	}
}
