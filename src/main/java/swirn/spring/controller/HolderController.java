package swirn.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.validation.Valid;
import swirn.spring.domain.entity.Book;
import swirn.spring.domain.entity.Holder;
import swirn.spring.repository.BookRepository;
import swirn.spring.repository.HolderRepository;

@Controller
public class HolderController {
	private final HolderRepository holderRepository;

    @Autowired
    public HolderController(HolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }
	
    @GetMapping("/holders")
    public String showHoldersList(Model model) {
    	model.addAttribute("holders", holderRepository.findAll()); 
    	return "holders";
    }
    
    @PostMapping("/holder/add")
	public String addHolder(@Valid Holder holder, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        return "add-holder";
	    }
	    holderRepository.save(holder);
	    return "redirect:/holders";	
		}
    
    @SuppressWarnings("finally")
	@DeleteMapping("/holder/delete/{id}")
    public String delete(@PathVariable("id") Long id){
		try 
		{
			holderRepository.deleteById(id);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid holder id:" + id);
		}
		finally {
			return "redirect:/holders";
		}
	}
    
    @GetMapping("/holder/edit/{id}")
    public String showEditHolderForm(@PathVariable("id") Long id, Model model) {
    	Holder holder= holderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid holder id:" + id));
    	model.addAttribute("holder", holder);
    	return "edit-holder";
    }
    
    @PutMapping("/holder/update/{id}")
	public String  updateHolder(@PathVariable("id") Long id, @Valid Holder holder, BindingResult result, Model model)  {
		if (result.hasErrors()) {
	        return "add-holder";
	    }
	    holder.setId(id);
	    holderRepository.save(holder);
	    return "redirect:/holders";	
    }
    
    @GetMapping("/newholderform")
    public String showNewHolderForm(Holder holder) {
        return "add-holder";
    }

}
