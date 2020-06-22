package shopping.back.hj.dress;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DressValidator {

	public void validate(DressDto dressDto, Errors errors) {

		if (dressDto.getSale() == 0) { // 파는 가격이 0일 경우
//			field error
			errors.rejectValue("sale", "sale = 0 is wrongValue", "판매액은 0원일 수 없습니다.");
//			global error
			errors.reject("wrongSale", "Sale is Wrong");
		}

		if (dressDto.getDiscount() >= 100) { // 100% 할인일 경우
			errors.rejectValue("discount", "discount = 100 is wrongValue", "할인율은 100%일 수 없습니다.");
			errors.reject("wrongDiscount", "Discount is Wrong");
		}

		// TODO begin
	}
}
