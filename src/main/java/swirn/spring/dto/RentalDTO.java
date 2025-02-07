package swirn.spring.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RentalDTO {
    private Long id;
    private Long bookId;
    private Long holderId;
    private LocalDate startDate;
    private LocalDate endDate;
}