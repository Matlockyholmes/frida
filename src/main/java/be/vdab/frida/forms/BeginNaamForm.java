package be.vdab.frida.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BeginNaamForm {
    @NotNull
    @NotEmpty
    private final String beginNaam;

    public BeginNaamForm(String beginNaam) {
        this.beginNaam = beginNaam;
    }

    public String getBeginNaam() {
        return beginNaam;
    }
}
