package shopping.back.hj.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	@Autowired
	private AccountRespository accountRespository;
	
	public ResponseEntity<?> createAccount(AccountDto accountDto){
		return null;
	}
}
