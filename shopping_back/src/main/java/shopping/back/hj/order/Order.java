package shopping.back.hj.order;

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
public class Order {
	
	@Id @GeneratedValue
	private Integer id;
	
	// 주문 날짜
	private LocalDateTime order_time = LocalDateTime.now();
	
	// 상품정보 = 상품, 사이즈, 수량, 할인정보
	@ManyToOne
	private Dress dress;
	
	private Integer cnt;
	// 결제정보 = 상품가격 * 수량 % 할인률
	
	// 배송정보 = 수령인, 주소, 핸드폰
	@ManyToOne
	private Account account;
	
	// 입금안내?
	
}
