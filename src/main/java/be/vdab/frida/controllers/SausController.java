package be.vdab.frida.controllers;

import be.vdab.frida.services.DefaultSausService;
import be.vdab.frida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sauzen")
public class SausController {
    private final SausService sausService;
    private final char[] charArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public SausController(SausService sausService){
        this.sausService = sausService;
    }

    @GetMapping
    public ModelAndView sauzen(){
        return new ModelAndView("sauzen","sauzen",sausService.findall());
    }
    @GetMapping("{nummer}")
    public ModelAndView saus(@PathVariable long nummer){
        ModelAndView modelAndView = new ModelAndView("saus");
        sausService.findById(nummer).ifPresent(saus -> modelAndView.addObject("saus",saus));
        return modelAndView;
    }
    @GetMapping("alfabet")
    public ModelAndView toonAlfabet(){
        return new ModelAndView("alfabet","alfabet",charArray);
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView toonSausBijLetter(@PathVariable char letter){
        return new ModelAndView("alfabet","sauzen", sausService.findByNaamBegintMet(letter)).addObject("alfabet",charArray);
    }
}
