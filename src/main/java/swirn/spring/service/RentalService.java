package swirn.spring.service;

import swirn.spring.dto.RentalDTO;

import java.util.List;

public interface RentalService {

    List<RentalDTO> getAll();

    RentalDTO getById(Long id);

    void deleteById(Long id);

    RentalDTO create(RentalDTO rental);

    RentalDTO update(Long id, RentalDTO rental);

}
