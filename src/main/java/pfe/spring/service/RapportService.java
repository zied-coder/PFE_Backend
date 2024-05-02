package pfe.spring.service;

import pfe.spring.entity.Programme;
import pfe.spring.entity.Rapport;

import java.util.List;

public interface RapportService {

    public void generateReportIfNeeded(Programme programme);

    public Rapport getRapport (Long idRapport);


    public List<Rapport> getAllRapport ();

    public void deleteRapport(Long idRapport);

    public Rapport updateRapport(Long idRapport);


}
