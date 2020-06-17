package shopping.back.hj.dress;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.enums.DressType;
import shopping.back.hj.enums.Sex;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class DressDto {
		// 브랜드
		private String brand;
		
		// 품번
		private String article_number;
		
		// 상의  하의
		@Enumerated(EnumType.STRING)
		private DressType dress_type;
		
		// 성별
		@Enumerated(EnumType.STRING)
		private Sex sex;
		
		// 판매 가격
		private Integer sale;
		
		// 할인 가격
		private Integer discount;
		
		// 설명
		private String explanation;
}
