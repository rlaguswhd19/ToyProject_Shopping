package shopping.back.hj.dress;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.relaxedLinks;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;
import shopping.back.hj.dimages.Dimage;
import shopping.back.hj.dimages.DimageRepository;
import shopping.back.hj.enums.DressType;
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
	
	// 파일 2개
	private MockMultipartFile file1 = new MockMultipartFile("files", "test1.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test2.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	@Test
	@TestDescription("정상적으로 Dress를 생성하고 등록하는 Test")
	public void createDress() throws Exception {
		Dimage dimage = Dimage.builder()
				.image_files("test.jpg")
				.build();
		
		Dimage newDimage = dimageRepository.save(dimage);
		
		DressDto dressDto = DressDto.builder()
				.brand("COVERNAT")
				.name("커버낫 반팔")
				.article_number("C1804SL01WH")
				.sex(Sex.Men)
				.price(39000L)
				.dress_type(DressType.Top)
				.discount(10)
				.explanation("Test")
				.dimage_id(newDimage.getId())
				.build();
		
		mockMvc.perform(post("/api/dress")
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
							headerWithName(HttpHeaders.ACCEPT).description("Accept header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					requestFields (
							fieldWithPath("brand").description("브랜드"),
							fieldWithPath("name").description("이름"),
							fieldWithPath("article_number").description("품번"),
							fieldWithPath("dress_type").description("타입"),
							fieldWithPath("sex").description("성별"),
							fieldWithPath("price").description("가격"),
							fieldWithPath("discount").description("할인율"),
							fieldWithPath("explanation").description("설명"),
							fieldWithPath("dimage_id").description("이미지 id")
					),
					responseHeaders(
							headerWithName(HttpHeaders.LOCATION).description("Location header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					relaxedResponseFields(
							fieldWithPath("id").type(JsonFieldType.NUMBER).description("dress_id"),
							fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
							fieldWithPath("brand").type(JsonFieldType.STRING).description("브랜드"),
							fieldWithPath("article_number").type(JsonFieldType.STRING).description("품번"),
							fieldWithPath("dress_type").type(JsonFieldType.STRING).description("타입"),
							fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
							fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
							fieldWithPath("discount").type(JsonFieldType.NUMBER).description("할인율"),
							fieldWithPath("explanation").type(JsonFieldType.STRING).description("설명"),
							fieldWithPath("created_date").type(JsonFieldType.STRING).description("등록 날짜"),
							fieldWithPath("dimage").type(JsonFieldType.OBJECT).description("이미지 Entity FK")
					)
				))
			;
	}
	
	@Test
	@TestDescription("DressDto에 없는 값들을 보내는 Test")
	public void createDress_BadRequest_UnknownProperty() throws Exception {
		Dress dress = generateDress(100);
		
		mockMvc.perform(post("/api/dress")
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
		Long dimage_id = dress.getDimage().getId();
		String dressName = "update dressName";
		
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.setDimage_id(dimage_id);
		dressDto.setName(dressName);
		
		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/dress/{id}", dress.getId())
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
							linkWithRel("profile").description("link to profile")
					),
					pathParameters(
							parameterWithName("id").description("Dress id")
					),
					requestHeaders(
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
							headerWithName(HttpHeaders.ACCEPT).description("Accept header")
					)
			))
		;
	}
	
	@Test
	@TestDescription("존재하지 않는 Dress 수정하기")
	public void updateDress_isNotFoundDress() throws Exception {
		Dress dress = generateDress(100);
		Long dimage_id = dress.getDimage().getId();
		String dressName = "update dressName";
		
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.setDimage_id(dimage_id);
		dressDto.setName(dressName);
		
		mockMvc.perform(put("/api/dress/{id}", 12341141L)
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
		dressDto.setDimage_id(2031423L);
		dressDto.setName(dressName);
		
		mockMvc.perform(put("/api/dress/{id}", dress.getId())
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
		dressDto.setDimage_id(dimage_id);
		dressDto.setName(dressName);
		dressDto.setDiscount(121);
		
		mockMvc.perform(put("/api/dress/{id}", dress.getId())
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
		Long dimage_id = dress.getDimage().getId();
		String dressName = "update dressName";
		
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.setDimage_id(dimage_id);
		dressDto.setName(dressName);
		dressDto.setPrice(0L);
		dressDto.setDiscount(100);
		
		mockMvc.perform(put("/api/dress/{id}", dress.getId())
				.content(objectMapper.writeValueAsString(dressDto))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}
	
	public Dress generateDress(int idx) {
		Dimage dimage = Dimage.builder()
				.image_files("test" + idx + ".jpg")
				.build();
		
		Dimage newDimage = dimageRepository.save(dimage);
		
		Dress dress = Dress.builder()
				.brand("test listsDress" + idx)
				.name("커버낫 반팔")
				.article_number("test listsDress")
				.sex(Sex.Free)
				.price(39000L)
				.dress_type(DressType.Top)
				.discount(10)
				.explanation("test listsDress")
				.created_date(LocalDate.now())
				.dimage(newDimage)
				.build();
		
		return dressRepository.save(dress);
	}
}
