package shopping.back.hj.dress;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

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
import shopping.back.hj.dress.dimages.Dimage;
import shopping.back.hj.dress.dimages.DimageRepository;
import shopping.back.hj.dress.dsize.Dsize;
import shopping.back.hj.enums.DressSize;
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
	
	@Autowired
	private DimageRepository dimageRepository;
	
	private MockMultipartFile file1 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	
	@Test
	@TestDescription("DressDto에 비어있는 값들을 보내는 Test, Valid 수행")
	public void createDress_BadRequest_EmptyInput() throws Exception {
		DressDto dressDto = DressDto.builder().build();
		
		mockMvc.perform(post("/api/dress")
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("_links.index").exists())
			;
	}
	
	@Test
	@TestDescription("DressDto의 로직에 맞지 않는 값들을 보내는 Test, DressValidator 수행")
	public void createDress_BadRequest_WrongInput() throws Exception {
		Set<Dsize> dsize = createDsize();
		Dimage dimage = createDimage(1);
		
		DressDto dressDto = DressDto.builder()
				.brand("Test COVERNAT")
				.name("Test")
				.article_number("C1804SL01WH")
				.sex(Sex.Men)
				.price(0L)
				.dress_type(DressType.Top)
				.discount(100)
				.explanation("Test")
				.dimage(dimage)
				.dsize(dsize)
				.build();
		
		mockMvc.perform(post("/api/dress")
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
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
	
	public Set<Dsize> createDsize() {
		Set<Dsize> dsize = new HashSet<>();
		
		Dsize d1 = Dsize.builder()
				.size(DressSize.L)
				.height(100)
				.width(50)
				.build();
		Dsize d2 = Dsize.builder()
				.size(DressSize.M)
				.height(90)
				.width(40)
				.build();
		Dsize d3 = Dsize.builder()
				.size(DressSize.S)
				.height(80)
				.width(30)
				.build();
		
		dsize.add(d1);
		dsize.add(d2);
		dsize.add(d3);
		
		return dsize;
	}
	
	public Dimage createDimage(int idx) {
		Dimage dimage = Dimage.builder()
				.image_files("test" + idx + ".jpg")
				.build();
		Dimage newDimage = dimageRepository.save(dimage);
		return newDimage;
	}
}
