package swirn.spring.dto;

import java.time.Year;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDTO {
	    private Long id;
	    private String title;
	    private String author;
	    private Year year;
	    private List<RentalDTO> rentals;
		private boolean isBorrowed;
}

