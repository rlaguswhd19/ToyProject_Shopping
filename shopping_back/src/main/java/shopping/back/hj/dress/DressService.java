package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Service
public class DressService {

	@Autowired
	private DressRepository dressRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommonsMultipartResolver commonsMultipartResolver;
	
	public ResponseEntity createDress(DressDto dressDto) {
		
		Dress dress = modelMapper.map(dressDto, Dress.class);
		//TODO 여기서 dressDto의 이미지를 저장하고 이지미 path를 dress에 셋해야한다.
		
		Dress newDress = dressRepository.save(dress);
		DressModel dressModel = new DressModel(newDress);
		
		WebMvcLinkBuilder selfLinkBuilder= linkTo(DressController.class).slash(newDress.getId());
		URI createUri = selfLinkBuilder.toUri();
		
		dressModel.add(new Link("/docs/dress.html#resources-create-Dress").withRel("profile"));
		dressModel.link_Lists(dressModel);
		dressModel.link_Update(dressModel);
		
		return ResponseEntity.created(createUri).body(dressModel);
	}
}
