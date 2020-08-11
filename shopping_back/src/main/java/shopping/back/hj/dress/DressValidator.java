package shopping.back.hj.dress;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import shopping.back.hj.dress.dimages.DimageRepository;
import shopping.back.hj.dress.dsize.Dsize;
import shopping.back.hj.enums.DressColor;
import shopping.back.hj.enums.DressSize;
import shopping.back.hj.enums.DressType;
import shopping.back.hj.enums.Sex;

@Component
public class DressValidator {

	@Autowired
	DimageRepository dimageRepository;

	@Autowired
	DressRepository dressRepository;

	public void validate(DressDto dressDto, Errors errors) {
		if(dressDto.getName().length() > 50) {
			errors.rejectValue("name", "name is wrongValue", "이름의 길이가 50이 넘어서는 안됩니다.");
			errors.reject("wrongName", "Name is Wrong");
		}
		
		if(dressDto.getArticle_number().length() > 20) {
			errors.rejectValue("article_number", "article_number is wrongValue", "품번의 길이가 20이 넘어선 안됩니다.");
			errors.reject("wrongArticle_number", "Article_number is Wrong");
		}
		
		DressType dress_type = dressDto.getDress_type();
		if(!(dress_type == DressType.Top || dress_type == DressType.Bottom)) {
			errors.rejectValue("dress_type", "dress_type is wrongValue", "옷의 타입이 잘못된 값입니다.");
			errors.reject("wrongDress_type", "Dress_type is Wrong");
		}
		
		DressColor dressColor = dressDto.getColor();
		if (!(dressColor == DressColor.RED || dressColor == DressColor.ORANGE || dressColor == DressColor.YELLOW
				|| dressColor == DressColor.GREEN || dressColor == DressColor.BLUE || dressColor == DressColor.PURPLE
				|| dressColor == DressColor.BLACK || dressColor == DressColor.WHITE)) {
			errors.rejectValue("color", "color is wrongValue", "다른 색상이 올 수 없습니다.");
			errors.reject("wrongColor", "Color is Wrong");
		}
		

		// price
		if (dressDto.getPrice() < 1) { // 파는 가격이 0일 경우
//			field error
			errors.rejectValue("price", "price = 0 is wrongValue", "판매액은 0원일 수 없습니다.");
//			global error
			errors.reject("wrongPrice", "Price is Wrong");
		}

		if (dressDto.getDiscount() >= 100 || dressDto.getDiscount() < 1) { // 100% 할인일 경우
			errors.rejectValue("discount", "discount = "+dressDto.getDiscount()+" is wrongValue", "할인율은 +"+dressDto.getDiscount()+"%일 수 없습니다.");
			errors.reject("wrongDiscount", "Discount is Wrong");
		}
		
		if(dressDto.getExplanation().length() > 500) {
			errors.rejectValue("explanation", "explanation의 길이가 500이 넘어서는 안됩니다.");
			errors.reject("wrongExplanation", "Explanation is Wrong");
		}
		
		// sex
		Sex sex = dressDto.getSex();
		if (!(sex == Sex.Men || sex == Sex.Women || sex == Sex.Free)) {
			errors.rejectValue("sex", "sex is wrongValue", "다른 성별이 올 수 없습니다.");
			errors.reject("wrongSex", "Sex is Wrong");
		}

		// dsize 크기 비교 go to hell...
		Set<Dsize> dsize = dressDto.getDsize();
		Map<DressSize, Dsize> map = new HashMap<>();
		DressSize[] arr = { DressSize.XXS, DressSize.XS, DressSize.S, DressSize.M, DressSize.L, DressSize.XL,
				DressSize.XXL, DressSize.XXXL };
		if(dsize.size() == 0) {
			errors.rejectValue("dsize", "옷 사이즈를 적지않았습니다.",
					"dsize 에러");
			errors.reject("wrongDsize", "Dsize is Wrong");
		}
		
		for (Dsize ds : dsize) {
			map.put(ds.getSize(), ds);
		}

		Dsize temp = null;

		for (int i = 0; i < arr.length; i++) {
			Dsize ds = map.get(arr[i]);
			if (ds == null) {
				continue;
			} else {
				if (temp == null) {
					temp = ds;
					continue;
				}

				if (temp.getWidth() >= ds.getWidth() || temp.getHeight() >= ds.getHeight()
						|| temp.getInfo() >= ds.getInfo()) {
					errors.rejectValue("dsize", "옷 사이즈 " + temp.getSize() + "보다  " + ds.getSize() + "이 작습니다.",
							"dsize 에러");
					errors.reject("wrongDsize", "Dsize is Wrong");
				}

				temp = ds;
			}
		}
	}
}
