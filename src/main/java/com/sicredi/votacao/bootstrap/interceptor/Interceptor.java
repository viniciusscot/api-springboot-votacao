package com.sicredi.votacao.bootstrap.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.bootstrap.log.LogKibana;
import com.sicredi.votacao.bootstrap.log.LogLevel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class Interceptor implements HandlerInterceptor {

    protected final Log logger = LogFactory.getLog(this.getClass());
    private final ObjectMapper objectMapper;

    public Interceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        if (response.getStatus() != HttpServletResponse.SC_OK)
            return;

        LogKibana log;
        var endpoint = request.getServletPath();
        var methodHttp = request.getMethod();

        log = new LogKibana(endpoint, methodHttp, LogLevel.INFO, LocalDateTime.now(), Boolean.FALSE, "");

        this.logger.info(log.toStringSucefullCase());
    }

}
