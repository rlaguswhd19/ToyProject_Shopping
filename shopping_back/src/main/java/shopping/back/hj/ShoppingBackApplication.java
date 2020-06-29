package shopping.back.hj;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class ShoppingBackApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ShoppingBackApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public CommonsMultipartResolver getMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		// 하나의 용량
		commonsMultipartResolver.setMaxUploadSize(10000000);
		
		// 전체 용량
		commonsMultipartResolver.setMaxUploadSizePerFile(100000000);
		
		// 인코딩
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		
		return commonsMultipartResolver;
		
	}
}
