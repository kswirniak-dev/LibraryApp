package swirn.spring.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import swirn.spring.dto.simple.BookSimpleDTO;
import swirn.spring.dto.simple.HolderSimpleDTO;

@Getter @Setter
public class RentalDTO {
    private Long id;
    private BookSimpleDTO book;
    private HolderSimpleDTO holder;
    private LocalDate startDate;
    private LocalDate endDate;

}