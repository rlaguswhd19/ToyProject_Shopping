package shopping.back.hj.index;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import shopping.back.hj.dress.DressController;

@RestController
public class IndexController {
	
	@GetMapping("/api")
	public RepresentationModel<?> index() {
		var index = new RepresentationModel<>();
		index.add(linkTo(DressController.class).withRel("dress"));
		return index;
	}
}
