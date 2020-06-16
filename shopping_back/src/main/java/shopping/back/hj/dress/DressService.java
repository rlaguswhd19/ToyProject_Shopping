package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public class DressService {

	@Autowired
	DressRepository dressRepository;
	
	public ResponseEntity<Dress> createDress() {
		
		URI createUri = linkTo(methodOn(DressController.class).createDress()).slash("id").toUri();
		return ResponseEntity.created(createUri).build();
	}

}
