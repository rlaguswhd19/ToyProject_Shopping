package shopping.back.hj.dress;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.accounts.Account;
import shopping.back.hj.dress.dimages.Dimage;
import shopping.back.hj.dress.dsize.Dsize;
import shopping.back.hj.enums.DressCategory;
import shopping.back.hj.enums.DressColor;
import shopping.back.hj.enums.Sex;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Dress {

	@Id
	@GeneratedValue
	private Long id;

	// 이름
	private String name;

	//색깔
	@Enumerated(EnumType.STRING)
	private DressColor color;

	// 품번
	private String article_number;

	// 상의 하의
	@Enumerated(EnumType.STRING)
	private DressCategory category;

	// 성별
//	@Column(columnDefinition = "varchar(255) default 'Public'")
	@Enumerated(EnumType.STRING)
	private Sex sex;

	// 판매 가격
	private Long price;

	// 할인 가격
//	@Column(columnDefinition = "Integer default '0'")
	private Integer discount;
	
	private Long discount_price;
	
	// 설명
	private String explanation;

	// 시간
	private LocalDate created = LocalDate.now();

	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
	private Dimage dimage;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Dsize> dsize;
	
	private String material;
	
	private String origin;
	
	private LocalDate manufacture;
	
	private Long view = 0L;
	
	@ManyToOne // 주인
	@JoinColumn(name = "account_id")
	private Account account;

	@Override
	public String toString() {
		return "Dress [id=" + id + ", name=" + name + ", color=" + color + ", article_number=" + article_number
				+ ", category=" + category + ", sex=" + sex + ", price=" + price + ", discount=" + discount
				+ ", discount_price=" + discount_price + ", explanation=" + explanation + ", created=" + created
				+ ", dimage=" + dimage + ", dsize=" + dsize + ", material=" + material + ", origin=" + origin
				+ ", manufacture=" + manufacture + ", view=" + view + ", account=" + account + "]";
	}
}
