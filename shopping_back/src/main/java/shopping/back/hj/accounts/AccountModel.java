package shopping.back.hj.accounts;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import shopping.back.hj.dress.DressController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class AccountModel extends EntityModel<Account>{

	@SuppressWarnings("deprecation")
	public AccountModel(Account content, Link... links) {
		super(content, links);
		add(linkTo(AccountController.class).slash(content.getId()).withSelfRel());
	}
	
	public void link_update(AccountModel accountModel) {
		accountModel.add(linkTo(DressController.class).withRel("update-account"));
	}
}
