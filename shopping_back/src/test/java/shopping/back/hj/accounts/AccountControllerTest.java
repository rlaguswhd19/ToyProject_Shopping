package shopping.back.hj.accounts;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shopping.back.hj.accounts.address.Address;
import shopping.back.hj.common.AppProperties;
import shopping.back.hj.common.RestDocsConfiguration;
import shopping.back.hj.common.TestDescription;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class AccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AppProperties appProperties;
	
	@Autowired
	private AccountValidator accountValidator;
	
	@Test
	@TestDescription("정상적으로 Account를 생성하는 Test")
	public void createAccount() throws JsonProcessingException, Exception {
		
		AccountDto accountDto = generatedAccountDto();
		
		ResultActions perform = mockMvc.perform(post("/api/accounts")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE)
				.content(objectMapper.writeValueAsString(accountDto))
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").exists())
				.andDo(document("create-account",
					links(
							linkWithRel("self").description("link to self")
					),
					requestHeaders(
							headerWithName(HttpHeaders.ACCEPT).description("Accept header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					relaxedRequestFields(
							fieldWithPath("email").description("이메일"),
							fieldWithPath("password").description("비밀번호"),
							fieldWithPath("phone_number").description("전화번호"),
							fieldWithPath("birth").description("생년월일"),
							fieldWithPath("address").description("주소 객체")
					),
					responseHeaders(
							headerWithName(HttpHeaders.LOCATION).description("Location header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					relaxedResponseFields(
							fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
							fieldWithPath("birth").type(JsonFieldType.STRING).description("생년월일"),
							fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
							fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
							fieldWithPath("address").type(JsonFieldType.OBJECT).description("주소 객체"),
							fieldWithPath("phone_number").type(JsonFieldType.STRING).description("전화번호"),
							fieldWithPath("dress_arr").type(JsonFieldType.ARRAY).description("생성한 옷 목록"),
							fieldWithPath("dpage_arr").type(JsonFieldType.ARRAY).description("생성한 페이지 목록"),
							fieldWithPath("roles").type(JsonFieldType.ARRAY).description("권한")
					)
				))
				;
	}
	
	private AccountDto generatedAccountDto() {
		Address address = Address.builder()
				.post("54903")
				.road("전북 전주시 덕진구 호성로 132")
				.jibun("전북 전주시 덕진구 호성동1가 829-4")
				.building("진흥더블파크1단지아파트")
				.detail("105동 703호")
				.build();
		
		AccountDto accountDto = AccountDto.builder()
				.email("test@naver.com")
				.password("1q2w3e4r!@")
				.address(address)
				.phone_number("01047321566")
				.birth("1994/08/23")
				.build();
		
		return accountDto;
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
