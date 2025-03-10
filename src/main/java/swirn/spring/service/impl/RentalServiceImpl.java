package swirn.spring.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swirn.spring.domain.entity.Book;
import swirn.spring.domain.entity.Holder;
import swirn.spring.domain.entity.Rental;
import swirn.spring.dto.RentalDTO;
import swirn.spring.mapper.RentalMapper;
import swirn.spring.repository.BookRepository;
import swirn.spring.repository.HolderRepository;
import swirn.spring.repository.RentalRepository;
import swirn.spring.service.RentalService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final BookRepository bookRepository;
    private final HolderRepository holderRepository;

    @Autowired
    public RentalServiceImpl(RentalRepository rentalRepository, RentalMapper rentalMapper, BookRepository bookRepository, HolderRepository holderRepository)
    {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
        this.bookRepository = bookRepository;
        this.holderRepository = holderRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<RentalDTO> getAll()
    {
        return rentalRepository
                .findAll()
                .stream()
                .map(rentalMapper::rentalToRentalDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RentalDTO getById(Long id)
    {
        return rentalMapper.rentalToRentalDTO(rentalRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public void deleteById(Long id)
    {
        if (!rentalRepository.existsById(id))
        {
            throw new EntityNotFoundException("Rental with id " + id + " not found");
        }
        rentalRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RentalDTO create(RentalDTO rentalDTO)
    {
        Book book = bookRepository.findById(rentalDTO.getBook().getId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Holder holder = holderRepository.findById(rentalDTO.getHolder().getId())
                .orElseThrow(() -> new EntityNotFoundException("Holder not found"));

        Rental rental = new Rental();
        rental.setBook(book);
        rental.setHolder(holder);
        rental.setStartDate(rentalDTO.getStartDate());
        rental.setEndDate(rentalDTO.getEndDate());

        if (book.getRentals() == null)
        {
            book.setRentals(new ArrayList<>());
        }
        book.getRentals().add(rental);
        if (holder.getRentals() == null)
        {
            holder.setRentals(new ArrayList<>());
        }
        holder.getRentals().add(rental);

        Rental savedRental = rentalRepository.save(rental);
        return rentalMapper.rentalToRentalDTO(savedRental);
    }


    @Override
    @Transactional
    public RentalDTO update(Long id, RentalDTO rentalDTO)
    {
        Rental existingRental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));

        Book book = bookRepository.findById(rentalDTO.getBook().getId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Holder holder = holderRepository.findById(rentalDTO.getHolder().getId())
                .orElseThrow(() -> new EntityNotFoundException("Holder not found"));

        existingRental.setBook(book);
        existingRental.setHolder(holder);
        existingRental.setStartDate(rentalDTO.getStartDate());
        existingRental.setEndDate(rentalDTO.getEndDate());


        Rental savedRental = rentalRepository.save(existingRental);

        return rentalMapper.rentalToRentalDTO(savedRental);
    }
}
