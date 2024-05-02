package pfe.spring.service;



import org.springframework.mail.SimpleMailMessage;
import pfe.spring.entity.*;
import pfe.spring.mongo_repo.RapportMongoRepo;
import pfe.spring.repositury.ProgrammeRepo;
import pfe.spring.repositury.SessionContribuableRepo;
import pfe.spring.repositury.SessionControleRepo;
import pfe.spring.repositury.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProgrammeServiceImpl implements ProgrammeService{

    private final JavaMailSender javaMailSender;
    @Autowired
    ProgrammeRepo programmeRepo;

    @Autowired
    RapportMongoRepo rapportMongoRepo;

    @Autowired
    UserRepo userRepo;
    @Autowired
    SessionControleRepo sessionControleRepo;

    @Autowired
    SessionContribuableRepo sessionContribuableRepo;

    @Autowired
    RapportService rapportService;


    @Override
    public Programme createProgramme(Programme programme, Long idSession, Long idControlleur, Long idSessionContribuable, List<String> taskGoals) {
        log.info("Creating new Programme");
        SessionControle session = sessionControleRepo.findById(idSession).orElse(null);

        if (session != null) {
            User selectedControlleur = session.getControlleurs()
                    .stream()
                    .filter(controlleur -> controlleur.getIdUser().equals(idControlleur))
                    .findFirst()
                    .orElse(null);

            SessionContribuable selectedContribuable = session.getContribuablesSession()
                    .stream()
                    .filter(sessionContribuable -> sessionContribuable.getId().equals(idSessionContribuable))
                    .findFirst()
                    .orElse(null);

            if (selectedControlleur != null && selectedContribuable != null) {
                programme.setControlleur(selectedControlleur);
                programme.setSessionContribuable(selectedContribuable);

                List<Task> tasks = new ArrayList<>();
                for (String goal : taskGoals) {
                    Task task = new Task();
                    task.setGoal(goal);
                    task.setProgramme(programme);
                    task.setAvancement(Avancement.AFAIRE);
                    tasks.add(task);
                }
                SimpleMailMessage message = new SimpleMailMessage();
                programme.setTasks(tasks);
                message.setTo(selectedControlleur.getEmail());
                message.setSubject("nouveau programme du controle");
                message.setText("vous êtes marqué comme un controlleur chez la contribuable : " + selectedContribuable.getContribuable().getNom());
                javaMailSender.send(message);
                log.info("Email notification sent to the controlleur: {}", selectedControlleur.getEmail());
            } else {
                log.warn("Controlleur with ID {} or Contribuable with ID {} not found, cannot create Programme", idControlleur, idSessionContribuable);
            }
        } else {
            log.warn("Session with ID {} not found, cannot create Programme", idSession);
        }

        return programmeRepo.save(programme);
    }

    @Override
    public void deleteProgramme(Long idProgramme) {
        log.info("Deleting programme with ID: {}", idProgramme);
        Programme programme = programmeRepo.findById(idProgramme).orElse(null);
        programmeRepo.delete(programme);
    }


    @Override
    public List<Programme> getProgrammes() {
        log.info("Getting all Programmes");
        return programmeRepo.findAll();
    }

    @Override
    public List<Programme> getProgrammesBySession(Long sessionId) {
        log.info("Getting Programmes by Session ID: {}", sessionId);
        return programmeRepo.getProgrammesBySession(sessionId);
    }

    @Override
    public List<Programme> getProgrammesByControlleur(String username) {
        log.info("Getting Programmes by Controlleur Username: {}", username);
        return programmeRepo.getProgrammesByControlleur(username);
    }


    @Override
    public List<Task> getTasksByIdProgramme(Long idProgramme) {
        var programme = programmeRepo.findById(idProgramme).orElse(null);
        log.info("Getting Tasks by ProgrammeId: {}", idProgramme);

        if (programme != null) {
            return programme.getTasks();
        } else {
            return null;
        }
    }
    @Override
    public int updateOverallProgress(Long idProgramme) {
        log.info("Updating Overall Progress for Programme with ID: {}", idProgramme);
        var programme = programmeRepo.findById(idProgramme).orElse(null);
        if(programme!=null) {
            List<Task> tasks = programme.getTasks();
            if (tasks.isEmpty()) {
                log.warn("No tasks found for Programme with ID: {}", idProgramme);
                return 0;
            }
            int totalProgress = 0;
            for (Task task : tasks) {
                totalProgress += task.getProgress();
            }
            int overallProgress = totalProgress / tasks.size();
            programme.setOverallProgress(overallProgress);
            if (overallProgress == 100) {
                log.info("Overall Progress is 100% for Programme with ID: {}", idProgramme);
                rapportService.generateReportIfNeeded(programme);
            }
            return overallProgress;
        }
        return 0;
    }
    @Override
    public List<Rapport> getRapportsByProgramme(Long idProgramme) {
        log.info("Getting Rapports by Programme ID: {}", idProgramme);
        return rapportMongoRepo.findRapportByIdProgramme(idProgramme);
    }
    @Override
    public long getProgrammeCount() {
        log.info("Getting Programme Count");
        return programmeRepo.count();
    }
}