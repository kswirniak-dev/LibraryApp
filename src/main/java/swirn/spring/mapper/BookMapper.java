package swirn.spring.mapper;



import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import swirn.spring.domain.entity.Book;
import swirn.spring.dto.BookDTO;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book entity);

    Book bookDTOtoBook(BookDTO dto);
    
    List<Book> convertBookDTOListToBookList(List<BookDTO> list);

    List<BookDTO> convertBookListToBookDTOList(List<Book> list);
}