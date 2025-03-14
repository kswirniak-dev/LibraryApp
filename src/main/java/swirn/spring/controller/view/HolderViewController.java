package swirn.spring.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import swirn.spring.dto.HolderDTO;
import swirn.spring.service.HolderService;

@Controller
@RequestMapping("holders")
public class HolderViewController {

	private final HolderService holderService;


    @Autowired
    public HolderViewController(HolderService holderService) {
		this.holderService = holderService;
	}
	
    @GetMapping()
    public String indexView(Model model) {
    	model.addAttribute("holders", holderService.getAll());
    	return "holder/list";
    }
    
    @PostMapping("add")
	public String addView(HolderDTO holder, BindingResult result) {
	    if (result.hasErrors()) {
	        return "holder/add";
	    }
	    holderService.create(holder);
	    return "redirect:/holders";	
		}
    

	@DeleteMapping("{id}/delete")
    public String deleteView(@PathVariable("id") Long id) {
		holderService.deleteById(id);
		return "redirect:/holders";
	}
    
    @GetMapping("{id}/edit")
    public String editView(@PathVariable("id") Long id, Model model) {
    	HolderDTO holder = holderService.getById(id);
    	model.addAttribute("holder", holder);
    	return "holder/edit";
    }
    
    @PutMapping("{id}/update")
	public String  updateHolder(@PathVariable("id") Long id, @Valid HolderDTO holder, BindingResult result)  {
		if (result.hasErrors()) {
	        return "holder/edit";
	    }
	    holderService.update(id, holder);
	    return "redirect:/holders";	
    }
    
    @GetMapping("add")
    public String showNewHolderForm(Model model) {
		model.addAttribute("holder", new HolderDTO());
        return "holder/add";
    }

}
