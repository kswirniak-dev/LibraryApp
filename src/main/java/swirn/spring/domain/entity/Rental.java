package swirn.spring.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor
@Table
public class Rental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "book_id")
	private Book book;
	
	@ManyToOne( cascade ={ CascadeType.MERGE, CascadeType.PERSIST} )
	@JoinColumn(name = "holder_id")
	private Holder holder;
	
	@Column(columnDefinition = "DATETIME")
	private LocalDate startDate;
	
	@Column(columnDefinition = "DATETIME")
	private LocalDate endDate;

}
