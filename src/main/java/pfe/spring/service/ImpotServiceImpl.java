package pfe.spring.service;


import pfe.spring.entity.Impot;
import pfe.spring.repositury.ContribuableRepo;
import pfe.spring.repositury.ImpotRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImpotServiceImpl implements ImpotService{


    @Autowired
    private ImpotRepo impotRepo;

    @Autowired
    private ContribuableRepo contribuableRepo;


    @Override
    public Impot saveImpot(Impot impot) {
        log.info("Saving impot: {}", impot);
        return impotRepo.save(impot);
    }

    @Override
    public List<Impot> getAllImpots() {
        log.info("Getting all Impots");
        return impotRepo.findAll();
    }

    public void deleteImpot(Long idImpot) {
        log.info("Deleting Impot with ID: {}", idImpot);
        Impot impot = impotRepo.findById(idImpot).orElse(null);
        if (impot != null) {
            impotRepo.delete(impot);
            log.info("Impot with ID {} deleted successfully", idImpot);
        } else {
            log.warn("Impot with ID {} not found, no deletion performed", idImpot);
        }
    }

    @Override
    public Impot updateImpot(Long idImpot) {
        Impot impot = impotRepo.findById(idImpot).orElse(null);
        if (impot != null) {
            log.info("Found Impot with id: {}", idImpot);
            return impotRepo.save(impot);
        } else {
            return null;
        }
    }

    @Override
    public Impot getImpot(Long idImpot) {
        log.info("getting Impot with ID: {}", idImpot);
        return impotRepo.findById(idImpot).orElse(null);
    }

}
