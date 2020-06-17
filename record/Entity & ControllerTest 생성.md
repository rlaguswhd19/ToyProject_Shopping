SpringBoot Shopping Entity 생성



```java
@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id") 
// 비교할때 모든것을 비교하는것이 아닌 id로 비교하는것 stackoverflow 방지
public class Account {
	
	@Id @GeneratedValue
	private Integer id;
	
	private Integer age;
	
	private String email;
	
	private String password;
	
	private String address;
	
	private String phone_number;
	
	// 찜 목록 (FK) 찜목록을 옷 id로 Set 생성
	@OneToMany 
	private Set<Dress> dress_arr;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING) 
	//enum이 기본은 index로 되어있어 나중에 enum 순서가 바뀌면 혼란이 올수 있음 String값으로 지정
	private Set<AccountRole> role;
}
```



Test를 생성하는 방법

@RunWith (SpringBoot.class) :  JUnit 프레임워크가 내장된 Runner를 실행할 때 SpringRunner.class라는 확장된 클래스를 실행 하기 위해서 사용

@SpringbootTest : 스프링 부트 어플리케이션 테스트 시 테스트에 필요한 거의 모든 의존성을 제공

@AutoConfigureMockMvc는  Mock 테스트 시 필요한 의존성 제공



#### 예시

```java
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class DressCountrollerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void createDress() throws Exception {
		Dress dress = Dress.builder()
				.brand("COVERNAT")
				.article_number("C1804SL01WH")
				.sex(Sex.Man)
				.sale(39000)
				.dress_type(DressType.top)
				.discount(10)
				.explanation("Test")
				.build();
		
		mockMvc.perform(post("/api/dress")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(dress))
				)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(header().exists(HttpHeaders.LOCATION))
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON.toString()+";charset=UTF-8"))
			;
	}
```

