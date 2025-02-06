package swirn.spring.domain.entity;

import java.time.Year;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table
public class Book {
	
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
	private Long id;
	
	@Column(nullable = false)
	@Getter @Setter
	private String title;
	
	@Column
	@Getter @Setter
	private String author;
	
	@Column(columnDefinition = "YEAR", nullable = false)
	@Getter @Setter
	private Year year;
	
	@Column
	@OneToMany(mappedBy = "book")
	@Getter @Setter
	private List<Rental> rentals;

}
