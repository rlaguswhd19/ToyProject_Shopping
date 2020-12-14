package shopping.back.hj.accounts;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import shopping.back.hj.enums.AccountRole;

@Service
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRespository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRespository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return new User(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
	}

	private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
		return roles.stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_"+r.name()))
				.collect(Collectors.toSet());
	}

	public ResponseEntity<?> createAccount(AccountDto accountDto) {
		Account account = modelMapper.map(accountDto, Account.class);
		
		// encode
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		
		// birth -> LocalDate
		toBirth(accountDto, account);
		
		// 전화번호 바꾸기
		toPhone(accountDto, account);
		
		Account newAccount = accountRespository.save(account);
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(AccountController.class).slash(newAccount.getId());
		URI createUri = selfLinkBuilder.toUri();
		
		AccountModel accountModel = new AccountModel(newAccount);
		return ResponseEntity.created(createUri).body(accountModel);
	}
	

	public ResponseEntity<?> updateAccount(AccountDto accountDto) {
		Optional<Account> optionalAccount = accountRespository.findByEmail(accountDto.getEmail());
		Account account = optionalAccount.get();
		
		modelMapper.map(accountDto, account);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		
		toBirth(accountDto, account);
		toPhone(accountDto, account);
		
		Account updateAccount = accountRespository.save(account);
		
		AccountModel accountModel = new AccountModel(updateAccount);
		return ResponseEntity.ok(accountModel);
	}
	
	public void toBirth(AccountDto accountDto, Account account) {
		String[] birth_arr = accountDto.getBirth().split("/");
		account.setBirth(LocalDate.of(Integer.parseInt(birth_arr[0]), Integer.parseInt(birth_arr[1]), Integer.parseInt(birth_arr[2])));
	}
	
	public void toPhone(AccountDto accountDto, Account account) {
		StringBuilder number = new StringBuilder(accountDto.getPhone_number());
		number.insert(3, "-");
		number.insert(8, "-");
		account.setPhone_number(number.toString());
	}

	public ResponseEntity<?> findByEmail(String email) {
		Optional<Account> optionalAccount = accountRespository.findByEmail(email);
		if(optionalAccount.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Account account = optionalAccount.get();
		
		return ResponseEntity.ok(account);
	}
}
