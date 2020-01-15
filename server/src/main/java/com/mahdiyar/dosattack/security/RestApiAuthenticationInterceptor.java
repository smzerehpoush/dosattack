package com.mahdiyar.dosattack.security;

import com.mahdiyar.dosattack.common.RequestContext;
import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.exceptions.UserNotAuthenticatedException;
import com.mahdiyar.dosattack.model.entity.UserEntity;
import com.mahdiyar.dosattack.service.AuthenticationService;
import com.mahdiyar.dosattack.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@Slf4j
public class RestApiAuthenticationInterceptor extends HandlerInterceptorAdapter {
    private static final Integer DEFAULT_SIZE = 5000;
    private static final Integer LIMIT = 500;
    @Autowired
    private AuthenticationService authenticationService;
    private static ConcurrentHashMap<Long, Integer> ipMap = new ConcurrentHashMap<>(DEFAULT_SIZE);
    private static Set<Long> ipBlackList = new CopyOnWriteArraySet<>();
    private static Random rnd;
    @Autowired
    private RequestContext requestContext;

    //    @Autowired
//    private RequestLogMongoRepository requestLogMongoRepository;
    @Scheduled(fixedRate = 3000)
    public void emptyIpMap() {
        logger.info("clearing ip map after 3 seconds.");
        ipMap.clear();
    }

    @Scheduled(fixedRate = 30000)
    public void emptyBlockedIpList() {
        logger.info("clearing ip block list after 30 seconds.");
        ipBlackList.clear();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UserNotAuthenticatedException, GeneralNotFoundException {
        requestContext.setRequest(request);
        if (request == null) {
            return false;
        }
        if (request.getMethod().equals("OPTIONS")) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        String ip = resolveClientIP(request);
        Long ipLongValue = convertIpToLong(ip);
        ipMap.put(ipLongValue, ipMap.getOrDefault(ipLongValue, 1) + 1);
        requestContext.setClientIp(ip);
//        RequestLogEntity requestLogEntity = new RequestLogEntity(requestContext.getClientIp(), request.getMethod(), new Date());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(RateLimit.class)) {
                if (dropRequest(ipLongValue)) {
                    return false;
                }
            }
            if (method.isAnnotationPresent(AuthRequired.class)) {
                Cookie[] cookies = request.getCookies();
                if (cookies == null)
                    throw new UserNotAuthenticatedException();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(Constants.AUTHORIZATION)) {
                        UserEntity userEntity = authenticationService.authenticate(cookie.getValue());
                        if (method.getAnnotation(AuthRequired.class).admin()) {
                            if (!userEntity.isAdmin())
                                throw new UserNotAuthenticatedException();
                        }
                        requestContext.setUser(userEntity);
                    }

                }

            }
        }
        return true;
    }

    private boolean dropRequest(Long ipLongValue) {
        if (ipBlackList.contains(ipLongValue)) {
//            System.err.println("ip is in black list.");
            return true;
        }
        if (ipMap.getOrDefault(ipLongValue, 1).compareTo(LIMIT) >= 0) {
            System.err.println("adding ip to block list.");
            ipBlackList.add(ipLongValue);
            return true;
        }
        return false;
    }


    private String resolveClientIP(HttpServletRequest request) {
        rnd = new Random(System.currentTimeMillis());
        switch (rnd.nextInt(4)) {
            case 0:
                return "127.0.2.21";
            case 1:
                return "54.36.214.65";
            case 2:
                return "65.90.24.26";
            case 3:
                return "43.76.41.89";
            default:
                return "0.0.0.0";
        }
//        return request.getRemoteAddr().isEmpty() ? "0.0.0.0" : request.getRemoteAddr().split(".").length == 4 ? request.getRemoteAddr() : "0.0.0.0";
    }

    private Long convertIpToLong(String ip) {
        String[] ipParts = ip.split("\\.");
        Long value = 0L;
        for (int i = 0; i < ipParts.length; i++) {
            value += (long) Math.pow(256, ipParts.length - 1d - i) * Integer.parseInt(ipParts[i]);
        }
        return value;
    }
}
