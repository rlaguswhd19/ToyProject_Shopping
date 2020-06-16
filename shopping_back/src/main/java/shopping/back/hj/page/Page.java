package shopping.back.hj.page;

import java.util.HashMap;

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
import shopping.back.hj.dress.DressSize;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Page {
	
	@Id @GeneratedValue
	private Integer id;
	
	// 옷
	@OneToOne
	private Dress dress;
	
	// 이미지 경로
	private String image_path;
	
	// 사이즈, 남은수량
	private HashMap<DressSize, Integer> size_arr;
	
	// account(게시글의 주인)
	@ManyToOne
	private Account account;
	
	// 조회수
	private Integer views;
}
