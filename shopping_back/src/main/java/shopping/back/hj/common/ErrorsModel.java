package shopping.back.hj.common;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.Errors;

import shopping.back.hj.index.IndexController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ErrorsModel extends EntityModel<Errors>{

	@SuppressWarnings("deprecation")
	public ErrorsModel(Errors content, Link... links) {
		super(content, links);
		add(linkTo(IndexController.class).withRel("index"));
	}
}
