package shopping.back.hj.accounts;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.dress.Dress;
import shopping.back.hj.enums.AccountRole;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Account {
	
	@Id @GeneratedValue
	private Long id;

	private LocalDateTime birth;
	
	private String email;
	
	private String password;
	
	private String address;
	
	private String phone_number;
	
	// 양방향 드레스 리스트
	@OneToMany
	private Set<Dress> dress_arr;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<AccountRole> role = new HashSet<>();
}
