package swirn.spring.dto.simple;

import lombok.Getter;
import lombok.Setter;
import swirn.spring.dto.HolderDTO;

import java.time.LocalDate;

@Getter @Setter
public class RentalSimpleDTO {
    private Long id;
    private BookSimpleDTO book;
    private HolderDTO holder;
    private LocalDate startDate;
    private LocalDate endDate;
}
