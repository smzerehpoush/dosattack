package com.mahdiyar.dosattack.security;

import com.mahdiyar.dosattack.common.RequestContext;
import com.mahdiyar.dosattack.exceptions.GeneralNotFoundException;
import com.mahdiyar.dosattack.exceptions.UserNotAuthenticatedException;
import com.mahdiyar.dosattack.model.entity.RequestLogEntity;
import com.mahdiyar.dosattack.model.entity.UserEntity;
import com.mahdiyar.dosattack.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RestApiAuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthenticationService authenticationService;
    private static ConcurrentHashMap<Long, Integer> ipMap = new ConcurrentHashMap<>();
    private static Random rnd;
    @Autowired
    private RequestContext requestContext;
//    @Autowired
//    private RequestLogMongoRepository requestLogMongoRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UserNotAuthenticatedException, GeneralNotFoundException {
        requestContext.setRequest(request);
        if (request == null) {
            return true;
        }
        if (request.getMethod().equals("OPTIONS")) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        String ip = resolveClientIP(request);
        addIpToMap(ip);
        requestContext.setClientIp(ip);
        RequestLogEntity requestLogEntity = new RequestLogEntity(requestContext.getClientIp(), request.getMethod(), new Date());
//        requestLogRedisRepository.save(requestLogEntity);
//        requestLogMongoRepository.save(requestLogEntity);
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(RateLimit.class)) {

            }
            if (method.isAnnotationPresent(AuthRequired.class)) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("Authorization")) {
                        UserEntity userEntity = authenticationService.authenticate(cookie.getValue());
                        requestContext.setUser(userEntity);
                    }

                }

            }
        }
        return true;
    }

    private void addIpToMap(String ip) {

        Long ipLongValue = convertIpToLong(ip);
        ipMap.put(ipLongValue, ipMap.getOrDefault(ipLongValue, 1) + 1);
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
