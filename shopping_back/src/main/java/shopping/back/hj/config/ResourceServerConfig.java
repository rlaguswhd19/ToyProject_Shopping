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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("hj_resources");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.anonymous()
				.and()
			.authorizeRequests()
			
				// GET 요청 모두 허용
				.mvcMatchers(HttpMethod.GET, "/api/**")
					.permitAll()
				// 회원가입은 로그인 하지 않은 사용자만 가능하다.
				.mvcMatchers(HttpMethod.POST, "/api/accounts")
					.anonymous()
//				 Test
//				.mvcMatchers(HttpMethod.POST, "/api/**").permitAll()
				.anyRequest()
					.authenticated()
				.and()
			.formLogin()
//				.loginPage("/api/accounts/signin") 로그인 페이지
//				.defaultSuccessUrl(defaultSuccessUrl) 성공시
//				.failureUrl(authenticationFailureUrl) 실패시
				.and()
			.cors()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
	       // - (3)
	       configuration.addAllowedOrigin("*");
	       configuration.addAllowedMethod("*");
	       configuration.addAllowedHeader("*");
	       configuration.setAllowCredentials(true);
	       
	       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	       source.registerCorsConfiguration("/**", configuration);
	       return source;
	}
}
