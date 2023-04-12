package com.wellington.authserver.core;

import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwoFactorServiceController {

    @Autowired
    EmailService emailService;

    @Autowired
    DAOService daoService;

    @PutMapping(value = "/users/{userId}/emails/{email}/2fa")
    public ResponseEntity<Object> send2faCodeInEmail(@PathVariable("userId") UUID userId,
            @PathVariable("email") String email) throws AddressException, MessagingException {
        String twoFaCode = String.valueOf(new Random().nextInt(9999) + 1000);

        emailService.sendEmail(email, twoFaCode);

        daoService.update2FAProperties(userId, twoFaCode);
        return ResponseEntity.status(HttpStatus.OK).body("ok");

    }

}
