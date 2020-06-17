package shopping.back.hj.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @AllArgsConstructor
@Getter @Setter
public class ColorInfo {
	
	private DressSize dress_size;
	private Integer num;
	
}
