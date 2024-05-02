package pfe.spring.service;

import pfe.spring.entity.SessionContribuable;
import pfe.spring.entity.SessionControle;
import pfe.spring.entity.User;

import java.util.List;

public interface SessionControleService {

  public SessionControle AddSession(SessionControle session, List<String>usernames, List<String>names, List<String> descriptions);

     public List<SessionContribuable> getSessionContribuable (Long idSession);
     public List<User> getSessionControlleurs (Long idSession);
     public List<SessionControle> getAllSession ();
     public SessionControle getSession(Long idSession);

    public void deleteSessionControle(Long idSessionControle);

    public SessionControle updateSessionControle(Long idSession, List<String> usernames, List<String> names, List<String> descriptions, SessionControle updatedSession);

    public long getSessionCount();

    public SessionControle validateSession(Long idSession);

    public SessionControle invalidateSession(Long idSession);

    public List<SessionControle> searchSession(String query);

    public int updateProgress(Long idSession);


    ;

}
