package com.tenhawks.auth.exception;


import com.tenhawks.auth.bean.ApiResponse;
import com.tenhawks.auth.bean.Meta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The exception handler.
 *
 * @author Mukhtiar Ahmed
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(AlreadyRegisteredException.class)
  @ResponseBody
  public ApiResponse alreadyRegisteredExceptionHandler(AlreadyRegisteredException exception, HttpServletRequest
          request, HttpServletResponse response) {
    log.info("alreadyRegisteredExceptionHandler {}", exception.getMessage());
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage(exception.getMessage());
    apiResponse.setStatus(new Meta(HttpStatus.BAD_REQUEST));
    return apiResponse;
  }

  @ExceptionHandler(NotRegisteredException.class)
  @ResponseBody
  public ApiResponse notRegisteredExceptionHandler(NotRegisteredException exception) {
    log.info("notRegisteredExceptionHandler {}", exception.getMessage());
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage(exception.getMessage());
    apiResponse.setStatus(new Meta(HttpStatus.BAD_REQUEST));
    return apiResponse;
  }


  @ExceptionHandler(AlreadyUsedException.class)
  @ResponseBody
  public ApiResponse alreadyUsedExceptionHandler(AlreadyUsedException exception) {
    log.info("alreadyUsedExceptionHandler {}", exception.getMessage());
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage(exception.getMessage());
    apiResponse.setStatus(new Meta(HttpStatus.BAD_REQUEST));
    return apiResponse;
  }

  @ExceptionHandler(SystemException.class)
  @ResponseBody
  public ApiResponse authenticationExceptionHandler(AuthenticationException exception) {
    log.info("authenticationException {}", exception.getMessage());
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage(exception.getMessage());
    apiResponse.setStatus(new Meta(HttpStatus.UNAUTHORIZED));
    return apiResponse;
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseBody
  public ApiResponse accessDeniedExceptionHandler(AccessDeniedException exception) {
    log.info("accessDeniedExceptionHandler {}", exception.getMessage());
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage(exception.getMessage());
    apiResponse.setStatus(new Meta(HttpStatus.UNAUTHORIZED));
    return apiResponse;
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ApiResponse exceptionHandler(Exception exception) {
    log.error("exceptionHandler ", exception);
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage(exception.getMessage());
    apiResponse.setStatus(new Meta(HttpStatus.INTERNAL_SERVER_ERROR));
    return apiResponse;
  }


}