package shopping.back.hj.dress;

import java.util.Set;

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
import shopping.back.hj.dress.dimages.Dimage;
import shopping.back.hj.dress.dsize.Dsize;
import shopping.back.hj.enums.DressColor;
import shopping.back.hj.enums.DressType;
import shopping.back.hj.enums.Sex;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class DressDto {
	
		// 브랜드
		@NotEmpty
		private String brand;
		
		// 이름
		@NotEmpty
		private String name;
		
		@NotNull
		@Enumerated(EnumType.STRING)
		private DressColor color;
		
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
		private Sex sex = Sex.Free;
		
		// 판매 가격
		@Min(0)
		@NotNull
		private Long price;
		
		// 할인 가격
		@Min(0) @Max(100)
		@NotNull
		private Integer discount = 0;
		
		// 설명
		@NotEmpty
		private String explanation;
		
		@NotNull
		private Dimage dimage;
		
		@NotNull
		private Set<Dsize> dsize;

		@Override
		public String toString() {
			return "DressDto [brand=" + brand + ", name=" + name + ", article_number=" + article_number
					+ ", dress_type=" + dress_type + ", sex=" + sex + ", price=" + price + ", discount=" + discount
					+ ", explanation=" + explanation + ", dimage=" + dimage + ", dsize=" + dsize + "]";
		}
}
