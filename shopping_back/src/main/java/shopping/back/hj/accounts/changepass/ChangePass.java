package shopping.back.hj.accounts.changepass;


import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ChangePass {
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String newPassword;
	
	@NotEmpty
	private String email;
	
	@Override
	public String toString() {
		return "ChangePass [password=" + password + ", newPassword=" + newPassword + ", email=" + email + "]";
	}
	
}
