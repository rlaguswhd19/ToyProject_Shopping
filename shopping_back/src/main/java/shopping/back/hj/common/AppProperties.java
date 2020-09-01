package shopping.back.hj.common;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "hj-app")
@Getter @Setter
public class AppProperties {
	
	@NotEmpty
	private String adminEmail;
	
	@NotEmpty
	private String adminPassword;
	
	@NotEmpty
	private String userEmail;
	
	@NotEmpty
	private String userPassword;
	
	@NotEmpty
	private String clientId;
	
	@NotEmpty
	private String clientSecret;
}
