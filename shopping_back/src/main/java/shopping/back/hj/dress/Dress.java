package shopping.back.hj.dress;

import java.time.LocalDate;

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
import shopping.back.hj.enums.DressType;
import shopping.back.hj.enums.Sex;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Setter @Getter @EqualsAndHashCode(of = "id")
public class Dress {

	@Id @GeneratedValue
	private Long id;
	
	// 이름
	private String name;
	
	// 브랜드
	private String brand;
	
	// 품번
	private String article_number;
	
	// 상의  하의
	@Enumerated(EnumType.STRING)
	private DressType dress_type;
	
	// 성별
//	@Column(columnDefinition = "varchar(255) default 'Public'")
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	// 판매 가격
	private Integer price;
	
	// 할인 가격
//	@Column(columnDefinition = "Integer default '0'")
	private Integer discount;
	
	// 설명
	private String explanation;
	
	// 시간
	private LocalDate created_date = LocalDate.now();

	private String files;

	@Override
	public String toString() {
		return "Dress [id=" + id + ", name=" + name + ", brand=" + brand + ", article_number=" + article_number
				+ ", dress_type=" + dress_type + ", sex=" + sex + ", price=" + price + ", discount=" + discount
				+ ", explanation=" + explanation + ", created_date=" + created_date + ", files=" + files + "]";
	}
}
