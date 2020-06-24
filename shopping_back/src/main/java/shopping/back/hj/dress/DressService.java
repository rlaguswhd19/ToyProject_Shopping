package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DressService {

	@Autowired
	private DressRepository dressRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity createDress(DressDto dressDto) {
		Dress dress = modelMapper.map(dressDto, Dress.class);
		Dress newDress = dressRepository.save(dress);
		DressModel dressModel = new DressModel(newDress);
		
		WebMvcLinkBuilder selfLinkBuilder= linkTo(DressController.class).slash(newDress.getId());
		URI createUri = selfLinkBuilder.toUri();
		
		dressModel.link_Lists(dressModel);
		dressModel.link_Update(dressModel);
		
		return ResponseEntity.created(createUri).body(dressModel);
	}
}
