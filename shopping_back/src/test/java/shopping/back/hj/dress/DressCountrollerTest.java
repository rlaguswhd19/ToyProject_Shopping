package shopping.back.hj.dress;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.Authenticator.RequestorType;

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
	
	// 파일 2개
	private MockMultipartFile file1 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	private MockMultipartFile file2 = new MockMultipartFile("files", "test.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "some jpg".getBytes());
	
	@Test
	@TestDescription("정상적으로 Dress를 생성하고 등록하는 Test")
	public void createDress() throws Exception {
		DressDto dressDto = DressDto.builder()
				.brand("COVERNAT")
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.sale(39000)
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
				.param("sale", dressDto.getSale().toString())
				.param("dress_type", dressDto.getDress_type().toString())
				.param("discount", dressDto.getDiscount().toString())
				.param("explanation", dressDto.getExplanation())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON)
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
							linkWithRel("image-dress").description("dress image file path")
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
							parameterWithName("sale").description("가격"),
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
							fieldWithPath("id").description("dress_id"),
							fieldWithPath("brand").description("브랜드"),
							fieldWithPath("article_number").description("품번"),
							fieldWithPath("dress_type").description("타입"),
							fieldWithPath("sex").description("성별"),
							fieldWithPath("sale").description("가격"),
							fieldWithPath("discount").description("할인율"),
							fieldWithPath("explanation").description("설명"),
							fieldWithPath("image_path").description("이미지 경로"),
							fieldWithPath("created_date").description("등록 날짜"),
							fieldWithPath("_links.self.href").description("link to self"),
							fieldWithPath("_links.update-dress.href").description("link to get dress lists"),
							fieldWithPath("_links.lists-dress.href").description("link to update an existing dress"),
							fieldWithPath("_links.profile.href").description("link to profile"),
							fieldWithPath("_links.image-dress.href").description("dress image file path")
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
				.sale(39000)
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
				.param("sale", dress.getSale().toString())
				.param("dress_type", dress.getDress_type().toString())
				.param("discount", dress.getDiscount().toString())
				.param("explanation", dress.getExplanation())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON)
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
				.param("sale", "null")
				.param("dress_type", "null")
				.param("discount", "null")
				.param("explanation", "")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON)
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
				.sale(0)
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
				.param("sale", dressDto.getSale().toString())
				.param("dress_type", dressDto.getDress_type().toString())
				.param("discount", dressDto.getDiscount().toString())
				.param("explanation", dressDto.getExplanation())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaTypes.HAL_JSON)
				)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("content[0].objectName").exists())
			.andExpect(jsonPath("content[0].defaultMessage").exists())
			.andExpect(jsonPath("content[0].code").exists())
			.andExpect(jsonPath("_links.index").exists())
			;
	}
}
