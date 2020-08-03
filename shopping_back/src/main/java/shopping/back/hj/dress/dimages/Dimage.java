package shopping.back.hj.dress.dimages;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity @EqualsAndHashCode(of = "id")
public class Dimage {
	
	@Id @GeneratedValue
	private Long id;
	
	private String image_files;
	
	private Integer image_repIdx;

	@Override
	public String toString() {
		return "Dimage [id=" + id + ", image_files=" + image_files + ", image_repIdx=" + image_repIdx + "]";
	}
	
}
