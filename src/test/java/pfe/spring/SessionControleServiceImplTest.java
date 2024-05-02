package pfe.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pfe.spring.auth.AuthenticationService;
import pfe.spring.entity.*;
import static org.junit.jupiter.api.Assertions.*;

import pfe.spring.repositury.*;
import pfe.spring.service.ContribuableServiceImpl;
import pfe.spring.service.ProgrammeServiceImpl;
import pfe.spring.service.SessionControleServiceImpl;
import pfe.spring.service.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
@Transactional

public class SessionControleServiceImplTest {

    @Autowired
    private SessionControleServiceImpl sessionControleService;

    @Autowired
    private SessionControleRepo sessionControleRepo;

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ContribuableRepo contribuableRepo;
    @Autowired
    private ContribuableServiceImpl contribuableService;

    @Autowired
    private SessionContribuableRepo sessionContribuableRepo;

    @Autowired
    private ProgrammeServiceImpl programmeService;

    @Autowired
    private ProgrammeRepo programmeRepo;


    @Test
    public void testDeleteUser() {
        // Create a user to be deleted
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@gmail.com");
        user.setPassword("password");
        User savedUser = userRepo.save(user);

        assertNotNull(savedUser.getIdUser());

        userService.deleteUser(savedUser.getIdUser());

        assertFalse(userRepo.existsById(savedUser.getIdUser()));
    }

    @Test
    public void testAddAndDeleteSession() {
        SessionControle session = new SessionControle();
        session.setIdSession(1L);



        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("mootez@gmail.com");
        user1.setPassword("azda");
        User addedUser1=userRepo.save(user1);
        assertNotNull(addedUser1.getIdUser());

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("motez@gmail.com");
        user2.setPassword("azda");
        User addedUser2=userRepo.save(user2);
        assertNotNull(addedUser2.getIdUser());

        User user3 = new User();
        user3.setUsername("user3");
        user3.setEmail("mtez@gmail.com");
        user3.setPassword("azda");
        User addedUser3=userRepo.save(user3);
        assertNotNull(addedUser3.getIdUser());


        Contribuable contribuable1 = new Contribuable();
        contribuable1.setNom("contribuable1");
        Contribuable addedcontribuable1=contribuableRepo.save(contribuable1);
        assertNotNull(addedcontribuable1.getIdContribuable());

        Contribuable contribuable2 = new Contribuable();
        contribuable2.setNom("contribuable2");
        Contribuable addedcontribuable2=contribuableRepo.save(contribuable2);
        assertNotNull(addedcontribuable2.getIdContribuable());

        Contribuable contribuable3 = new Contribuable();
        contribuable3.setNom("contribuable3");
        Contribuable addedcontribuable3=contribuableRepo.save(contribuable3);
        assertNotNull(addedcontribuable3.getIdContribuable());




        List<String> usernames = Arrays.asList(user1.getUsername(), user2.getUsername(), user3.getUsername());
        List<String> names = Arrays.asList(contribuable1.getNom(), contribuable2.getNom(), contribuable3.getNom());
        List<String> descriptions = Arrays.asList("desc1","desc2","desc3");


        SessionControle addedSession = sessionControleService.AddSession(session, usernames, names, descriptions);
        List<SessionContribuable>sessionContribuables=addedSession.getContribuablesSession();
        SessionControle savedSession = sessionControleRepo.findById(addedSession.getIdSession()).orElse(null);
        assertFalse(sessionContribuables.isEmpty());
        assertNotNull(savedSession);
        assertEquals(Etat.ENATTENT, savedSession.getEtat());
        assertEquals(3, savedSession.getControlleurs().size());
        assertEquals(3, savedSession.getContribuablesSession().size());
        sessionControleService.deleteSessionControle(addedSession.getIdSession());
        SessionControle deletedSession = sessionControleRepo.findById(addedSession.getIdSession()).orElse(null);
        assertNull(deletedSession);


        userService.deleteUser(addedUser1.getIdUser());
        assertFalse(userRepo.existsById(addedUser1.getIdUser()));

        userService.deleteUser(addedUser2.getIdUser());
        assertFalse(userRepo.existsById(addedUser2.getIdUser()));

        userService.deleteUser(addedUser3.getIdUser());
        assertFalse(userRepo.existsById(addedUser3.getIdUser()));


        contribuableService.deleteContribuable(addedcontribuable1.getIdContribuable());
        assertFalse(contribuableRepo.existsById(addedcontribuable1.getIdContribuable()));

        contribuableService.deleteContribuable(addedcontribuable2.getIdContribuable());
        assertFalse(contribuableRepo.existsById(addedcontribuable2.getIdContribuable()));

        contribuableService.deleteContribuable(addedcontribuable3.getIdContribuable());
        assertFalse(contribuableRepo.existsById(addedcontribuable3.getIdContribuable()));

        sessionContribuableRepo.deleteAll(sessionContribuables);

        for (SessionContribuable sessionContribuable : sessionContribuables) {
            assertFalse(sessionContribuableRepo.existsById(sessionContribuable.getId()));
        }
    }

}
