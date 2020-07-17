package shopping.back.hj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("static.resources.properties")
@Getter @Setter
public class StaticResourcesProperties {
	
	private String save_location;
	
	private String config_location;
}
