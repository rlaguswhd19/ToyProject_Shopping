package shopping.back.hj.pages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shopping.back.hj.dress.DressSize;

@Builder @AllArgsConstructor
@Getter @Setter
public class ColorInfo {
	
	private DressSize dress_size;
	private Integer num;
	
}
