package com.mahdiyar.dosattack.service;

import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.exceptions.UserNotAuthenticatedException;
import com.mahdiyar.dosattack.model.entity.mysql.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private UserService userService;
    private static final ConcurrentHashMap<String, String> userTokens = new ConcurrentHashMap<>();
    private MessageDigest messageDigest;

    public AuthenticationService() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update("hfakljdshfakjldhfldskjahfadjhdasp;fhidsbfksauy8ery-0q234".getBytes());
        } catch (NoSuchAlgorithmException e) {
            logger.error("error in get instance of message digest", e);
        }
    }

    public UserEntity authenticate(String plainToken) throws UserNotAuthenticatedException, GeneralNotFoundException {
        String result = userTokens.get(hash(plainToken));
        if (result == null)
            throw new UserNotAuthenticatedException();
        return userService.findById(result);
    }

    public void addUser(String userId, String hashedToken) {
        userTokens.put(hashedToken, userId);
    }

    public String hash(String value) {
        return Base64.getEncoder().encodeToString(messageDigest.digest(value.getBytes(StandardCharsets.UTF_8)));
    }

}
