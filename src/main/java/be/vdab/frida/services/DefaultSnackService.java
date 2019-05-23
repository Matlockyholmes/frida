package be.vdab.frida.services;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.repositories.SnackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultSnackService implements SnackService{
    private SnackRepository snackRepository;

    public DefaultSnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    @Override
    public Optional<Snack> read(long id) {
        return snackRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void update(Snack snack) {
        snackRepository.update(snack);
    }

    @Override
    public List<Snack> findByBeginNaam(String beginNaam) {
        return snackRepository.findByBeginNaam(beginNaam);
    }
}
