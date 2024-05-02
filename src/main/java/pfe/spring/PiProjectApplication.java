package pfe.spring;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pfe.spring.entity.Role;
import pfe.spring.entity.Status;
import pfe.spring.entity.User;
import pfe.spring.repositury.RoleRepo;
import pfe.spring.repositury.UserRepo;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "pfe.spring.repositury")
@EnableMongoRepositories(basePackages = "pfe.spring.mongo_repo")
public class PiProjectApplication {
	private final PasswordEncoder passwordEncoder;

	public PiProjectApplication(PasswordEncoder passwordEncoder){
		this.passwordEncoder=passwordEncoder;
	}
	public static void main(String[] args) {
		SpringApplication.run(PiProjectApplication.class, args);
	}
	@Bean
	public CommandLineRunner initializeRolesAndUsers(RoleRepo roleRepo, UserRepo userRepo) {
		return args -> {
			if (roleRepo.count() == 0) {
				Role initialRole = new Role();
				initialRole.setStatus(Status.Active);
				initialRole.setLibelle("SuperAdmin");
				roleRepo.save(initialRole);
				if (userRepo.findAll().isEmpty()) {
					User initialUser = new User();
					initialUser.setUsername("superAdmin");
					initialUser.setPassword(passwordEncoder.encode("azerty"));
					initialUser.setIdUser(1L);
					initialUser.setEmail("superadmin@gmail.com");
					initialUser.setName("Super");
					initialUser.setPrenom("Admin");
					initialUser.setRole(initialRole);
					userRepo.save(initialUser);
				}
			}
		};
	}


}



