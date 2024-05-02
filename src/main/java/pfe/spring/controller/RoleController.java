package pfe.spring.controller;



import pfe.spring.entity.Role;
import pfe.spring.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Role/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/save")
    public Role save(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    @PostMapping("/affectRoleToUser/{username}/{libelle}")
    public void affectRoleToUser(@PathVariable String username, @PathVariable String libelle) {
         roleService.affectRoleToUser(username,libelle);
    }

    @GetMapping("/getRoleCount")
    public long getUserCount(){
        return roleService.getRoleCount();
    }
    @GetMapping("/getAll")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
