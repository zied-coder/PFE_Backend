package pfe.spring.service;

import pfe.spring.entity.Role;

import java.util.List;

public interface RoleService {
    public Role addRole(Role role);
    public void deleteRole(long idRole);

    public void affectRoleToUser(String username, String libelle);

    public List<Role> getAllRoles();

    public long getRoleCount();
}
