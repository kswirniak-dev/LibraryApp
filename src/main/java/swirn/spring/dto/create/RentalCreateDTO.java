package swirn.spring.dto.create;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RentalCreateDTO {
    private Long bookId;
    private Long holderId;
    private LocalDate startDate;
    private LocalDate endDate;
}