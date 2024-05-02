package pfe.spring.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.spring.entity.*;
import pfe.spring.repositury.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SessionControleServiceImpl implements SessionControleService{

    @Autowired
    private SessionControleRepo sessionControleRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ContribuableRepo contribuableRepo;
    @Autowired
    SessionContribuableRepo sessionContribuableRepo;

    @Autowired
    ProgrammeRepo programmeRepo;


    @Override
    public SessionControle AddSession(SessionControle session, List<String>usernames, List<String>names, List<String> descriptions) {
        log.info("Adding SessionControle with ID: {}", session.getIdSession());
        List<User> users=new ArrayList<>();
        List<User> controlleurs = userRepo.findByUsernameIn(usernames);
        users.addAll(controlleurs);
        List<Contribuable> allContribuables=new ArrayList<>();
        List<Contribuable> contribuables = contribuableRepo.findByNomIn(names);
        allContribuables.addAll(contribuables);


        if (descriptions.size() != contribuables.size()) {
            log.debug("Number of descriptions doesn't match the number of contribuables.");
            throw new IllegalArgumentException("Number of descriptions doesn't match the number of contribuables");
        }
        List<SessionContribuable> sessionContribuables = new ArrayList<>();

        for (int i = 0; i < allContribuables.size(); i++) {
            Contribuable contribuable = allContribuables.get(i);
            String description = descriptions.get(i);
            SessionContribuable sessionContribuable = new SessionContribuable();
            sessionContribuable.setSession(session);
            contribuable.getImpotsContribuable();
            sessionContribuable.setContribuable(contribuable);
            sessionContribuable.setDescription(description);
            sessionContribuables.add(sessionContribuable);
            sessionContribuableRepo.save(sessionContribuable);
        }

        session.setEtat(Etat.ENATTENT);
        session.setControlleurs(users);
        session.setContribuablesSession(sessionContribuables);
        return sessionControleRepo.save(session);
    }



    @Override
    public long getSessionCount() {
        log.info("Getting SessionControle count");
        return sessionControleRepo.count();
    }

    @Override
    public SessionControle validateSession(Long idSession) {
        log.info("Validating SessionControle with ID: {}", idSession);
        var session = sessionControleRepo.findById(idSession).orElse(null);
        session.setEtat(Etat.VALIDE);
        session.setDate_Validation(LocalDateTime.now());
        return session;
    }

    @Override
    public SessionControle invalidateSession(Long idSession) {
        log.info("Invalidating SessionControle with ID: {}", idSession);
        var session = sessionControleRepo.findById(idSession).orElse(null);
        session.setEtat(Etat.NONVALIDE);
        return session;
    }

    @Override
    public List<SessionControle> searchSession(String query) {
        log.info("Searching SessionControle with query: {}", query);
        return sessionControleRepo.findByCodeContainingIgnoreCase(query);
    }

    @Override
    public List<SessionContribuable> getSessionContribuable(Long idSession) {
        log.info("Getting SessionContribuables for SessionControle with ID: {}", idSession);
        SessionControle session = sessionControleRepo.findById(idSession).orElse(null);
        return session.getContribuablesSession();
    }

    @Override
    public List<User> getSessionControlleurs(Long idSession) {
        log.info("Getting Controlleurs for SessionControle with ID: {}", idSession);
        SessionControle session = sessionControleRepo.findById(idSession).orElse(null);
        return session.getControlleurs();
    }

    @Override
    public List<SessionControle> getAllSession() {
        log.info("Getting all SessionControles");
        return sessionControleRepo.findAll();
    }

    @Override
    public SessionControle getSession(Long idSession) {
        log.info("Getting SessionControle with ID: {}", idSession);
        return sessionControleRepo.findById(idSession).orElse(null);
    }

    @Override
    public void deleteSessionControle(Long idSessionControle) {
        log.info("Deleting SessionControle with ID: {}", idSessionControle);
        SessionControle sessionControle = sessionControleRepo.findById(idSessionControle).orElse(null);
        sessionControleRepo.delete(sessionControle);
    }

    @Override
    public SessionControle updateSessionControle(Long idSession, List<String> usernames, List<String> names, List<String> descriptions, SessionControle updatedSession) {
        log.info("Updating SessionControle with ID: {}", idSession);

        SessionControle session = sessionControleRepo.findById(idSession).orElse(null);
        if (session == null) {
            log.warn("SessionControle not found with ID: {}", idSession);
            return null;
        }

        if (updatedSession.getObjet() != null) {
            session.setObjet(updatedSession.getObjet());
        }

        if (updatedSession.getCode() != null) {
            session.setCode(updatedSession.getCode());
        }
        if (updatedSession.getDate_Debut() != null) {
            session.setDate_Debut(updatedSession.getDate_Debut());
        }
        if (updatedSession.getDate_Fin() != null) {
            session.setDate_Fin(updatedSession.getDate_Fin());
        }

        if (!usernames.isEmpty()) {
            List<User> controlleurs = userRepo.findByUsernameIn(usernames);
            session.setControlleurs(controlleurs);
        }

        if (!names.isEmpty()) {
            List<Contribuable> allContribuables = new ArrayList<>();
            List<Contribuable> contribuables = contribuableRepo.findByNomIn(names);
            allContribuables.addAll(contribuables);

            if (descriptions.size() != contribuables.size()) {
                log.debug("Number of descriptions doesn't match the number of contribuables for updating session with ID: {}", idSession);
                throw new IllegalArgumentException("Number of descriptions doesn't match the number of contribuables");
            }

            List<SessionContribuable> sessionContribuables = new ArrayList<>();
            for (int i = 0; i < allContribuables.size(); i++) {
                Contribuable contribuable = allContribuables.get(i);
                String description = descriptions.get(i);
                SessionContribuable sessionContribuable = new SessionContribuable();
                sessionContribuable.setSession(session);
                sessionContribuable.setContribuable(contribuable);
                sessionContribuable.setDescription(description);
                sessionContribuables.add(sessionContribuable);
                sessionContribuableRepo.save(sessionContribuable);
            }
            session.setContribuablesSession(sessionContribuables);
        }

        return sessionControleRepo.save(session);
    }

    @Override
    public int updateProgress(Long idSession) {
        log.info("Updating Progress for SessionControle with ID: {}", idSession);
        var session = sessionControleRepo.findById(idSession).orElse(null);
        List<Programme> programmes = programmeRepo.getProgrammesBySession(idSession);

        if (programmes.isEmpty()) {
            return 0;
        }

        int totalProgress = 0;
        for (Programme programme : programmes) {
            totalProgress += programme.getOverallProgress();
        }

        int progress = totalProgress / programmes.size();
        session.setProgress(progress);
        sessionControleRepo.save(session);
        return progress;
    }




}
