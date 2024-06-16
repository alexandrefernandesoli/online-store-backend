package br.ufms.facom.onlinestorebackend.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public void handleError(HttpServletRequest request, HttpServletResponseWrapper response) throws IOException {
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (throwable != null) {
            throw (RuntimeException) throwable;
        } else {
            response.sendError(HttpServletResponseWrapper.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
