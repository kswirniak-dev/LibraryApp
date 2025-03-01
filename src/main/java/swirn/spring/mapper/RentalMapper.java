package swirn.spring.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import swirn.spring.domain.entity.Rental;
import swirn.spring.dto.RentalDTO;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    Rental rentalDTOtoRental(RentalDTO dto);

    RentalDTO rentalToRentalDTO(Rental rental);

    
    List<Rental> convertRentalDTOListToRentalList(List<RentalDTO> list);

    List<RentalDTO> convertRentalListToRentalDTOList(List<Rental> list);

}
