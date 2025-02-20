package swirn.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swirn.spring.dto.HolderDTO;
import swirn.spring.mapper.HolderMapper;
import swirn.spring.repository.HolderRepository;
import swirn.spring.service.HolderService;

import java.util.List;

@Service
public class HolderServiceImpl implements HolderService {

    private final HolderRepository holderRepository;
    private final HolderMapper holderMapper;

    @Autowired
    public HolderServiceImpl(HolderRepository holderRepository, HolderMapper holderMapper) {
        this.holderRepository = holderRepository;
        this.holderMapper = holderMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolderDTO> getAll() {
        return holderMapper.convertHolderListToHolderDTOList(holderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public HolderDTO getById(Long id) {
        return holderMapper.holderToHolderDTO(holderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException()));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        holderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public HolderDTO create(HolderDTO holder) {
        return holderMapper.holderToHolderDTO(holderRepository.save(holderMapper.holderDTOtoHolder(holder)));
    }

    @Override
    @Transactional
    public HolderDTO update(Long id, HolderDTO holder) {
        HolderDTO holderFromRepo = holderMapper.holderToHolderDTO(holderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException()));
        holderFromRepo.setName(holder.getName());
        holderFromRepo.setContactNumber(holder.getContactNumber());
        return holderMapper.holderToHolderDTO(holderRepository.save(holderMapper.holderDTOtoHolder(holderFromRepo)));
    }
}
