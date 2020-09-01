package shopping.back.hj.dimages;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import shopping.back.hj.accounts.Account;
import shopping.back.hj.accounts.AccountDto;
import shopping.back.hj.accounts.AccountRepository;
import shopping.back.hj.accounts.AccountService;
import shopping.back.hj.common.AppProperties;
import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;
import shopping.back.hj.dress.DressRepository;
import shopping.back.hj.dress.dimages.Dimage;
import shopping.back.hj.dress.dimages.DimageRepository;
import shopping.back.hj.dress.dimages.DimageService;

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
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private DressRepository dressRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private DimageRepository dimageRepository;
	
	@Autowired
	private AppProperties appProperties;
	
	// 파일 2개
	private MockMultipartFile file1 = new MockMultipartFile("files", "test1.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test2.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	
	@Before
	public void setUp() {
		dressRepository.deleteAll();
		accountRepository.deleteAll();
		dimageRepository.deleteAll();
	}
	
	@Test
	@TestDescription("이미지 파일을 전송하는 Test")
	public void create_dimage() throws Exception {
		
		mockMvc.perform(multipart("/api/dimages")
				.file(file1)
				.file(file2)
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.param("idx", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("image_files").value(file1.getOriginalFilename()+"/"+file2.getOriginalFilename()+"/"))
		;
	}
	
	@Test
	@TestDescription("대표이미지 인덱스가 이미지 파일 길이보다 클때")
	public void create_dimage_BadRequest_WrongRepIdx() throws Exception {
		
		mockMvc.perform(multipart("/api/dimages")
				.file(file1)
				.file(file2)
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.param("idx", "123")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
		.andDo(print())
		.andExpect(status().isBadRequest())
		;
	}
	
	@Test
	@TestDescription("이미지 확장자가 아닌 파일을 전송하는 Test, ImageValidator 수행")
	public void create_dimage_BadRequest_WrongFile() throws Exception {
		
		mockMvc.perform(multipart("/api/dimages")
				.file(file1)
				.file(wrongFile)
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.param("idx", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			;
	}
	
	@Test
	@TestDescription("이미지 파일을 안보낼때")
	public void create_dimage_BadRequest_EmptyFile() throws Exception {
		
		mockMvc.perform(multipart("/api/dimages")
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.param("idx", "2")
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
		
		ResponseEntity<?> returnData = dimagesService.createDimage(files, 2);
		Dimage dimage = (Dimage) returnData.getBody();
		
		mockMvc.perform(delete("/api/dimages/{id}", dimage.getId())
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isOk())
			;
	}
	
	private String getBearerToken() throws Exception {
		return "Bearer"+getAccessToken();
	}
	
	private String getAccessToken() throws Exception {
		
		AccountDto accountDto = AccountDto.builder()
				.email(appProperties.getUserEmail())
				.password(appProperties.getUserPassword())
				.address("random")
				.phone_number("010-4732-1566")
				.birth("1994/08/23")
				.build();
		
		Account account = (Account)accountService.createAccount(accountDto).getBody();
		
		ResultActions perform = mockMvc.perform(post("/oauth/token")
				.with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
				.param("username", appProperties.getUserEmail())
				.param("password", appProperties.getUserPassword())
				.param("grant_type", "password")
				);
		
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();
		return parser.parseMap(responseBody).get("access_token").toString();
	}
	
}
