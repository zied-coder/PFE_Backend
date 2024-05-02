package pfe.spring.service;


import pfe.spring.entity.Role;
import pfe.spring.entity.User;
import pfe.spring.repositury.RoleRepo;
import pfe.spring.repositury.UserRepo;
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
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Role addRole(Role role) {
        log.info("Adding Role: {}", role);
        return roleRepo.save(role);
    }

    @Override
    public void deleteRole(long idRole) {
        log.info("Deleting Role with ID: {}", idRole);
        Role role = roleRepo.findById(idRole).orElse(null);
        roleRepo.delete(role);
    }

    @Override
    public void affectRoleToUser(String username, String libelle) {
        log.info("Affecting Role with Libelle: {} to User with Username: {}", libelle, username);
        Role role= roleRepo.findByLibelle(libelle).orElse(null);
        User user= userRepo.findByUsername(username).orElse(null);
        user.setRole(role);
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("Getting all Roles");
        return roleRepo.findAll();
    }

    @Override
    public long getRoleCount() {
        log.info("Getting Role count");
        return roleRepo.count() ;
    }
}
