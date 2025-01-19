package swirn.spring.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Borrowing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private int bookId;
	
	@Column
	private int holderId;
	
	@Column(columnDefinition = "DATETIME")
	private LocalDate startDate;
	
	@Column(columnDefinition = "DATETIME")
	private LocalDate endDate;

}
