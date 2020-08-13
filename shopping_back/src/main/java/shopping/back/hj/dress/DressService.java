package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javassist.bytecode.Mnemonic;
import shopping.back.hj.dress.dimages.Dimage;
import shopping.back.hj.dress.dimages.DimageRepository;
import shopping.back.hj.dress.dimages.DimageService;

@Service
public class DressService {

	@Autowired
	private DressRepository dressRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DimageService dimageService;

	public ResponseEntity<?> createDress(DressDto dressDto) throws IllegalStateException, IOException {

		Dress dress = modelMapper.map(dressDto, Dress.class);
		String manufacture = dressDto.getManufacture();
		String[] temp = manufacture.split("-");
		dress.setManufacture(LocalDate.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), 1));
		
		System.out.println(dress);

		Dress newDress = dressRepository.save(dress);

		DressModel dressModel = new DressModel(newDress);

		WebMvcLinkBuilder selfLinkBuilder = linkTo(DressController.class).slash(newDress.getId());
		URI createUri = selfLinkBuilder.toUri();

		// link
		dressModel.add(new Link("/docs/dress.html#resources-create-Dress").withRel("profile"));
		dressModel.link_Lists(dressModel);
		dressModel.link_Update(dressModel);
		dressModel.link_imagePath(dressModel, newDress.getDimage());

		return ResponseEntity.created(createUri).body(dressModel);
	}

	public ResponseEntity<?> listsDress(Pageable pageable, PagedResourcesAssembler<Dress> assembler) {

		Page<Dress> page = dressRepository.findAll(pageable);
		PagedModel<EntityModel<Dress>> pageModel = assembler.toModel(page, dress -> pageDress(dress));

		pageModel.add(new Link("/docs/dress.html#resources-lists-dress").withRel("profile"));

		return ResponseEntity.ok(pageModel);
	}

	private DressModel pageDress(Dress dress) {
		DressModel dressModel = new DressModel(dress);
		dressModel.link_imagePath(dressModel, dress.getDimage());
		return dressModel;
	}

	public ResponseEntity<?> getDress(Long id) {
		Optional<Dress> optionalDress = dressRepository.findById(id);

		if (optionalDress.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Dress dress = optionalDress.get();

		DressModel dressModel = new DressModel(dress);
		dressModel.add(new Link("/docs/dress.html/resources-get-dress").withRel("profile"));
		dressModel.link_imagePath(dressModel, dress.getDimage());

		return ResponseEntity.ok(dressModel);
	}

	public ResponseEntity<?> updateDress(Long id, DressDto dressDto) {
		Optional<Dress> optionalDress = dressRepository.findById(id);
		if(optionalDress.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Dress dress = optionalDress.get();
		
		Dimage dimage = dimageService.findById(dressDto.getDimage().getId());
		if(dimage == null) {
			return ResponseEntity.notFound().build();
		}
		
		modelMapper.map(dressDto, dress);
		
		DressModel dressModel = new DressModel(dress);
		dressModel.add(new Link("/docs/dress.html/resources-update-dress").withRel("profile"));
		dressModel.link_imagePath(dressModel, dress.getDimage());
		
		return ResponseEntity.ok(dressModel);
	}
	
}
