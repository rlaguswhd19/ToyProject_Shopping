package shopping.back.hj.dress;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;
import shopping.back.hj.enums.DressType;
import shopping.back.hj.enums.Sex;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class DressValidatorTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private DressRepository dressRepository;
	
	private MockMultipartFile file1 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	
	@Test
	@TestDescription("DressDto의 로직에 맞지 않는 값들을 보내는 Test, DressValidator 수행")
	public void createDress_BadRequest_WrongInput() throws Exception {
		DressDto dressDto = DressDto.builder()
				.brand("COVERNAT")
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.price(0)
				.dress_type(DressType.Top)
				.discount(100)
				.explanation("Test")
				.build();
		
		mockMvc.perform(multipart("/api/dress")
				.file(file1)
				.file(file2)
				.param("brand", dressDto.getBrand())
				.param("article_number", dressDto.getArticle_number())
				.param("sex", dressDto.getSex().toString())
				.param("price", dressDto.getPrice().toString())
				.param("dress_type", dressDto.getDress_type().toString())
				.param("discount", dressDto.getDiscount().toString())
				.param("explanation", dressDto.getExplanation())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("content[0].objectName").exists())
			.andExpect(jsonPath("content[0].defaultMessage").exists())
			.andExpect(jsonPath("content[0].code").exists())
			.andExpect(jsonPath("_links.index").exists())
			;
	}
	
	@Test
	@TestDescription("이미지 파일이 아닐경우 Test, ImageValidator 수행")
	public void createDress_BadRequest_WrongFile() throws Exception {
		DressDto dressDto = DressDto.builder()
				.brand("COVERNAT")
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.price(39000)
				.dress_type(DressType.Top)
				.discount(10)
				.explanation("Test")
				.build();
		
		mockMvc.perform(multipart("/api/dress")
				.file(file1)
				.file(wrongFile)
				.param("brand", dressDto.getBrand())
				.param("article_number", dressDto.getArticle_number())
				.param("sex", dressDto.getSex().toString())
				.param("price", dressDto.getPrice().toString())
				.param("dress_type", dressDto.getDress_type().toString())
				.param("discount", dressDto.getDiscount().toString())
				.param("explanation", dressDto.getExplanation())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("content[0].objectName").exists())
			.andExpect(jsonPath("content[0].defaultMessage").exists())
			.andExpect(jsonPath("content[0].code").exists())
			;
	}
}
