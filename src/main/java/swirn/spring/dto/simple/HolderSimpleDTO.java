package swirn.spring.dto.simple;

import lombok.Getter;
import lombok.Setter;
import swirn.spring.dto.RentalDTO;

import java.util.List;

@Getter @Setter
public class HolderSimpleDTO {
	
    private Long id;
    private String name;
    private String contactNumber;
}