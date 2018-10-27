package com.tenhawks.auth.exception;

/**
 * @author Mukhtiar Ahmed
 */
public class AuthenticationException extends SystemException {

  /**
   * <p>
   * This is the constructor of <code>AuthenticationException</code> class with message and cause arguments.
   * </p>
   *
   * @param exception the error message.
   */
  public AuthenticationException(String exception) {
    super(exception);
  }
  /**
   * <p>
   * This is the constructor of <code>AuthenticationException</code> class with message and cause arguments.
   * </p>
   *
   * @param message the error message.
   * @param cause the cause of the exception.
   */
  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
