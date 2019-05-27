package be.vdab.frida.controllers;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.exceptions.SnackNietGevondenException;
import be.vdab.frida.forms.BeginNaamForm;
import be.vdab.frida.services.DefaultSnackService;
import be.vdab.frida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("snacks")
public class SnackController {
    private char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SnackService snackService;

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }
    @GetMapping
    public ModelAndView toonPagina(){
        return new ModelAndView("snacks","alfabet",alfabet);
    }
    @GetMapping("{letter}")
    public ModelAndView toonSnack(@PathVariable char letter){
        return new ModelAndView("snacks","snacks", snackService.findByBeginNaam(String.valueOf(letter))).addObject("alfabet",alfabet);
    }
    @GetMapping("beginnaam/form")
    public ModelAndView beginNaamForm(){
        ModelAndView modelAndView = new ModelAndView("beginnaam");
        modelAndView.addObject(new BeginNaamForm(""));
        return modelAndView;
    }
    @GetMapping("beginnaam")
    public ModelAndView beginNaam(@Valid BeginNaamForm beginNaamForm, Errors errors){
        ModelAndView modelAndView = new ModelAndView("beginnaam");
        if(errors.hasErrors()){
            return modelAndView;
        }
        return modelAndView.addObject("snacks",snackService.findByBeginNaam(beginNaamForm.getBeginNaam()));
    }

    @GetMapping("wijzigen/form/{id}")
    public ModelAndView snackWijzigenForm(@PathVariable long id){
        Snack chosenSnack = snackService.read(id).filter(snack -> snack.getId() == id).get();
        return new ModelAndView("wijzigen").addObject(chosenSnack);
    }

    @PostMapping
    public String snackWijzigen(@Valid Snack snack, Errors errors, RedirectAttributes redirect){
        if(errors.hasErrors()){
            return "wijzigen";
        }
        try{
            snackService.update(snack);
            redirect.addAttribute("gewijzigd",snack.getNaam());
            return "redirect:/snacks";
        } catch (SnackNietGevondenException ex){
            return "redirect:/snacks";
        }


    }

}
