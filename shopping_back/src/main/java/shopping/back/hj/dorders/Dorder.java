package shopping.back.hj.dorders;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.accounts.Account;
import shopping.back.hj.dress.Dress;
import shopping.back.hj.enums.DressSize;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Dorder {
	
	@Id @GeneratedValue
	private Long id;
	
	// 주문 날짜
	private LocalDateTime order_time;
	
	// 옷 사이즈, 갯수
	@Enumerated(EnumType.STRING)
	private DressSize dsize;

	// 결제정보 = 상품가격 * 수량 % 할인률
	// 상품정보 = 상품, 사이즈, 수량, 할인정보
	// 옷정보
	@ManyToOne
	private Dress dress;

	// 배송정보 = 수령인, 주소, 핸드폰
	@ManyToOne
	private Account account;
}
