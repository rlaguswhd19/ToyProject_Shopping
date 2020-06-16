package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DressService {

	@Autowired
	DressRepository dressRepository;

	public ResponseEntity<Dress> createDress(Dress dress) {
		Dress newDress = dressRepository.save(dress);

		URI createUri = linkTo(DressController.class).slash(newDress.getId()).toUri();
		return ResponseEntity.created(createUri).body(newDress);
	}

}
