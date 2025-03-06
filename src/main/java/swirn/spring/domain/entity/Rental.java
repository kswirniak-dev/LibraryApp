package swirn.spring.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


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

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(columnDefinition = "DATETIME")
	private LocalDate startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(columnDefinition = "DATETIME")
	private LocalDate endDate;

}
