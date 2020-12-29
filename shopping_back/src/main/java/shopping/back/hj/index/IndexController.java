package shopping.back.hj.index;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	@GetMapping("/api")
	public RepresentationModel<?> index() {
		var index = new RepresentationModel<>();
		index.add(new Link("/docs/dress.html").withRel("dress"));
		index.add(new Link("/docs/account.html").withRel("account"));
		return index;
	}
}
