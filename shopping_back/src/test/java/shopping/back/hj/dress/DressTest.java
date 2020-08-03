package shopping.back.hj.dress;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import shopping.back.hj.enums.DressType;
import shopping.back.hj.enums.Sex;

@RunWith(JUnitParamsRunner.class)
public class DressTest {
	
	@Test
	@Parameters(method = "paramsForTest")
	public void builder(String brand, String article_num, Sex sex, Long price, DressType dresstype, Integer discount) {
		Dress dress = Dress.builder()
				.brand(brand)
				.article_number(article_num)
				.sex(sex)
				.price(price)
				.dress_type(dresstype)
				.discount(discount)
				.build();
		
		assertThat(dress).isNotNull();
		
	}
	
	private Object[] paramsForTest() {
		return new Object[] {
			new Object[] {"COVERNAT","C1804SL01WH",Sex.Men,39000,DressType.Top,10},
			new Object[] {"MILLIONCOR","239961",Sex.Women,43000,DressType.Bottom,15},
		};
	}
}
