package shopping.back.hj.accounts;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.accounts.address.Address;
import shopping.back.hj.dress.Dress;
import shopping.back.hj.dress.dpages.Dpage;
import shopping.back.hj.enums.AccountRole;
import shopping.back.hj.enums.Sex;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Account {
	
	@Id @GeneratedValue
	private Long id;
	
	private LocalDate birth;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	private String phone_number;
	
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	// 양방향 드레스 리스트
	@OneToMany(mappedBy = "account")
	private Set<Dress> dress_arr = new HashSet<Dress>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<AccountRole> roles = Set.of(AccountRole.USER);

	@Override
	public String toString() {
		return "Account [id=" + id + ", birth=" + birth + ", email=" + email + ", password=" + password + ", address="
				+ address + ", phone_number=" + phone_number + ", sex=" + sex + ", dress_arr=" + dress_arr + ", roles="
				+ roles + "]";
	}
}
