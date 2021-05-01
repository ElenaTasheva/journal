package com.example.my_bullet_journal.web.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class ErrorPageInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ErrorPageInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {

        HttpServletRequest requestCacheWrapperObject
                = new ContentCachingRequestWrapper(request);
        requestCacheWrapperObject.getParameterMap();
        // Read inputStream from requestCacheWrapperObject and log it
        
        logger.error("Error!!! {}", requestCacheWrapperObject.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        return true;
    }

}
