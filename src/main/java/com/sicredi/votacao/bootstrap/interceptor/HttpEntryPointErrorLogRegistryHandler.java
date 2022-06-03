package com.sicredi.votacao.bootstrap.interceptor;

import com.sicredi.votacao.bootstrap.log.LogKibana;
import com.sicredi.votacao.bootstrap.log.LogLevel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class HttpEntryPointErrorLogRegistryHandler implements HandlerExceptionResolver {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        LogKibana log;
        var endpoint = httpServletRequest.getServletPath();
        var methodHttp = httpServletRequest.getMethod();

        log = new LogKibana(endpoint, methodHttp, LogLevel.ERROR, LocalDateTime.now(), Boolean.TRUE, e.getMessage());

        this.logger.info(log.toStringErrorCase());
        return null;
    }

}
