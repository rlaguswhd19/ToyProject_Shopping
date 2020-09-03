package shopping.back.hj.accounts.address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Builder
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
@EqualsAndHashCode(of = {"id"})
public class Address {
	
	@GeneratedValue @Id
	private Long id;
	
	@NotEmpty
	private String post;
	
	@NotEmpty
	private String road;
	
	@NotEmpty
	private String jibun;
	
	@NotEmpty
	private String building; //추가 주소 = 동, 호수
	
	private String detail; // 동 호수

	@Override
	public String toString() {
		return "Address [id=" + id + ", post=" + post + ", road=" + road + ", jibun=" + jibun + ", detail=" + detail
				+ ", extra=" + building + "]";
	}
}
