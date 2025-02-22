package swirn.spring.mapper;



import java.util.List;

import org.mapstruct.Mapper;
import swirn.spring.domain.entity.Book;
import swirn.spring.dto.BookDTO;
import swirn.spring.dto.simple.BookSimpleDTO;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book entity);

    Book bookDTOtoBook(BookDTO dto);

    BookSimpleDTO bookToBookSimpleDTO(Book entity);
    Book bookSimpleDTOToBook(BookSimpleDTO dto);

    BookSimpleDTO bookDTOtoBookSimpleDTO(BookDTO dto);

    BookDTO bookSimpleDTOtoBookDTO(BookSimpleDTO dto);
    List<Book> convertBookDTOListToBookList(List<BookDTO> list);

    List<BookDTO> convertBookListToBookDTOList(List<Book> list);
}