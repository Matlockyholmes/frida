package be.vdab.frida.controllers;

import be.vdab.frida.domain.Adres;
import be.vdab.frida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;

@Controller
@RequestMapping("/")
class IndexController {
    public String boodscap(){
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_WEEK);
        if(day == 2 || day == 5){
            return "gesloten";
        } else {
            return "open";
        }
    }
    @GetMapping
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index","boodschap",boodscap());
        modelAndView.addObject("frituur",new Adres("Mayonaisen Steenweg","420", new Gemeente("Kontich",2550)));
        return modelAndView;
    }
}
