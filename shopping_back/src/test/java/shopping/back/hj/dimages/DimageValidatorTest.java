package shopping.back.hj.dimages;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class DimageValidatorTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DimageRepository dimageRepository;
	
	// 파일 2개
	private MockMultipartFile file1 = new MockMultipartFile("files", "test1.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test2.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	
	@Test
	@TestDescription("이미지 파일이 아닐경우 Test, ImageValidator 수행")
	public void uploadBasic_BadRequest_WrongFile() throws Exception {
		
		mockMvc.perform(multipart("/api/dress/uploadBasic")
				.file(file1)
				.file(file2)
				.file(wrongFile)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			;
	}
}
