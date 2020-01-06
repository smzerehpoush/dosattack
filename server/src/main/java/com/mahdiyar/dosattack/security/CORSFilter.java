package com.mahdiyar.dosattack.security;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestOrigin = ((HttpServletRequest) servletRequest).getHeader("Origin");
        if (requestOrigin != null) {
            response.setHeader("Access-Control-Allow-Origin", requestOrigin);
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Origin, Authorization ");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Content-Type, Origin, Accept, X-Requested-With, Authorization");
        if (((HttpServletRequest) servletRequest).getMethod().equals("OPTIONS")) {
            servletResponse.getWriter().print("OK");
            servletResponse.getWriter().flush();
            return;
        }

        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy() {

    }
}
