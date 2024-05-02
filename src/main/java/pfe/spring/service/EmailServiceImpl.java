package pfe.spring.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pfe.spring.entity.TokenPassword;
import pfe.spring.repositury.TokenRepo;
import pfe.spring.requests.ForgotPasswordRequest;
import pfe.spring.requests.ResetPasswordRequest;
import pfe.spring.repositury.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;

    @Autowired
    UserRepo userRepo;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    public TokenRepo tokenRepo;


    @Override
    public void processForgotPassword(ForgotPasswordRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            TokenPassword token = new TokenPassword();
            token.setToken(UUID.randomUUID().toString());
            token.setUser(userRepo.findByEmail(request.getEmail()));
            token.setExpirationDate(LocalDateTime.now().plusMinutes(30));
            tokenRepo.save(token);
            sendResetEmail(request.getEmail(), token.getToken());
            log.info("Forgot password request processed successfully for email: {}", request.getEmail());
        } else {
            log.warn("Forgot password request ignored as no user found with email: {}", request.getEmail());
            throw new UsernameNotFoundException("No user found with the provided email.");
        }
    }

    public void sendResetEmail(String email, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Click the link below to reset your password:\n"
                + "http://localhost:4200/reset-Password?token=" + resetToken +"&email="+ email);

        javaMailSender.send(message);
        log.info("Password reset email sent to: {}", email);
    }

    public Boolean resetPassword(ResetPasswordRequest newPassword, String email, String resetToken) {
        Optional<TokenPassword> tokenPasswordOptional = tokenRepo.findByToken(resetToken);
        if (tokenPasswordOptional.isPresent()) {
            TokenPassword tokenPassword = tokenPasswordOptional.get();
            if (tokenPassword.getUser().getEmail().equals(email)) {
                if (!tokenPassword.getExpirationDate().isBefore(LocalDateTime.now())) {
                    tokenPassword.getUser().setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
                    tokenPassword.setExpirationDate(LocalDateTime.now().minusMinutes(1));
                    tokenRepo.save(tokenPassword);
                    log.info("Password reset successful for email: {}", email);
                    return true;
                } else {
                    log.warn("Password reset token has expired for email: {}", email);
                }
            } else {
                log.warn("Password reset request ignored as email does not match token for email: {}", email);
            }
        } else {
            log.warn("Password reset request ignored as invalid token received for email: {}", email);
        }
        return false;
    }
}
