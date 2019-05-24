package be.vdab.frida.controllers;

import be.vdab.frida.forms.BeginNaamForm;
import be.vdab.frida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
}
