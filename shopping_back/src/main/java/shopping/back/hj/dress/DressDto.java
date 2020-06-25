package shopping.back.hj.dress;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.accounts.Account;
import shopping.back.hj.enums.DressType;
import shopping.back.hj.enums.Sex;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class DressDto {
		// 브랜드
		@NotEmpty
		private String brand;
		
		// 품번
		@NotEmpty
		private String article_number;
		
		// 상의  하의
		@NotNull
		@Enumerated(EnumType.STRING)
		private DressType dress_type;
		
		// 성별
		@NotNull
		@Enumerated(EnumType.STRING)
		private Sex sex = Sex.Man;
		
		// 판매 가격
		@Min(0)
		@NotNull
		private Integer sale;
		
		// 할인 가격
		@Min(0) @Max(100)
		@NotNull
		private Integer discount = 0;
		
		// 설명
		@NotEmpty
		private String explanation;
}
