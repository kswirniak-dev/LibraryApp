package swirn.spring.dto;

import java.time.Year;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDTO {
	    private Long id;
	    private String title;
	    private String author;
	    private Year year;
}

