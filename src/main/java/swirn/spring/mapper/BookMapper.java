package swirn.spring.mapper;



import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import swirn.spring.domain.entity.Book;
import swirn.spring.domain.entity.Rental;
import swirn.spring.dto.BookDTO;
import swirn.spring.dto.RentalDTO;
import swirn.spring.dto.simple.BookSimpleDTO;

@Mapper(componentModel = "spring", uses = {RentalMapper.class})
public interface BookMapper {

    @Mapping(target = "activeRental", expression = "java(mapOptionalRental(entity.getActiveRental()))")
    BookDTO bookToBookDTO(Book entity);

    Book bookDTOtoBook(BookDTO dto);

    BookSimpleDTO bookToBookSimpleDTO(Book entity);
    Book bookSimpleDTOToBook(BookSimpleDTO dto);

    BookSimpleDTO bookDTOtoBookSimpleDTO(BookDTO dto);

    BookDTO bookSimpleDTOtoBookDTO(BookSimpleDTO dto);
    List<Book> convertBookDTOListToBookList(List<BookDTO> list);

    List<BookDTO> convertBookListToBookDTOList(List<Book> list);

    default RentalDTO mapOptionalRental(Optional<Rental> rental) {
        return rental.map(this::rentalToRentalDTO).orElse(null);
    }

    RentalDTO rentalToRentalDTO(Rental rental);
}