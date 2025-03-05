package swirn.spring.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table
public class Holder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String contactNumber;

	@OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
	private List<Rental> rentals;
}
