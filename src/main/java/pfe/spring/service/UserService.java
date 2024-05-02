package pfe.spring.service;

import pfe.spring.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {


    public User addUser(User user);
    public List<User> getUsers();

    public void deleteUser(Long idUser);

    public User updateUSer(Long idUser,User updatedUser);

    public User getUser(Long idUser);

    public Optional<User> getUserDetails(String username);

    public void changeUserRole(Long idUser, Long idRole);

    public void assignRoleToUser(String username, String libelle);


    public String getUserRole(String  userName);

    public long getUserCount();

    public List<User> searchUsers(String query);


}
