package br.ufms.facom.onlinestorebackend.exceptions;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExceptionHandlingFilter implements Filter {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.globalExceptionHandler = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getBean(GlobalExceptionHandler.class);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException((HttpServletRequest) request, (HttpServletResponse) response, e);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        ResponseEntity<Map<String, Object>> errorResponse = globalExceptionHandler.handleSecurityException(e);
        response.setStatus(errorResponse.getStatusCodeValue());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse.getBody()));
        response.getWriter().flush();
    }

    @Override
    public void destroy() {
    }
}
