package swirn.spring.dto.simple;

import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public class BookSimpleDTO {
    private Long id;
    private String title;
    private String author;
    private Year year;
}
