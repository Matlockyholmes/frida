package be.vdab.frida.forms;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class BeginNaamFormTest {
    private Validator validator;
    @Before
    public void before(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void naamOk(){
        assertThat(validator.validateValue(BeginNaamForm.class,"beginNaam","b")).isEmpty();
    }
    @Test
    public void naamMoetIngevuldZijn(){
        assertThat(validator.validateValue(BeginNaamForm.class, "beginNaam",null)).isNotEmpty();
    }
    @Test
    public void naamMoetMinstensEénTekenZijn(){
        assertThat(validator.validateValue(BeginNaamForm.class, "beginNaam", "")).isNotEmpty();
    }
}
