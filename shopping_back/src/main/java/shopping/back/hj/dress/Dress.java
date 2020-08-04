package shopping.back.hj.dress;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.dress.dimages.Dimage;
import shopping.back.hj.dress.dsize.Dsize;
import shopping.back.hj.enums.DressType;
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

	// 브랜드
	private String brand;

	// 품번
	private String article_number;

	// 상의 하의
	@Enumerated(EnumType.STRING)
	private DressType dress_type;

	// 성별
//	@Column(columnDefinition = "varchar(255) default 'Public'")
	@Enumerated(EnumType.STRING)
	private Sex sex;

	// 판매 가격
	private Long price;

	// 할인 가격
//	@Column(columnDefinition = "Integer default '0'")
	private Integer discount;

	// 설명
	private String explanation;

	// 시간
	private LocalDate created_date = LocalDate.now();

	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
	private Dimage dimage;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Dsize> dsize;
}
