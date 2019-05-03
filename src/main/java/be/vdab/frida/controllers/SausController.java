package be.vdab.frida.controllers;

import be.vdab.frida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sauzen")
public class SausController {

    private final Saus[] sauzen = {
            new Saus(1, "cocktail", new String[] {"tomaten","peper"}),
            new Saus(2,"mayonaise", new String[] {"puistenuitduwsel"}),
            new Saus(3,"mosted", new String[] {"paprika","wijn"}),
            new Saus(4, "tartare", new String[] {"vis","zout","dingenwaarjevanhoudt"}),
            new Saus(5, "vinaigrette", new String[] {"vin","aig","rette"})
    };
    @GetMapping
    public ModelAndView sauzen(){
        return new ModelAndView("sauzen","sauzen",sauzen);
    }
}
