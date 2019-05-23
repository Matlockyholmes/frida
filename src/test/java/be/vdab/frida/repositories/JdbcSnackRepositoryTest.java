package be.vdab.frida.repositories;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.exceptions.SnackNietGevondenException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcSnackRepository.class)
@Sql("/insertSnacks.sql")
public class JdbcSnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String SNACKS = "snacks";

    @Autowired
    private JdbcSnackRepository repository;

    private long idVanTestSnack (){
        return super.jdbcTemplate.queryForObject("select id from snacks where naam = 'test1'", Long.class);
    }
    @Test
    public void findByBeginNaam(){
        assertThat(repository.findByBeginNaam("test")).hasSize(super.countRowsInTableWhere(SNACKS, "naam like 'test%'"))
                .extracting(snack -> snack.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam.startsWith("test")))
                .isSorted();
    }
    @Test
    public void updateOnbestaandeSnack(){
        assertThatExceptionOfType(SnackNietGevondenException.class).isThrownBy(
                ()-> repository.update(new Snack(-1, "test1", BigDecimal.ONE)));
    }
    @Test
    public void update(){
        long id = idVanTestSnack();
        Snack snack = new Snack(id, "test1", BigDecimal.ONE);
        repository.update(snack);
        assertThat(super.jdbcTemplate.queryForObject("select prijs from snacks where id = ?", BigDecimal.class, id)).isOne();
    }
    @Test
    public void findById(){
        assertThat(repository.findById(idVanTestSnack()).get().getNaam()).isEqualTo("test1");
    }
    @Test
    public void findByOnbestaandId(){
        assertThat(repository.findById(-1)).isEmpty();
    }
}
