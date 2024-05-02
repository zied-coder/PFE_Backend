package pfe.spring.controller;

import pfe.spring.auth.AuthenticationResponse;
import pfe.spring.auth.AuthenticationService;
import pfe.spring.auth.RegisterRequest;
import pfe.spring.entity.User;
import pfe.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
@Transactional
@CrossOrigin("*")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/add")
    public ResponseEntity<AuthenticationResponse> addUser(
            @Valid @RequestBody RegisterRequest request, @RequestParam("libelle") String libelle) {
        return ResponseEntity.ok(authenticationService.addUser(request,libelle));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request, HttpServletResponse response
       ) throws IOException {
        authenticationService.refreshToken(request,response);
  ;
    }

    @GetMapping("/get")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @GetMapping("/getUserNumber")
    public long getUserCount(){
        return userService.getUserCount();
    }


    @GetMapping("/getUserDetails/{username}")
    public ResponseEntity<Optional<User>> getUserDetails(@PathVariable String username){
         return ResponseEntity.ok(userService.getUserDetails(username));
    }



    @PutMapping("/updateRole/{idUser}/{idRole}")

    public ResponseEntity<?> changeUserRole(@PathVariable Long idUser, @PathVariable Long idRole) {
        userService.changeUserRole(idUser, idRole);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/assign/{username}/{libelle}")

    public ResponseEntity<?> assignRoleToUser(@PathVariable String username, @PathVariable String libelle) {
        userService.assignRoleToUser(username,libelle);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    @PutMapping ("update/{userId}")
    public ResponseEntity<?> updatUser(@PathVariable Long userId,@RequestBody User updatedUser) {
        try {
            userService.updateUSer(userId,updatedUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("query") String query) {
        List<User> users = userService.searchUsers(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



}