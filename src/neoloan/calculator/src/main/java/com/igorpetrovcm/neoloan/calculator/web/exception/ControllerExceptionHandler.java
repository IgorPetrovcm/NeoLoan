package com.igorpetrovcm.neoloan.calculator.web.exception;


import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        ProblemDetail problemDetail = handleValidationException(ex);
        return ResponseEntity.status(status.value()).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        ProblemDetail problemDetail = handleHttpMessageNotReadableException(ex, status, request);
        return ResponseEntity.status(status.value()).body(problemDetail);
    }

    private ProblemDetail handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            HttpStatusCode statusCode,
            WebRequest request
    ){
        String details = ex.getMessage();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(statusCode, details);
        problemDetail.setTitle("Bad request");
        problemDetail.setInstance(URI.create(request.getContextPath()));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    private ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        String details = getErrorsDetails(ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getStatusCode(), details);
        problemDetail.setTitle("Bad request");
        problemDetail.setInstance(ex.getBody().getInstance());
        problemDetail.setProperty("timestamp", Instant.now()); // adding more data using the Map properties of the ProblemDetail
        return problemDetail;
    }

    private String getErrorsDetails(MethodArgumentNotValidException ex) {
        return Optional.ofNullable(ex.getDetailMessageArguments())
                .map(args -> Arrays.stream(args)
                        .filter(x -> !Objects.isNull(x))
                        .reduce("Please make sure to provide a valid request, ",
                                (a, b) -> a + " " + b)
                ).orElse("").toString();
    }
}
