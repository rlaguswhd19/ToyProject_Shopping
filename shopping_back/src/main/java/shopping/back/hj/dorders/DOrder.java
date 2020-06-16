package shopping.back.hj.dorders;

import java.time.LocalDateTime;

import javax.persistence.Entity;
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

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class DOrder {
	
	@Id @GeneratedValue
	private Integer id;
	
	// 주문 날짜
	private LocalDateTime order_time;
	
	// 조회수
	private Integer clickcount;
	
	// 결제정보 = 상품가격 * 수량 % 할인률
	// 상품정보 = 상품, 사이즈, 수량, 할인정보
	@ManyToOne
	private Dress dress;

	// 배송정보 = 수령인, 주소, 핸드폰
	@ManyToOne
	private Account account;
}
