package swirn.spring.domain.entity;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.*;
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
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	@Getter @Setter
	private List<Rental> rentals;

	@Transient
	public boolean isBorrowed() {
		return rentals.stream()
				.anyMatch( r ->  (r.getStartDate().isBefore(LocalDate.now()) && r.getEndDate() == null )
						|| r.getEndDate().isAfter(LocalDate.now()) );
	}
	@Transient
	public Optional<Rental> getActiveRental() {
		if (rentals == null) {
			return Optional.empty();
		}

		return rentals.stream()
				.filter(r -> r.getStartDate().isBefore(LocalDate.now().plusDays(1))
						&& (r.getEndDate() == null || r.getEndDate().isAfter(LocalDate.now())))
				.findFirst();
	}

}
