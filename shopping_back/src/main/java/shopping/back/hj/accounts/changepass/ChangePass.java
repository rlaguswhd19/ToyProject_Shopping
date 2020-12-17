package shopping.back.hj.accounts.changepass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ChangePass {
	private String password;
	private String newPassword;
	private String email;
	
	@Override
	public String toString() {
		return "ChangePass [password=" + password + ", newPassword=" + newPassword + ", email=" + email + "]";
	}
	
}
