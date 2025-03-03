package swirn.spring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class HolderDTO {
	
    private Long id;
    private String name;
    private String contactNumber;
    private List<RentalDTO> rentals;
}