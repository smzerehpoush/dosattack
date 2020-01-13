package com.mahdiyar.dosattack.service;

import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.exceptions.UserNotAuthenticatedException;
import com.mahdiyar.dosattack.model.entity.UserEntity;
import com.mahdiyar.dosattack.model.entity.UserTokenEntity;
import com.mahdiyar.dosattack.repository.redisRepositories.UserTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private UserService userService;
    private MessageDigest messageDigest;

    @Autowired
    private UserTokenRepository userTokenRepository;

    public AuthenticationService() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update("hfakljdshfakjldhfldskjahfadjhdasp;fhidsbfksauy8ery-0q234".getBytes());
        } catch (NoSuchAlgorithmException e) {
            logger.error("error in get instance of message digest", e);
        }
    }

    public UserEntity authenticate(String plainToken) throws UserNotAuthenticatedException, GeneralNotFoundException {
        Optional<UserTokenEntity> result = userTokenRepository.findById(hash(plainToken));
        if (!result.isPresent())
            throw new UserNotAuthenticatedException();
        return userService.findById(result.get().getUserId());
    }

    public void addUser(String userId, String hashedToken) {
        UserTokenEntity userTokenEntity = new UserTokenEntity(userId, hashedToken);
        userTokenRepository.save(userTokenEntity);
    }

    public String hash(String value) {
        return Base64.getEncoder().encodeToString(messageDigest.digest(value.getBytes(StandardCharsets.UTF_8)));
    }

}
