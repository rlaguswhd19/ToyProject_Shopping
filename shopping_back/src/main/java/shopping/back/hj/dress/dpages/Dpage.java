package shopping.back.hj.dress.dpages;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
public class Dpage {
	
	@Id @GeneratedValue
	private Long id;
	
	// 옷 page당 하나씩 매칭
	@OneToOne
	private Dress dress;
	
	// account(게시글의 주인) 게시글주인은 많은 페이지를 가질 수 있다.
	@ManyToOne
	private Account account;
	
	// 조회수
	private Integer views;
	
	// 댓글
	// 평점
}
