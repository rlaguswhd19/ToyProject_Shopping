package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class DressService {

	@Autowired
	DressRepository dressRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public ResponseEntity createDress(DressDto dressDto) {
		Dress dress = modelMapper.map(dressDto, Dress.class);
		Dress newDress = dressRepository.save(dress);

		URI createUri = linkTo(DressController.class).slash(newDress.getId()).toUri();
		return ResponseEntity.created(createUri).body(newDress);
	}

}
