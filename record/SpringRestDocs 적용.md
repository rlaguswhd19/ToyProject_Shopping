#### SpringRestDocs 적용



Springboot의 경우 @AutoConfigureRestDocs의 애노테이션 하나로 적용할 수 있다. 적용하고 싶은 Test위에 애노테이션을 붙여준다.



그리고 andDo(document("폴더명")) 으로 생성한다. 

#### createDress

```java
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
		
		mockMvc.perform(post("/api/dress")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(dressDto))
				)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(header().exists(HttpHeaders.LOCATION))
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON.toString()+";charset=UTF-8"))
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.lists-dress").exists())
			.andExpect(jsonPath("_links.update-dress").exists())
			.andDo(document("create-dress"))
			;
	}
```



이것을 실행하면 이런식으로 요청과 응답에 대한 폴더가 생성된다. 하지만 이쁘게 보이지 않는다. json이 formatting이 되어 있지 않아 RestDocs에 대한 커스터마이징을 해야한다

 <img src=".\asset\6-RestDocs_generated_snippet.JPG" alt="6-RestDocs_generated_snippet"  />



test에 common이란 Package를 만들어서 RestDocsConfiguration이란 class를 만들었다.

```java
package shopping.back.hj.common;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
@TestConfiguration
public class RestDocsConfiguration {

	@Bean
	public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
		return new RestDocsMockMvcConfigurationCustomizer() {
			
			@Override
			public void customize(MockMvcRestDocumentationConfigurer configurer) {
				configurer.operationPreprocessors()
					.withResponseDefaults(prettyPrint())
					.withRequestDefaults(prettyPrint());
			}
		};
	}

}
```



prettyPrint()가 자동으로 import되지 않기 때문에 찾는데 힘들었다.

응답과 요청에 대해 prettyPrint()하면 json이 이쁘게 formatting되어 나온다.



이제 기본적인것 말고 추가적으로 문서화를 진행한다.

* 요청헤더
* 요청필드

* 응답헤더
* 응답필드
* 링크
  * self
  * lists-dress
  * update-dress



Hateoas를 import할때 잘 안됀다 이것들을 import한다.

```java
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
```

```java
.andDo(document("create-dress",
					links(
							linkWithRel("self").description("link to self"),
							linkWithRel("lists-dress").description("link to get dress lists"),
							linkWithRel("update-dress").description("link to update an existing dress")
					),
					requestHeaders(
							headerWithName(HttpHeaders.ACCEPT).description("Accept header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					requestFields(
							fieldWithPath("brand").description("dress brand"),
							fieldWithPath("article_number").description("dress number"),
							fieldWithPath("dress_type").description("dress type"),
							fieldWithPath("sex").description("dress sex type"),
							fieldWithPath("sale").description("dress price"),
							fieldWithPath("discount").description("dress discount"),
							fieldWithPath("explanation").description("dress explanation")
					),
					responseHeaders(
							headerWithName(HttpHeaders.LOCATION).description("Location header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
					),
					responseFields(
							fieldWithPath("id").description("dress id"),
							fieldWithPath("brand").description("dress brand"),
							fieldWithPath("article_number").description("dress number"),
							fieldWithPath("dress_type").description("dress type"),
							fieldWithPath("sex").description("dress sex type"),
							fieldWithPath("sale").description("dress price"),
							fieldWithPath("discount").description("dress discount"),
							fieldWithPath("explanation").description("dress explanation"),
							fieldWithPath("_links.self.href").description("dress explanation"),
							fieldWithPath("_links.update-dress.href").description("dress explanation"),
							fieldWithPath("_links.lists-dress.href").description("dress explanation")
					)
				))
			;
```



ResponseField를 작성할때 위에서 links를 작성했지만 field에서 links가 걸린다. 이럴때는 Relaxed를 쓸수 있지만 나중에 바뀔때 정확한 Docs를 만들수 없어 다 쓰는걸 권장한다.



Relaxed 접두어

* 장점: 문서의 일부분만 테스트 할 수 있다.
* 단점: 정확한 문서를 생성하지 못한다.



