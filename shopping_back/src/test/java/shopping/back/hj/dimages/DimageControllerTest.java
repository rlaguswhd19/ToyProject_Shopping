package shopping.back.hj.dimages;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class DimageControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DimageService dimagesService;
	
	// 파일 2개
	private MockMultipartFile file1 = new MockMultipartFile("files", "test1.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test2.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	
	@Test
	@TestDescription("이미지 파일을 전송하는 Test")
	public void create_dimage() throws Exception {
		
		mockMvc.perform(multipart("/api/dimages/basic")
				.file(file1)
				.file(file2)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
		.andDo(print())
		.andExpect(status().isCreated())
		;
	}
	
	@Test
	@TestDescription("이미지 확장자가 아닌 파일을 전송하는 Test, ImageValidator 수행")
	public void create_dimage_BadRequest_WrongFile() throws Exception {
		
		mockMvc.perform(multipart("/api/dimages/basic")
				.file(file1)
				.file(wrongFile)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			;
	}
	
	@Test
	@TestDescription("이미지 확장자가 아닌 파일을 전송하는 Test, ImageValidator 수행")
	public void delete_dimage() throws Exception {
		MultipartFile[] files = new MockMultipartFile[2];
		files[0] = file1;
		files[1] = file2;
		
		ResponseEntity<?> returnData = dimagesService.createDimage(files);
		Dimage dimage = (Dimage) returnData.getBody();
		
		mockMvc.perform(delete("/api/dimages/basic/{id}", dimage.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isOk())
			;
	}
	
}
