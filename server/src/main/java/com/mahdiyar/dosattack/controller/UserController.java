package com.mahdiyar.dosattack.controller;

import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.exceptions.UserWithUsernameExistsException;
import com.mahdiyar.dosattack.exceptions.UsernameOrPasswordIncorrectException;
import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.model.dto.request.user.LoginRequestDto;
import com.mahdiyar.dosattack.model.dto.request.user.SignupRequestDto;
import com.mahdiyar.dosattack.model.dto.response.user.LoginResponseDto;
import com.mahdiyar.dosattack.model.dto.response.user.SignupResponseDto;
import com.mahdiyar.dosattack.model.dto.user.BriefUserDto;
import com.mahdiyar.dosattack.security.AuthRequired;
import com.mahdiyar.dosattack.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Api("User Controller")
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "User Signup", notes = "No Auth Required.")
    @PostMapping("/signup")
    public RestResponse<SignupResponseDto> signup(@RequestBody SignupRequestDto request) throws UserWithUsernameExistsException {
        return RestResponse.ok(userService.singup(request));
    }

    @ApiOperation(value = "User Login", notes = "No Auth Required.")
    @PostMapping("/login")
    public RestResponse<LoginResponseDto> login(@RequestBody LoginRequestDto request, HttpServletResponse httpServletResponse) throws UsernameOrPasswordIncorrectException {
        return RestResponse.ok(userService.login(request, httpServletResponse));

    }

    @ApiOperation(value = "Get User Info", notes = "Auth Required.")
    @AuthRequired
    @GetMapping("/{userId}")
    public RestResponse<BriefUserDto> get(@PathVariable("userId") String id) throws GeneralNotFoundException {
        return RestResponse.ok(userService.get(id));
    }
}
