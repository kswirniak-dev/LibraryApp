package swirn.spring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import swirn.spring.controller.view.HolderViewController;
import swirn.spring.domain.entity.Holder;
import swirn.spring.repository.HolderRepository;

@ExtendWith(MockitoExtension.class)
public class HolderControllerTest {
	
	    private MockMvc mockMvc;

	    @Mock
	    private HolderRepository holderRepository;

	    @Mock
	    private Model model;

	    @Mock
	    private BindingResult bindingResult;
	    
	    @InjectMocks
	    private HolderViewController holderController;
	    
	    Holder holder;

	    @BeforeEach
	    void setUp() {
	    	
	    	this.holder = new Holder();
	    	this.holder.setId(1L);
	    	this.holder.setName("Kowalski, Jan");
	    	this.holder.setContactNumber("123456789");

	        MockitoAnnotations.openMocks(this);
	        ReflectionTestUtils.setField(holderController, "holderRepository", holderRepository);
	        mockMvc = MockMvcBuilders.standaloneSetup(holderController).build();
	             
	    }
	    
	    @Test
	    void showBookList_ShouldAddBooksToModelAndRenderIndexPage() {
	        // Arrange
	        when(holderRepository.findAll()).thenReturn(Collections.emptyList());

	        // Act
	        String viewName = holderController.showHoldersList(model);

	        // Assert
	        assertEquals("holders", viewName);
	        verify(model).addAttribute(eq("holders"), anyList());
	        verify(holderRepository).findAll();
	    }
	    
	    @Test
	    void addHolder_WhenValidationPasses_ShouldRenderHoldersPage() {
	    	// Arrange
	    	when(bindingResult.hasErrors()).thenReturn(false);
	    	
	    	// Act
	    	String viewName = holderController.addHolder(holder, bindingResult, model);
	    	
	    	// Assert
	    	assertEquals("redirect:/holders", viewName);
	    	verify(holderRepository, times(1)).save(holder);
	    	
	    }
	    
	    @Test
	    void addHolder_WhenValidationFails_ShouldReRenderAddHolderPage() {
	    	// Arrange
	    	when(bindingResult.hasErrors()).thenReturn(true);
	    	
	    	// Act
	    	String viewName = holderController.addHolder(holder, bindingResult, model);
	    			
	    	//Assert
	    	assertEquals("add-holder", viewName);
	    	verify(holderRepository, never()).save(holder);
	    }
	    @Test
	    void updateHolder_WhenValidationPasses_ShouldUpdateHolderAndRedirectToIndex() {
	        // Arrange
	        Long holderId = 1L;
	        when(bindingResult.hasErrors()).thenReturn(false);
	        when(holderRepository.save(any(Holder.class))).thenReturn(holder);

	        // Act
	        String viewName = holderController.updateHolder(holderId, holder, bindingResult, model);

	        // Assert
	        assertEquals("redirect:/holders", viewName);
	        assertEquals(holderId, holder.getId());
	        verify(holderRepository, times(1)).save(holder);
	    }
	    
	    @Test
	    void updateBook_WhenValidationFails_ShouldReturnAddHolderPage() {
	        // Arrange
	        Long holderId = 1L;
	        when(bindingResult.hasErrors()).thenReturn(true);

	        // Act
	        String viewName = holderController.updateHolder(holderId, holder, bindingResult, model);

	        // Assert
	        assertEquals("add-holder", viewName);
	        verify(holderRepository, never()).save(holder);
	    }
	    
	    @Test
	    void delete_ShouldDeleteHolderAndRedirectToIndex() {
	        // Arrange
	        Long holderId = 1L;
	        
	        // Act
	        String viewName = holderController.delete(holderId);

	        // Assert
	        verify(holderRepository, times(1)).deleteById(holderId);
	        assertEquals("redirect:/holders", viewName);
	        
	    }

	    @Test
	    void delete_WhenValidationFails_ShouldThrowIllegallArgumentException() {
	        // Arrange
	        Long holderId = 2L;
	        Mockito.doThrow(new IllegalArgumentException()).when(holderRepository).deleteById(holderId);
	        	        
	        // Act
	        String viewName = holderController.delete(holderId);

	        // Assert
	        verify(holderRepository, times(1)).deleteById(holderId);
	        assertEquals("redirect:/holders", viewName);
	        
	    }
}
