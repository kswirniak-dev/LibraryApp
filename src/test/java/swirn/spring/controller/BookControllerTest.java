package swirn.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import swirn.spring.domain.entity.Book;
import swirn.spring.repository.BookRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.then;

import org.mockito.MockingDetails;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;
    
    @InjectMocks
    private BookController bookController;
    
    Book book;

    @BeforeEach
    void setUp() {
    	book = new Book();
    	book.setId(1L);
    	book.setTitle("Nabchodzi burza");
    	book.setAuthor("Thomas, Derek");
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(bookController, "bookRepository", bookRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
             
    }

    @Test
    void redirectToMain_ShouldRedirectToBookList() {
        String result = bookController.redirectToMain();
        assertEquals("redirect:/books", result);
    }

    @Test
    void showBookList_ShouldAddBooksToModelAndRenderIndexPage() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        String viewName = bookController.showBookList(model);

        // Assert
        assertEquals("books", viewName);
        verify(model).addAttribute(eq("books"), anyList());
        verify(bookRepository).findAll();
    }

    @Test
    void showSignUpForm_ShouldRenderAddBookPage() {
        Book book = new Book();
        String viewName = bookController.showSignUpForm(book);
        assertEquals("add-book", viewName);
    }

    @Test
    void showEditBookForm_WhenBookExists_ShouldAddBookToModelAndRenderEditPage() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        String viewName = bookController.showEditBookForm(bookId, model);

        // Assert
        assertEquals("edit-book", viewName);
        verify(model).addAttribute("book", book);
    }

    @Test
    void showEditBookForm_WhenBookNotExists_ShouldThrowIllegalArgumentException() {
        // Arrange
        Long bookId = 1L;
       
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            bookController.showEditBookForm(bookId, model);
        });
    }

    @Test
    void addBook_WhenValidationPasses_ShouldSaveBookAndRedirectToIndex() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        String viewName = bookController.addBook(book, bindingResult, model);

        // Assert
        assertEquals("redirect:/books", viewName);
        verify(bookRepository).save(book);
    }

    @Test
    void addBook_WhenValidationFails_ShouldReturnAddBookPage() {
        // Arrange
        Book book = new Book();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = bookController.addBook(book, bindingResult, model);

        // Assert
        assertEquals("add-book", viewName);
        verify(bookRepository, never()).save(book);
    }

    @Test
    void updateBook_WhenValidationPasses_ShouldUpdateBookAndRedirectToIndex() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        String viewName = bookController.updateBook(bookId, book, bindingResult, model);

        // Assert
        assertEquals("redirect:/books", viewName);
        assertEquals(bookId, book.getId());
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook_WhenValidationFails_ShouldReturnAddBookPage() {
        // Arrange
        Long bookId = 1L;
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = bookController.updateBook(bookId, book, bindingResult, model);

        // Assert
        assertEquals("add-book", viewName);
        verify(bookRepository, never()).save(book);
    }

    @Test
    void delete_ShouldDeleteBookAndRedirectToIndex() {
        // Arrange
        Long bookId = 1L;
        
        // Act
        String viewName = bookController.delete(bookId);

        // Assert
        verify(bookRepository, times(1)).deleteById(bookId);
        assertEquals("redirect:/books", viewName);
        
    }
}