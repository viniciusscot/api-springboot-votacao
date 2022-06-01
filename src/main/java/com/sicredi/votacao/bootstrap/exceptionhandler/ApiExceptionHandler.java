package com.sicredi.votacao.bootstrap.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.sicredi.votacao.bootstrap.exceptions.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
            = "An unexpected internal system error has occurred. Try again and if "
            + "the problem persists, contact your system administrator.";

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "One or more fields are invalid. Please fill in correctly and try again.";

        BindingResult bindingResult = ex.getBindingResult();

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return new Problem.Object()
                            .setName(name)
                            .setUserMessage(message);
                })
                .collect(Collectors.toList());

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(detail)
                .setObjects(problemObjects);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.SYSTEM_ERROR;
        String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(detail);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {

        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = String.format("The resource %s you tried to access is non-existent.",
                ex.getRequestURL());

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(@NotNull TypeMismatchException ex, @NotNull HttpHeaders headers,
                                                        @NotNull HttpStatus status, @NotNull WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_PARAMETER;

        String detail = String.format("The URL parameter '%s' received the value '%s',"
                        + "which is of an invalid type. Correct and enter a value compatible with type %s.",
                ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException ex,
                                                                  @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.MESSAGE_INCOMPREHENSIBLE;
        String detail = "The request body is invalid. Check syntax error.";

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MESSAGE_INCOMPREHENSIBLE;
        String detail = String.format("Property '%s' does not exist. "
                + "Correct or remove this property and try again.", path);

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
                                                       HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MESSAGE_INCOMPREHENSIBLE;
        String detail = String.format("Property '%s' received value '%s',"
                        + "which is of an invalid type. Correct and enter a value compatible with type %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex,
                                                       WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(detail);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntityInUse(EntityInUseException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(detail);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(VoteAlreadyComputedException.class)
    public ResponseEntity<Object> handleVoteAlreadyComputed(VoteAlreadyComputedException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.VOTE_ALREADY_COMPUTED;
        String detail = ex.getMessage();

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(detail);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(VoteNotAuthorizedException.class)
    public ResponseEntity<Object> handleVoteNotAuthorized(VoteNotAuthorizedException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.VOTE_NOT_AUTHORIZED;
        String detail = ex.getMessage();

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(detail);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.BUSINESS_ERROR;
        String detail = ex.getMessage();

        Problem problem = createProblem(status, problemType, detail)
                .setUserMessage(detail);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NotNull Exception ex, Object body, @NotNull HttpHeaders headers,
                                                             @NotNull HttpStatus status, @NotNull WebRequest request) {

        if (body == null) {
            body = new Problem()
                    .setTimestamp(LocalDateTime.now())
                    .setTitle(status.getReasonPhrase())
                    .setStatus(status.value())
                    .setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
        } else if (body instanceof String) {
            body = new Problem()
                    .setTimestamp(LocalDateTime.now())
                    .setTitle((String) body)
                    .setStatus(status.value())
                    .setUserMessage(MSG_ERRO_GENERICA_USUARIO_FINAL);
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem createProblem(HttpStatus status, ProblemType problemType, String detail) {

        return new Problem()
                .setTimestamp(LocalDateTime.now())
                .setStatus(status.value())
                .setType(problemType.getUri())
                .setTitle(problemType.getTitle())
                .setDetail(detail);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

}
