package shopping.back.hj.accounts;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter 
public class AccountDto {
	
	private String email;
	
	private String password;
	
	private String address;
	
	private String phone_number;
	
	private String birth;
}
