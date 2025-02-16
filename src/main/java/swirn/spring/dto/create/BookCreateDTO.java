package swirn.spring.dto.create;

import java.time.Year;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookCreateDTO {
	    private String title;
	    private String author;
	    private Year year;
}