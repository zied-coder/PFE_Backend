package pfe.spring.service;

import pfe.spring.requests.ForgotPasswordRequest;
import pfe.spring.requests.ResetPasswordRequest;

public interface EmailService {
    public void processForgotPassword(ForgotPasswordRequest email);

    public void sendResetEmail(String email, String resetToken);

    public Boolean resetPassword(ResetPasswordRequest newPassword, String email, String resetToken);
}
