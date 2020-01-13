package com.mahdiyar.dosattack.service;

import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.exceptions.UserWithUsernameExistsException;
import com.mahdiyar.dosattack.exceptions.UsernameOrPasswordIncorrectException;
import com.mahdiyar.dosattack.model.dto.request.LoginRequestDto;
import com.mahdiyar.dosattack.model.dto.request.user.SignupRequestDto;
import com.mahdiyar.dosattack.model.dto.response.user.LoginResponseDto;
import com.mahdiyar.dosattack.model.dto.response.user.SignupResponseDto;
import com.mahdiyar.dosattack.model.dto.user.BriefUserDto;
import com.mahdiyar.dosattack.model.entity.UserEntity;
import com.mahdiyar.dosattack.repository.mysqlRepositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public LoginResponseDto login(LoginRequestDto request, HttpServletResponse httpServletResponse) throws UsernameOrPasswordIncorrectException {
        UserEntity userEntity = userRepository.findByUsernameAndHashedPassword(request.getUsername(), authenticationService.hash(request.getPassword()));
        if (userEntity == null)
            throw new UsernameOrPasswordIncorrectException();
        String plainToken = RandomString.make(40);
        String hashedToken = authenticationService.hash(plainToken);
        authenticationService.addUser(userEntity.getId(), hashedToken);
        Cookie cookie = new Cookie("Authorization", plainToken);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        httpServletResponse.addCookie(cookie);
        return new LoginResponseDto(userEntity.getId(), userEntity.getUsername());
    }

    public BriefUserDto get(String id) throws GeneralNotFoundException {
        UserEntity userEntity = findById(id);
        return new BriefUserDto(userEntity);
    }

    public SignupResponseDto singup(SignupRequestDto request) throws UserWithUsernameExistsException {
        if (userRepository.existsAllByUsername(request.getUsername())) {
            throw new UserWithUsernameExistsException(request.getUsername());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setHashedPassword(authenticationService.hash(request.getPassword()));
        userEntity.setAdmin(false);
        userEntity = userRepository.save(userEntity);
        return new SignupResponseDto(userEntity.getUsername());
    }

    public UserEntity findById(String id) throws GeneralNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (!optionalUserEntity.isPresent())
            throw new GeneralNotFoundException("user", "id", id);
        return optionalUserEntity.get();
    }
}
