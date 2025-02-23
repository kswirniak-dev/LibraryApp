package swirn.spring.domain.entity;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.annotation.Transient;

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

	@Transient
	public boolean isBorrowed() {
		return rentals.stream()
				.anyMatch( r ->  (r.getStartDate().isBefore(LocalDate.now()) && r.getEndDate() == null )
						|| r.getEndDate().isAfter(LocalDate.now()) );
	}
	@Transient
	public Rental getActiveRental() {
		Optional<Rental> rental =  rentals.stream().filter( r ->  r.getStartDate().isBefore(LocalDate.now()) && ( r.getEndDate() == null
				|| r.getEndDate().isAfter(LocalDate.now()))).findFirst();
		if (rental.isEmpty()) {
			return null;
		}
		else {
			return rental.get();
		}
	}
}
