package shopping.back.hj.dress;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import shopping.back.hj.dimages.Dimage;

public class DressModel extends EntityModel<Dress> {

	@SuppressWarnings("deprecation")
	public DressModel(Dress content, Link... links) {
		super(content, links);
		add(linkTo(DressController.class).slash(content.getId()).withSelfRel());
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

	public DressModel link_imagePath(DressModel dressModel, Dimage dimage) {
		dressModel.add(new Link("/assets/images/basic/" + dimage.getId()).withRel("images-dress"));
		return dressModel;
	}

}
