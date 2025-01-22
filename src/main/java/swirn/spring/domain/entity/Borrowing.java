package swirn.spring.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table
public class Borrowing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@OneToOne
	@JoinColumn(name = "holder_id")
	private Holder holder;
	
	@Column(columnDefinition = "DATETIME")
	private LocalDate startDate;
	
	@Column(columnDefinition = "DATETIME")
	private LocalDate endDate;

}
