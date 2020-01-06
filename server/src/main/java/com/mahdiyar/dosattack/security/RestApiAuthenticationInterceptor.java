package com.mahdiyar.dosattack.security;

import com.mahdiyar.dosattack.common.RequestContext;
import com.mahdiyar.dosattack.model.entity.RequestLogEntity;
import com.mahdiyar.dosattack.repository.mongoRepositories.RequestLogMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

@Component
public class RestApiAuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private RequestLogMongoRepository requestLogMongoRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        requestContext.setRequest(request);
        if (request == null) {
            return true;
        }
        if (request.getMethod().equals("OPTIONS")) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        resolveClientIP(request);
        RequestLogEntity requestLogEntity = new RequestLogEntity(requestContext.getClientIp(), request.getMethod(), new Date());
//        requestLogRedisRepository.save(requestLogEntity);
        requestLogMongoRepository.save(requestLogEntity);
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(RateLimit.class)) {

            }
//            if (method.isAnnotationPresent(AuthRequired.class) && method.isAnnotationPresent(ActiveUserRequired.class)) {
//                AuthRequired authRequired = method.getAnnotation(AuthRequired.class);
//                SessionEntity sessionEntity = sessionService.validateToken(requestContext.getTokenStr(), authRequired.roles(), requestContext.getClientIp());
//                checkUserIsActive(sessionEntity);
//                requestContext.setSession(sessionEntity);
//            } else if (method.isAnnotationPresent(AuthRequired.class)) {
//                AuthRequired authRequired = method.getAnnotation(AuthRequired.class);
//                SessionEntity sessionEntity = sessionService.validateToken(requestContext.getTokenStr(), authRequired.roles(), requestContext.getClientIp());
//                requestContext.setSession(sessionEntity);
//            } else if (method.isAnnotationPresent(ActiveUserRequired.class)) {
//                SessionEntity sessionEntity = sessionService.validateSession(requestContext.getTokenStr(), requestContext.getClientIp());
//                checkUserIsActive(sessionEntity);
//            }
        }
        return true;
    }

    private void resolveClientIP(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (StringUtils.isEmpty(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        requestContext.setClientIp(remoteAddr);
    }
}
