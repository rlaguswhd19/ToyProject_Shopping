package shopping.back.hj.dress;

import java.time.LocalDate;
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
import shopping.back.hj.enums.DressCategory;
import shopping.back.hj.enums.DressColor;
import shopping.back.hj.enums.Sex;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class DressDto {
	
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
		private DressCategory category;
		
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
		
		@NotEmpty
		private String material;
		
		@NotEmpty
		private String origin;
		
		@NotEmpty
		private String manufacture;

		@Override
		public String toString() {
			return "DressDto [name=" + name + ", color=" + color + ", article_number=" + article_number + ", category="
					+ category + ", sex=" + sex + ", price=" + price + ", discount=" + discount + ", explanation="
					+ explanation + ", dimage=" + dimage + ", dsize=" + dsize + ", material=" + material + ", origin="
					+ origin + ", manufacture=" + manufacture + "]";
		}
}
