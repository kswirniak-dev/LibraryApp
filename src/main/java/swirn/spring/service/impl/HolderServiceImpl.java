package swirn.spring.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swirn.spring.domain.entity.Holder;
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
    public HolderServiceImpl(HolderRepository holderRepository, HolderMapper holderMapper)
    {
        this.holderRepository = holderRepository;
        this.holderMapper = holderMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolderDTO> getAll()
    {
        return holderMapper.convertHolderListToHolderDTOList(holderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public HolderDTO getById(Long id)
    {
        return holderMapper.holderToHolderDTO(holderRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!holderRepository.existsById(id))
        {
            throw new EntityNotFoundException("Holder with id " + id + "not found");
        }
        holderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public HolderDTO create(HolderDTO holderDTO) {
        if (holderDTO == null)
        {
            throw new IllegalArgumentException("Holder data cannot be null");
        }
        if (holderDTO.getId() != null)
        {
            holderDTO.setId(null);
        }
        Holder holder = holderRepository.save(holderMapper.holderDTOtoHolder(holderDTO));
        return holderMapper.holderToHolderDTO(holder);
    }

    @Override
    @Transactional
    public HolderDTO update(Long id, HolderDTO holderDTO) {
        HolderDTO holderFromRepo = holderMapper.holderToHolderDTO(holderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
        if (holderDTO.getName() != null)
        {
            holderFromRepo.setName(holderDTO.getName());
        }
        if (holderDTO.getContactNumber() != null)
        {
            holderFromRepo.setContactNumber(holderDTO.getContactNumber());
        }
        Holder holder = holderRepository.save(holderMapper.holderDTOtoHolder(holderDTO));
        return holderMapper.holderToHolderDTO(holder);
    }
}
