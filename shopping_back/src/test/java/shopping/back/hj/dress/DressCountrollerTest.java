package shopping.back.hj.dress;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.relaxedLinks;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
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
import shopping.back.hj.common.AppProperties;
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
public class DressCountrollerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private DressRepository dressRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private DimageRepository dimageRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AppProperties appProperties;
	
	@Before
	public void setUp() {
		dressRepository.deleteAll();
		dimageRepository.deleteAll();
	}
	
	// 파일 2개
	private MockMultipartFile file1 = new MockMultipartFile("files", "test1.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());;
	private MockMultipartFile file2 = new MockMultipartFile("files", "test2.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());;
	private Set<Dsize> dsize = createDsize();
	
	@Test
	@TestDescription("정상적으로 Dress를 생성하고 등록하는 Test")
	public void createDress() throws Exception {
		Dimage dimage = Dimage.builder()
				.image_files("test.jpg")
				.build();
		
		Dimage newDimage = dimageRepository.save(dimage);
		
		DressDto dressDto = DressDto.builder()
				.name("커버낫 반팔")
				.article_number("C1804SL01WH")
				.sex(Sex.Men)
				.price(39000L)
				.color(DressColor.BLACK)
				.category(DressCategory.Top)
				.discount(10)
				.explanation("Test")
				.dimage(newDimage)
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
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(header().exists(HttpHeaders.LOCATION))
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON.toString()+";charset=UTF-8"))
			.andDo(document("create-dress",
					links(
							linkWithRel("self").description("link to self"),
							linkWithRel("lists-dress").description("link to get dress lists"),
							linkWithRel("update-dress").description("link to update an existing dress"),
							linkWithRel("profile").description("link to profile"),
							linkWithRel("images-dress").description("dress image file paths")
					),
					requestHeaders(
							headerWithName(HttpHeaders.AUTHORIZATION).description("Access token"),
							headerWithName(HttpHeaders.ACCEPT).description("Accept header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					relaxedRequestFields (
							fieldWithPath("name").description("이름"),
							fieldWithPath("article_number").description("품번"),
							fieldWithPath("category").description("카테고리"),
							fieldWithPath("sex").description("성별"),
							fieldWithPath("color").description("색상"),
							fieldWithPath("price").description("가격"),
							fieldWithPath("discount").description("할인율"),
							fieldWithPath("explanation").description("설명"),
							fieldWithPath("dimage").description("이미지"),
							fieldWithPath("dsize").description("사이즈 info"),
							fieldWithPath("material").description("소재"),
							fieldWithPath("origin").description("제조국"),
							fieldWithPath("manufacture").description("제조년월")
					),
					responseHeaders(
							headerWithName(HttpHeaders.LOCATION).description("Location header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					relaxedResponseFields(
							fieldWithPath("id").type(JsonFieldType.NUMBER).description("dress_id"),
							fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
							fieldWithPath("article_number").type(JsonFieldType.STRING).description("품번"),
							fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
							fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
							fieldWithPath("color").type(JsonFieldType.STRING).description("색상"),
							fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
							fieldWithPath("discount").type(JsonFieldType.NUMBER).description("할인율"),
							fieldWithPath("discount_price").type(JsonFieldType.NUMBER).description("실제가격"),
							fieldWithPath("explanation").type(JsonFieldType.STRING).description("설명"),
							fieldWithPath("created").type(JsonFieldType.STRING).description("등록 날짜"),
							fieldWithPath("dimage").type(JsonFieldType.OBJECT).description("이미지 FK"),
							fieldWithPath("dsize").type(JsonFieldType.ARRAY).description("사이즈 info"),
							fieldWithPath("material").type(JsonFieldType.STRING).description("소재"),
							fieldWithPath("origin").type(JsonFieldType.STRING).description("제조국"),
							fieldWithPath("manufacture").type(JsonFieldType.STRING).description("제조년월")
					)
				))
			;
	}
	
	@Test
	@TestDescription("DressDto에 없는 값들을 보내는 Test")
	public void createDress_BadRequest_UnknownProperty() throws Exception {
		Dress dress = generateDress(100);
		
		mockMvc.perform(post("/api/dress")
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.content(objectMapper.writeValueAsString(dress))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			;
	}
	
	@Test
	@TestDescription("30개의 Dress를 5개씩 2번째 페이지 조회하기")
	public void listsDress() throws Exception {
		for (int i = 0; i < 25; i++) {
			generateDress(i);
		}
		
		mockMvc.perform(get("/api/dress")
				.param("page", "1")
				.param("size", "5")
				.param("sort", "id,desc")
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("_links").exists())
			.andExpect(jsonPath("page").exists())
			.andExpect(jsonPath("_embedded.dressList[0]._links.self").exists())
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(document("lists-dress", 
					requestParameters(
							parameterWithName("size").description("page to retrieve, default is 0"),
							parameterWithName("page").description("size of the page to retrieve, default 20"),
							parameterWithName("sort").description("sort to the page to retrieve")
					),
					responseHeaders(
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					relaxedLinks(
							linkWithRel("self").description("link to self"),
							linkWithRel("profile").description("link to profile")
					),
					relaxedResponseFields(
							fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("The number of this page."),
		                    fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("The size of this page."),
		                    fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("The total number of pages."),
		                    fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("The total number of results.")
		            )
			))
		;
	}

	@Test
	@TestDescription("한개의 Dress 조회하기")
	public void getDress() throws Exception {
		Dress dress = generateDress(100);
		
		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/dress/{id}", dress.getId())
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("id").value(dress.getId()))
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(print())
			.andDo(document("get-dress",
					requestHeaders(
							headerWithName(HttpHeaders.ACCEPT).description("Accept header")
					),
					pathParameters(
							parameterWithName("id").description("Dress id")
					),
					links(
							linkWithRel("self").description("link to this dress"),
							linkWithRel("profile").description("link to profile"),
							linkWithRel("images-dress").description("link to image_files")
					)
				))
		;
	}
	
	@Test
	@TestDescription("없는 Dress 조회했을때 404 응답하기")
	public void getDress_isNotFoundDress() throws Exception {
		Dress dress = generateDress(100);
		
		mockMvc.perform(get("/api/dress/{id}", 12314123)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			// 404
			.andExpect(status().isNotFound())
			.andDo(print())
		;
	}
	
	@Test
	@TestDescription("Dress를 정상적으로 수정하기")
	public void updateDress() throws Exception {
		Dress dress = generateDress(100);
		String dressName = "update dressName";
		
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.setName(dressName);
		
		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/dress/{id}", dress.getId())
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(dressName))
			.andDo(document("update-dress",
					links(
							linkWithRel("self").description("link to self"),
							linkWithRel("profile").description("link to profile"),
							linkWithRel("images-dress").description("link to Dimage")
					),
					pathParameters(
							parameterWithName("id").description("Dress id")
					),
					requestHeaders(
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
							headerWithName(HttpHeaders.ACCEPT).description("Accept header")
					),
					relaxedRequestFields (
							fieldWithPath("name").description("이름"),
							fieldWithPath("article_number").description("품번"),
							fieldWithPath("category").description("카테고리"),
							fieldWithPath("sex").description("성별"),
							fieldWithPath("color").description("색상"),
							fieldWithPath("price").description("가격"),
							fieldWithPath("discount").description("할인율"),
							fieldWithPath("explanation").description("설명"),
							fieldWithPath("dimage").description("이미지"),
							fieldWithPath("dsize").description("사이즈 info"),
							fieldWithPath("material").description("소재"),
							fieldWithPath("origin").description("제조국"),
							fieldWithPath("manufacture").description("제조년월")
					)
			))
		;
	}
	
	@Test
	@TestDescription("존재하지 않는 Dress 수정하기")
	public void updateDress_isNotFoundDress() throws Exception {
		Dress dress = generateDress(100);
		String dressName = "update dressName";
		
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.setName(dressName);
		
		mockMvc.perform(put("/api/dress/{id}", 12341141L)
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isNotFound())
		;
	}
	
	@Test
	@TestDescription("Dimage_id가 잘못된 경우 BadRequest 응답하기")
	public void updateDress_isNotFoundDimage() throws Exception {
		Dress dress = generateDress(100);
		String dressName = "update dressName";
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.getDimage().setId(2302132031013L);
		dressDto.setName(dressName);
		
		mockMvc.perform(put("/api/dress/{id}", dress.getId())
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isNotFound())
		;
	}
	
	@Test
	@TestDescription("입력값이 잘못된 경우 BadRequest 응답하기 Valid 수행")
	public void updateDress_BadRequest_WrongInput_Valid() throws Exception {
		Dress dress = generateDress(100);
		Long dimage_id = dress.getDimage().getId();
		String dressName = "update dressName";
		
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.setName(dressName);
		dressDto.setDiscount(121);
		
		mockMvc.perform(put("/api/dress/{id}", dress.getId())
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}
	
	@Test
	@TestDescription("입력값이 잘못된 경우 BadRequest 응답하기 DressValidator 수행")
	public void updateDress_BadRequest_WrongInput_Validator() throws Exception {
		Dress dress = generateDress(100);
		String dressName = "update dressName";
		
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.setName(dressName);
		dressDto.setPrice(0L);
		dressDto.setDiscount(100);
		
		mockMvc.perform(put("/api/dress/{id}", dress.getId())
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}
	
	@Test
	@TestDescription("입력값이 비어있는 경우 BadRequest 응답하기")
	public void updateDress_BadRequest_Empty() throws Exception {
		Dress dress = generateDress(100);
		DressDto dressDto = DressDto.builder().build();
		
		mockMvc.perform(put("/api/dress/{id}", dress.getId())
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}
	
	public Set<Dsize> createDsize() {
		Set<Dsize> dsize = new HashSet<>();
		
		Dsize d1 = Dsize.builder()
				.size(DressSize.L)
				.info(100)
				.height(100)
				.width(50)
				.count(1)
				.build();
		Dsize d2 = Dsize.builder()
				.size(DressSize.M)
				.info(90)
				.height(90)
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
	
	
	public Dress generateDress(int idx) {
		Dimage dimage = Dimage.builder()
				.image_files("test" + idx + ".jpg")
				.image_repIdx(0)
				.build();
		Dimage newDimage = dimageRepository.save(dimage);
		
		Set<Dsize> dsize = createDsize();
		
		Dress dress = Dress.builder()
				.name("커버낫 반팔")
				.article_number("test listsDress")
				.sex(Sex.Free)
				.price(39000L)
				.color(DressColor.BLACK)
				.category(DressCategory.Top)
				.discount(10)
				.explanation("test listsDress")
				.created(LocalDate.now())
				.dimage(newDimage)
				.dsize(dsize)
				.material("겉면: 100%")
				.origin("한국")
				.manufacture(LocalDate.of(2020, 3, 1))
				.build();
		
		return dressRepository.save(dress);
	}
	
	private String getBearerToken() throws Exception {
		return "Bearer"+getAccessToken();
	}
	
	private String getAccessToken() throws Exception {
		
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
