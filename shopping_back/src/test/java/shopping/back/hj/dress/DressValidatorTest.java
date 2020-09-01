package shopping.back.hj.dress;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shopping.back.hj.accounts.Account;
import shopping.back.hj.accounts.AccountDto;
import shopping.back.hj.accounts.AccountRepository;
import shopping.back.hj.accounts.AccountService;
import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;
import shopping.back.hj.dress.dimages.Dimage;
import shopping.back.hj.dress.dimages.DimageRepository;
import shopping.back.hj.dress.dsize.Dsize;
import shopping.back.hj.enums.DressCategory;
import shopping.back.hj.enums.DressColor;
import shopping.back.hj.enums.DressSize;
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
	private AccountService accountService;
	
	@Autowired
	private DimageRepository dimageRepository;
	
	@Autowired
	private DressRepository dressRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	private MockMultipartFile file1 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	
	@Before
	public void setUp() {
		dressRepository.deleteAll();
		accountRepository.deleteAll();
		dimageRepository.deleteAll();
	}
	
	@Test
	@TestDescription("DressDto에 비어있는 값들을 보내는 Test, Valid 수행")
	public void createDress_BadRequest_EmptyInput() throws Exception {
		DressDto dressDto = DressDto.builder().build();
		
		mockMvc.perform(post("/api/dress")
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
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
				.name("Test")
				.article_number("C1804SL01WH")
				.sex(Sex.Men)
				.price(0L)
				.category(DressCategory.Top)
				.discount(100)
				.color(DressColor.BLACK)
				.explanation("Test")
				.dimage(dimage)
				.dsize(dsize)
				.material("겉면: 100%")
				.origin("한국")
				.manufacture("2020-03")
				.build();
		
		mockMvc.perform(post("/api/dress")
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
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
				.info(100)
				.height(85)
				.width(50)
				.count(1)
				.build();
		Dsize d2 = Dsize.builder()
				.size(DressSize.M)
				.info(90)
				.height(80)
				.width(40)
				.count(2)
				.build();
		Dsize d3 = Dsize.builder()
				.size(DressSize.S)
				.info(80)
				.height(80)
				.width(30)
				.count(3)
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
	
	private String getBearerToken() throws Exception {
		return "Bearer"+getAccessToken();
	}
	
	private String getAccessToken() throws Exception {
		String useremail = "random@naver.com";
		String password = "random";
		
		AccountDto accountDto = AccountDto.builder()
				.email(useremail)
				.password(password)
				.address("random")
				.phone_number("010-4732-1566")
				.birth("1994/08/23")
				.build();
		
		Account account = (Account)accountService.createAccount(accountDto).getBody();
		
		String clientId = "hjapp";
		String clientSecret = "hjpass";
		
		ResultActions perform = mockMvc.perform(post("/oauth/token")
				.with(httpBasic(clientId, clientSecret))
				.param("username", useremail)
				.param("password", password)
				.param("grant_type", "password")
				);
		
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();
		return parser.parseMap(responseBody).get("access_token").toString();
	}
}
