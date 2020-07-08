package shopping.back.hj.dress;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.Authenticator.RequestorType;
import java.time.LocalDateTime;

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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	
	// 파일 2개
	private MockMultipartFile file1 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	private MockMultipartFile wrongFile = new MockMultipartFile("files", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "some txt".getBytes());
	@Test
	@TestDescription("정상적으로 Dress를 생성하고 등록하는 Test")
	public void createDress() throws Exception {
		DressDto dressDto = DressDto.builder()
				.brand("COVERNAT")
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.price(39000)
				.dress_type(DressType.top)
				.discount(10)
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
					responseFields(
							fieldWithPath("id").type(JsonFieldType.NUMBER).description("dress_id"),
							fieldWithPath("brand").type(JsonFieldType.STRING).description("브랜드"),
							fieldWithPath("article_number").type(JsonFieldType.STRING).description("품번"),
							fieldWithPath("dress_type").type(JsonFieldType.STRING).description("타입"),
							fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
							fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
							fieldWithPath("discount").type(JsonFieldType.NUMBER).description("할인율"),
							fieldWithPath("explanation").type(JsonFieldType.STRING).description("설명"),
							fieldWithPath("created_date").type(JsonFieldType.STRING).description("등록 날짜"),
							fieldWithPath("image_paths").type(JsonFieldType.STRING).description("images-dress 링크 파일 이미지들의 집합"),
							fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("link to self"),
							fieldWithPath("_links.update-dress.href").type(JsonFieldType.STRING).description("link to get dress lists"),
							fieldWithPath("_links.lists-dress.href").type(JsonFieldType.STRING).description("link to update an existing dress"),
							fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("link to profile"),
							fieldWithPath("_links.images-dress.href").type(JsonFieldType.STRING).description("dress image files path")
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
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.price(39000)
				.dress_type(DressType.top)
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
	@TestDescription("DressDto의 로직에 맞지 않는 값들을 보내는 Test, DressValidator 수행")
	public void createDress_BadRequest_WrongInput() throws Exception {
		DressDto dressDto = DressDto.builder()
				.brand("COVERNAT")
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.price(0)
				.dress_type(DressType.top)
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
				.dress_type(DressType.top)
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
	
	public void generateDress(int idx) {
		Dress dress = Dress.builder()
				.brand("test listsDress" + idx)
				.article_number("test listsDress")
				.sex(Sex.Public)
				.price(39000)
				.dress_type(DressType.top)
				.discount(10)
				.explanation("test listsDress")
				.image_paths("test iamge_paths" + idx)
				.created_date(LocalDateTime.now())
				.build();
		
		Dress newDress = dressRepository.save(dress);
		System.out.println(newDress);
	}
}
