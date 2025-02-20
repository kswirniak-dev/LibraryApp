package swirn.spring.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import swirn.spring.domain.entity.Holder;
import swirn.spring.dto.HolderDTO;


@Mapper(componentModel = "spring")
public interface HolderMapper {
	
    HolderDTO holderToHolderDTO(Holder entity);

    Holder holderDTOtoHolder(HolderDTO dto);
    
    List<Holder> convertHolderDTOListToHolderList(List<HolderDTO> list);

    List<HolderDTO> convertHolderListToHolderDTOList(List<Holder> list);

}
