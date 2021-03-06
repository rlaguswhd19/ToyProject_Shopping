package shopping.back.hj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableResourceServer
public class ResoucreServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("hj_resource");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.anonymous()
				.and()
			.authorizeRequests()
				.mvcMatchers(HttpMethod.GET, "/api/**") //얻는 것
					.permitAll()
				.mvcMatchers(HttpMethod.POST, "/api/accounts") //회원가입
					.anonymous()
				.requestMatchers(CorsUtils::isPreFlightRequest) // Token 발급 option 근데 안돼서  Filter를 수정함
					.permitAll()
				.anyRequest() // 다른 모든 요청 token 필요 
					.authenticated()
				.and()
			.cors()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//	    configuration.addAllowedOrigin("*");
//	    configuration.addAllowedMethod("*");
//	    configuration.addAllowedHeader("*");
//	    configuration.setAllowCredentials(true);
//	       
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    source.registerCorsConfiguration("/**", configuration);
//	    return source;
//	}
}
