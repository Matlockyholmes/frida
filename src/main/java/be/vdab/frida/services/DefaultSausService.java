package be.vdab.frida.services;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.repositories.CSVSausRepository;
import be.vdab.frida.repositories.SausRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultSausService implements SausService{
    private final SausRepository repository;

    public DefaultSausService(SausRepository sausRepository){
        this.repository = sausRepository;
    }
    @Override
    public List<Saus> findall() {
        return repository.findall();
    }

    @Override
    public List<Saus> findByNaamBegintMet(char letter) {
        return repository.findall().stream().filter(saus -> saus.getNaam().charAt(0) == letter).collect(Collectors.toList());
    }

    @Override
    public Optional<Saus> findById(long id) {
        return repository.findall().stream().filter(saus -> saus.getNummer() == id).findFirst();
    }
}
