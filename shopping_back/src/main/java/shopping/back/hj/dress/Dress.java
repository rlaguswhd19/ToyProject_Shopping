package shopping.back.hj.dress;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Setter @Getter @EqualsAndHashCode(of = "id")
public class Dress {
	@Id @GeneratedValue
	private Integer id;
	
	// 브랜드
	private String brand;
	
	// 품번
	private String article_number;
	
	// 성별
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	// 판매 가격
	private Integer sales;
	
	// 할인 가격
	private Integer discount = 0;
	
	// 색상
	private String color;
	
	// 옷 사이즈
	private DressSize dsize;
	
	// 설명
	private String explanation;
}
