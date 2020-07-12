package shopping.back.hj.dress;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class DressCountrollerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private DressRepository dressRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// 파일 2개
	private MockMultipartFile file1 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	@Test
	@TestDescription("정상적으로 Dress를 생성하고 등록하는 Test")
	public void createDress() throws Exception {
		DressDto dressDto = DressDto.builder()
				.brand("COVERNAT")
				.name("커버낫 반팔")
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.price(39000)
				.dress_type(DressType.Top)
				.discount(10)
				.explanation("Test")
				.image_path("/dress_images/id")
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
					requestParameters (
							parameterWithName("brand").description("브랜드"),
							parameterWithName("article_number").description("품번"),
							parameterWithName("dress_type").description("타입"),
							parameterWithName("sex").description("성별"),
							parameterWithName("price").description("가격"),
							parameterWithName("discount").description("할인율"),
							parameterWithName("explanation").description("설명")
					),
					requestParts(
							partWithName("files").description("옷 대표 이미지 업로드")
					),
					responseHeaders(
							headerWithName(HttpHeaders.LOCATION).description("Location header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					relaxedResponseFields(
							fieldWithPath("id").type(JsonFieldType.NUMBER).description("dress_id"),
							fieldWithPath("brand").type(JsonFieldType.STRING).description("브랜드"),
							fieldWithPath("article_number").type(JsonFieldType.STRING).description("품번"),
							fieldWithPath("dress_type").type(JsonFieldType.STRING).description("타입"),
							fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
							fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
							fieldWithPath("discount").type(JsonFieldType.NUMBER).description("할인율"),
							fieldWithPath("explanation").type(JsonFieldType.STRING).description("설명"),
							fieldWithPath("created_date").type(JsonFieldType.STRING).description("등록 날짜"),
							fieldWithPath("image_paths").type(JsonFieldType.STRING).description("images-dress 링크 파일 이미지들의 집합")
					)
				))
			;
	}
	
	@Test
	@TestDescription("DressDto에 없는 값들을 보내는 Test = Fail")
	public void createDress_BadRequest_UnknownProperty() throws Exception {
		Dress dress = Dress.builder()
				.id(20L)
				.brand("COVERNAT")
				.name("커버낫 반팔")
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.price(39000)
				.dress_type(DressType.Top)
				.discount(10)
				.explanation("Test")
				.build();
		
		mockMvc.perform(multipart("/api/dress")
				.file(file1)
				.file(file2)
				.param("id", dress.getId().toString())
				.param("brand", dress.getBrand())
				.param("article_number", dress.getArticle_number())
				.param("sex", dress.getSex().toString())
				.param("price", dress.getPrice().toString())
				.param("dress_type", dress.getDress_type().toString())
				.param("discount", dress.getDiscount().toString())
				.param("explanation", dress.getExplanation())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			;
	}
	
	@Test
	@TestDescription("DressDto에 비어있는 값들을 보내는 Test, Valid 수행")
	public void createDress_BadRequest_EmptyInput() throws Exception {
		
		mockMvc.perform(multipart("/api/dress")
				.file(file1)
				.file(file2)
				.param("brand", "123")
				.param("article_number", "123")
				.param("sex", "null")
				.param("price", "null")
				.param("dress_type", "null")
				.param("discount", "null")
				.param("explanation", "")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("_links.index").exists())
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
							headerWithName("accept").description("Accept header")
					),
					pathParameters(
							parameterWithName("id").description("Dress id")
					),
					links(
							linkWithRel("self").description("link to this dress"),
							linkWithRel("profile").description("link to profile")
					)
				))
		;
	}
	
	@Test
	@TestDescription("없는 Dress 조회했을때 404 응답하기")
	public void getDress_isNotFound() throws Exception {
		mockMvc.perform(get("/api/dress/{id}", 123014123)
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
		
		// 먼저 dressDto를 변경하고 그다음에 image를 변경하도록 하자.
		mockMvc.perform(put("/api/dress")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				.content(objectMapper.writeValueAsString(dressDto))
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(dressName))
		;
	}
	
	@Test
	@TestDescription("입력값이 잘못된 경우 BadRequest 응답하기")
	public void updateDress_BadRequest_WrongInput() throws Exception {
		Dress dress = generateDress(100);
		String dressName = "update dressName";
		DressDto dressDto = modelMapper.map(dress, DressDto.class);
		dressDto.setName(dressName);
		
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/api/dress/{id}", dress.getId());
		builder.with(new RequestPostProcessor() {
			
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod(RequestMethod.PUT.toString());
				return request;
			}
		});
		
		
		mockMvc.perform(builder
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
				.accept(MediaTypes.HAL_JSON + ";charset=UTF-8")
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(dressName))
		;
	}
	
	public Dress generateDress(int idx) {
		Dress dress = Dress.builder()
				.brand("test listsDress" + idx)
				.name("커버낫 반팔")
				.article_number("test listsDress")
				.sex(Sex.Public)
				.price(39000)
				.dress_type(DressType.Top)
				.discount(10)
				.explanation("test listsDress")
				.image_path("test iamge_paths" + idx)
				.created_date(LocalDateTime.now())
				.build();
		
		return dressRepository.save(dress);
	}
}
