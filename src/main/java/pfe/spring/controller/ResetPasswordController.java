package pfe.spring.controller;


import pfe.spring.requests.ForgotPasswordRequest;

import pfe.spring.service.EmailService;
import pfe.spring.requests.MessageRequests;
import pfe.spring.requests.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@RestController
@RequestMapping("/Reset-Password/")
@RequiredArgsConstructor
@Transactional
@CrossOrigin("*")
@Slf4j
public class ResetPasswordController {

    @Autowired
    EmailService emailService;


    @PostMapping("/forget")
    public ResponseEntity<MessageRequests> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        emailService.processForgotPassword(request);
        return ResponseEntity.ok(new MessageRequests("Password reset initiated successfully. Please check your email"));
    }

    @PostMapping("/reset")
    public ResponseEntity<MessageRequests> resetPassword(@RequestBody ResetPasswordRequest newPassword, @RequestParam("email") String email, @RequestParam("token") String resetToken) {
        Boolean b = emailService.resetPassword(newPassword, email, resetToken);
        if (b) {
            return ResponseEntity.ok().body(new MessageRequests("password changed"));
        } else {
            return ResponseEntity.badRequest().body(new MessageRequests("wrong token"));
        }
    }


}
