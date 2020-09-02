package shopping.back.hj.dress.dsize;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.back.hj.enums.DressSize;

@Entity @Builder
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
@EqualsAndHashCode(of = {"size", "id"})
public class Dsize {

	@GeneratedValue
	@Id
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private DressSize size;
	
	@NotNull
	@Min(1)
	@Max(200)
	private Integer info;
	
	@NotNull
	@Min(1)
	@Max(300)
	private Integer width;
	
	@NotNull
	@Min(1)
	@Max(300)
	private Integer height;
	
	@NotNull
	@Min(1)
	private Integer count;

	@Override
	public String toString() {
		return "Dsize [id=" + id + ", size=" + size + ", width=" + width + ", height=" + height + "]";
	}
}
