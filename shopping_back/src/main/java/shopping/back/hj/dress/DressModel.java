package shopping.back.hj.dress;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DressModel extends EntityModel<Dress> {

	@SuppressWarnings("deprecation")
	public DressModel(Dress content, Link... links) {
		super(content, links);
		add(linkTo(DressController.class).slash(content.getId()).withSelfRel());
		// TODO Auto-generated constructor stub
	}

	public DressModel link_Update(DressModel dressModel) {
		// PUT
		dressModel.add(linkTo(DressController.class).withRel("update-dress"));
		return dressModel;
	}

	public DressModel link_Lists(DressModel dressModel) {
		dressModel.add(linkTo(DressController.class).withRel("lists-dress"));
		return dressModel;
	}
	
	public DressModel link_imagePath(DressModel dressModel, LocalDate created_date) {
		dressModel.add(new Link("/images/basic/"+created_date).withRel("images-dress"));
		return dressModel;
	}

}
