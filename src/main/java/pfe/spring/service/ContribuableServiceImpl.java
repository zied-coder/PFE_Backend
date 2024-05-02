package pfe.spring.service;

import pfe.spring.entity.Contribuable;
import pfe.spring.repositury.ContribuableRepo;
import pfe.spring.repositury.SessionControleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service @RequiredArgsConstructor
@Transactional
@Slf4j
public class ContribuableServiceImpl implements ContribuableService{

    @Autowired
    public ContribuableRepo contribuableRepo;
    @Autowired
    public SessionControleRepo sessionControleRepo;



    @Override
    public Contribuable saveContribuable(Contribuable contribuable) {
        log.info("Saving Contribuable: {}", contribuable);
        return contribuableRepo.save(contribuable);
    }

    @Override
    public List<Contribuable> getAllContribuables() {
        log.info("Getting all Contribuables.");
        return contribuableRepo.findAll();
    }

    @Override
    public void deleteContribuable(Long idContribuable) {
        log.info("Deleting Contribuable with ID: {}", idContribuable);
        contribuableRepo.deleteById(idContribuable);
    }

    @Override
    public Contribuable updateContribuable(Long idContribuable, Contribuable updatedContribuable) {
        log.info("Updating Contribuable with ID: {}. Updated Contribuable: {}", idContribuable, updatedContribuable);

        var contribuable = contribuableRepo.findById(idContribuable).orElseThrow(() -> new IllegalArgumentException("Contribuable not found with ID: " + idContribuable));

        if (updatedContribuable.getFormeJuridique() != null) {
            contribuable.setFormeJuridique(updatedContribuable.getFormeJuridique());
        }

        if (updatedContribuable.getNom() != null) {
            contribuable.setNom(updatedContribuable.getNom());
        }

        if (updatedContribuable.getSecteurActivite() != null) {
            contribuable.setSecteurActivite(updatedContribuable.getSecteurActivite());
        }

        if (updatedContribuable.getSituationJuridique() != null) {
            contribuable.setSituationJuridique(updatedContribuable.getSituationJuridique());
        }

        return contribuableRepo.save(contribuable);
    }

    @Override
    public Contribuable getContribuable(Long idContribuable) {
        log.info("Getting Contribuable with ID: {}", idContribuable);
        return contribuableRepo.findById(idContribuable).orElse(null);
    }

    @Override
    public long getContribuableCount() {
        log.info("Getting count of Contribuables.");
        return contribuableRepo.count();
    }

    @Override
    public List<Contribuable> searchContribuable(String query) {
        log.info("Searching Contribuables with query: {}", query);
        return contribuableRepo.findByNomContainingIgnoreCase(query);
    }


}
