package swirn.spring.dto.details;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import swirn.spring.dto.BookDTO;
import swirn.spring.dto.HolderDTO;

@Getter @Setter
public class RentalDetailsDTO {
	    private Long id;
	    private BookDTO book;
	    private HolderDTO holder;
	    private LocalDate startDate;
	    private LocalDate endDate;
}