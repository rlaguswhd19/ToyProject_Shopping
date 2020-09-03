package shopping.back.hj.accounts;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
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

import shopping.back.hj.dress.DressController;
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
		String[] birth_arr = accountDto.getBirth().split("/");
		account.setBirth(LocalDate.of(Integer.parseInt(birth_arr[0]), Integer.parseInt(birth_arr[1]), Integer.parseInt(birth_arr[2])));
		
		
		System.out.println("????????????????????????????????????????????");
		// 전화번호 바꾸기
		StringBuilder number = new StringBuilder(accountDto.getPhone_number());
		number.insert(3, "-");
		number.insert(8, "-");
		account.setPhone_number(number.toString());
		
		System.out.println(account);
		
		Account newAccount = accountRespository.save(account);
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(DressController.class).slash(newAccount.getId());
		URI createUri = selfLinkBuilder.toUri();
		
		return ResponseEntity.created(createUri).body(newAccount);
	}
}
