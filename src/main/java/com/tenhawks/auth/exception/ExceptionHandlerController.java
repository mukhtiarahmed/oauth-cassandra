package com.tenhawks.auth.exception;


import com.tenhawks.auth.bean.ApiResponse;
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
@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(AlreadyRegisteredException.class)
  @ResponseBody
  public ApiResponse alreadyRegisteredExceptionHandler(AlreadyRegisteredException exception, HttpServletRequest
      request, HttpServletResponse response) {
  
    return new ApiResponse();
  }

  @ExceptionHandler(NotRegisteredException.class)
  @ResponseBody
  public ApiResponse notRegisteredExceptionHandler(NotRegisteredException exception, HttpServletRequest
      request, HttpServletResponse response) {
   
    return new ApiResponse();
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ApiResponse exceptionHandler(Exception exception, HttpServletRequest
      request, HttpServletResponse response) {
   
    return new ApiResponse();
  }


  @ExceptionHandler(AlreadyUsedException.class)
  @ResponseBody
  public ApiResponse alreadyUsedExceptionHandler(AlreadyUsedException exception,
                                                 HttpServletRequest
                                                                     request, HttpServletResponse response) {
   
    return new ApiResponse();
  }
  
  @ExceptionHandler(SystemException.class)
  @ResponseBody
  public ApiResponse authenticationExceptionHandler(AuthenticationException exception,
                                                    HttpServletRequest request, HttpServletResponse response) {
    
    return new ApiResponse();
  }
  
 
}
