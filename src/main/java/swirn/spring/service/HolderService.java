package swirn.spring.service;

import swirn.spring.dto.HolderDTO;

import java.util.List;

public interface HolderService {

    List<HolderDTO> getAll();

    HolderDTO getById(Long id);

    void deleteById(Long id);

    HolderDTO create(HolderDTO book);

    HolderDTO update(Long id, HolderDTO holder);
}
