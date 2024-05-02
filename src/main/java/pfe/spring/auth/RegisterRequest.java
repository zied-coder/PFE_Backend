package pfe.spring.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;

    @Column(unique = true)
    public String username;

    @NotEmpty(message="password required")
    public String password;

    @Column(unique = true)
    @NotEmpty(message = "email required")
    @Email(message = "Email is not valid")
    public String email;

    private String prenom;

}
