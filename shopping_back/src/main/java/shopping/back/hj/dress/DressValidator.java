package shopping.back.hj.dress;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import shopping.back.hj.enums.DressType;
import shopping.back.hj.enums.Sex;

@Component
public class DressValidator {

	public void validate(DressDto dressDto, Errors errors) {

		if (dressDto.getPrice() == 0) { // 파는 가격이 0일 경우
//			field error
			errors.rejectValue("price", "price = 0 is wrongValue", "판매액은 0원일 수 없습니다.");
//			global error
			errors.reject("wrongPrice", "Price is Wrong");
		}

		if (dressDto.getDiscount() >= 100) { // 100% 할인일 경우
			errors.rejectValue("discount", "discount = 100 is wrongValue", "할인율은 100%일 수 없습니다.");
			errors.reject("wrongDiscount", "Discount is Wrong");
		}

		// TODO begin
		Sex sex = dressDto.getSex();
		if(!(sex == Sex.Man || sex == Sex.Woman || sex == Sex.Public)) {
			errors.rejectValue("sex", "sex is wrongValue", "다른 성별이 올 수 없습니다.");
			errors.reject("wrongSex", "Sex is Wrong");
		}
		
		DressType dressType = dressDto.getDress_type();
	}
}
