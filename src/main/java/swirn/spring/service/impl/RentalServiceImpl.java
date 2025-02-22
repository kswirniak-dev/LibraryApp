package swirn.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swirn.spring.dto.RentalDTO;
import swirn.spring.mapper.RentalMapper;
import swirn.spring.repository.RentalRepository;
import swirn.spring.service.RentalService;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    @Autowired
    public RentalServiceImpl(RentalRepository rentalRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<RentalDTO> getAll() {
        return rentalRepository
                .findAll()
                .stream()
                .map(rentalMapper::rentalToRentalDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RentalDTO getById(Long id) {
        return rentalMapper.rentalToRentalDTO(rentalRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        rentalRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RentalDTO create(RentalDTO rental) {
        return rentalMapper.rentalToRentalDTO(rentalRepository.save(rentalMapper.rentalDTOtoRental(rental)));
    }

    @Override
    @Transactional
    public RentalDTO update(Long id, RentalDTO rental) {
        RentalDTO rentalFromRepo = rentalMapper.rentalToRentalDTO(rentalRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        rentalFromRepo.setBook(rental.getBook());
        rentalFromRepo.setHolder(rental.getHolder());
        rentalFromRepo.setStartDate(rental.getStartDate());
        rentalFromRepo.setEndDate(rental.getEndDate());
        rentalRepository.save(rentalMapper.rentalDTOtoRental(rentalFromRepo));
        return rentalFromRepo;
    }
}
