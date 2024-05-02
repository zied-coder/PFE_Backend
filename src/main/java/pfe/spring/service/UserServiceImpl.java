package pfe.spring.service;

import pfe.spring.entity.Role;
import pfe.spring.entity.User;
import pfe.spring.repositury.RoleRepo;
import pfe.spring.repositury.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    public PasswordEncoder passwordEncoder;


    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<User> getUsers() {
        log.info("Getting all users.");
        List<User> users = userRepo.findAll();
        return users;
    }

    @Override
    public void deleteUser(Long idUser) {
        log.info("Deleting user with ID: {}", idUser);
        userRepo.deleteById(idUser);
    }

    @Override
    public User updateUSer(Long idUser, User updatedUser) {
        log.info("Updating user with ID: {}", idUser);
        var user = userRepo.findById(idUser).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + idUser));

        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPrenom() != null) {
            user.setPrenom(updatedUser.getPrenom());
        }
        if (updatedUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepo.save(user);
    }

    @Override
    public User getUser(Long idUser) {
        log.info("Getting user with ID: {}", idUser);
        return userRepo.findById(idUser).orElse(null);
    }

    @Override
    public Optional<User> getUserDetails(String username) {
        log.info("Getting user details for username: {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public void changeUserRole(Long idUser, Long idRole) {
        log.info("Changing role for user with ID: {} to role ID: {}", idUser, idRole);
        Optional<User> userOptional = userRepo.findById(idUser);
        Optional<Role> roleOptional = roleRepo.findById(idRole);
        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();
            user.setRole(role);
            userRepo.save(user);
        } else {
            log.warn("User or Role not found with provided IDs: user ID: {}, role ID: {}", idUser, idRole);
            throw new RuntimeException("User not found with id " + idUser);
        }
    }

    @Override
    public void assignRoleToUser(String username, String libelle) {
        log.info("Assigning role with libelle: {} to user with username: {}", libelle, username);
        Optional<User> userOptional = userRepo.findByUsername(username);
        Optional<Role> roleOptional = roleRepo.findByLibelle(libelle);
        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();
            user.setRole(role);
            userRepo.save(user);
        } else {
            log.warn("User or Role not found with provided username: {}, role libelle: {}", username, libelle);
            throw new RuntimeException("User not found with username " + username);
        }
    }

    @Override
    public String getUserRole(String userName) {
        log.info("Getting role for user with username: {}", userName);
        Optional<User> userOptional = userRepo.findByUsername(userName);
        User user = userOptional.orElse(null);
        if (user != null && user.getRole() != null) {
            return user.getRole().getLibelle();
        }
        return "";
    }

    @Override
    public long getUserCount() {
        log.info("Getting user count.");
        return userRepo.count();
    }

    @Override
    public List<User> searchUsers(String query) {
        log.info("Searching users with query: {}", query);
        return userRepo.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query, query, query);
    }



}
