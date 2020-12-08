package shopping.back.hj.accounts;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.accounts.address.Address;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter 
public class AccountDto {
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String phone_number;
	
	@NotEmpty
	private String birth;
	
	@NotNull
	private Address address;

	@Override
	public String toString() {
		return "AccountDto [email=" + email + ", password=" + password + ", phone_number=" + phone_number + ", birth="
				+ birth + ", address=" + address + "]";
	} 
}
