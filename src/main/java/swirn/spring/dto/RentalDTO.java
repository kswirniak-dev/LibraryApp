package swirn.spring.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import swirn.spring.dto.simple.BookSimpleDTO;

@Getter @Setter
public class RentalDTO {
    private Long id;
    private BookSimpleDTO book;
    private HolderDTO holder;
    private LocalDate startDate;
    private LocalDate endDate;
}