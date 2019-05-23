package be.vdab.frida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("snacks")
public class SnackController {
    private char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    @GetMapping
    public ModelAndView toonPagina(){
        return new ModelAndView("snacks","alfabet",alfabet);
    }
}
