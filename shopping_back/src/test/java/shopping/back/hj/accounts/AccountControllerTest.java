package shopping.back.hj.accounts;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.restdocs.payload.JsonFieldType;
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
import shopping.back.hj.enums.Sex;

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
	private ModelMapper modelMapper;
	
	@Autowired
	private AppProperties appProperties;
	
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
							fieldWithPath("address").description("주소 객체"),
							fieldWithPath("sex").description("성별")
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
							fieldWithPath("roles").type(JsonFieldType.ARRAY).description("권한"),
							fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
							fieldWithPath("dorder_arr").type(JsonFieldType.ARRAY).description("주문 내역")
					)
				))
				;
	}
	
	@Test
	@TestDescription("Account 계정 정보 수정 Test")
	public void updateAccount() throws JsonProcessingException, Exception {
		String email = appProperties.getUserEmail();
		ResultActions perform = mockMvc.perform(get("/api/accounts/{email}", email)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE)
				);
		
		perform
			.andExpect(jsonPath("id").value(3));
		
		
		String newAddress = "Test 주소";
		String newPhone = "01097012309";
		
		Account account = objectMapper.readValue(perform.andReturn().getResponse().getContentAsString(), Account.class);
		AccountDto accountDto = modelMapper.map(account, AccountDto.class);
		Address address = accountDto.getAddress();
		address.setBuilding(newAddress);
		accountDto.setAddress(address);
		accountDto.setBirth("1997/01/23");
		accountDto.setPhone_number(newPhone);
		
		ResultActions perform_update = mockMvc.perform(put("/api/accounts")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE)
				.content(objectMapper.writeValueAsString(accountDto))
				.header(HttpHeaders.AUTHORIZATION, getBearerToken())
				);
		
		perform_update
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("address.building").value(newAddress))
			.andExpect(jsonPath("phone_number").value("010-9701-2309"))
			;
	}
	
	
	@Test
	@TestDescription("email을 활용해 id를 가져오는 Test")
	public void findByEmail() throws Exception {
		String email = appProperties.getUserEmail();
		
		mockMvc.perform(get("/api/accounts/{email}", email)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_VALUE)
				)
				.andDo(print())
				.andExpect(jsonPath("id").value(3))
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
				.sex(Sex.Men)
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
	
	@Test
	@TestDescription("refresh_token을 활용하여 access_token 재발급 Test")
	public void getAccessTokenByRefreshToken() throws Exception {
		
		ResultActions perform = mockMvc.perform(post("/oauth/token")
				.with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
				.param("username", appProperties.getUserEmail())
				.param("password", appProperties.getUserPassword())
				.param("grant_type", "password")
				);
		
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();
		String reefresh_token = parser.parseMap(responseBody).get("refresh_token").toString();
		
		mockMvc.perform(post("/oauth/token")
				.with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
				.param("refresh_token", reefresh_token)
				.param("grant_type", "refresh_token")
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("access_token").exists())
				.andExpect(jsonPath("expires_in").exists())
				;
	}
}
