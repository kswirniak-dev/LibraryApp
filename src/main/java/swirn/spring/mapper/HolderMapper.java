import java.util.List;

import org.mapstruct.Mapper;

import swirn.spring.domain.entity.Holder;
import swirn.spring.domain.entity.Rental;
import swirn.spring.dto.HolderDTO;
import swirn.spring.dto.RentalDTO;

@Mapper(componentModel = "spring")
public interface HolderMapper {
	
    HolderDTO holderToHolderDTO(Holder entity);

    Holder holderDTOtoHolder(HolderDTO dto);
    
    List<Holder> convertHolderDTOListToHolderList(List<HolderDTO> list);

    List<HolderDTO> convertHolderListToHolderDTOList(List<Holder> list);

}
