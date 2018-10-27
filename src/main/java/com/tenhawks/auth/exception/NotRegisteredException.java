package com.tenhawks.auth.exception;

/**
 *  This exception is thrown when user not registered.
 * @author Mukhtiar
 */
public class NotRegisteredException extends SystemException {

  /**
   * <p>
   * This is the constructor of <code>NotRegisteredException</code> class with message and cause arguments.
   * </p>
   *
   * @param message the error message.
   * @param exception the cause of the exception.
   */
  public NotRegisteredException(String message, Throwable exception) {
    super(message, exception);

  }

  /**
   * <p>
   * This is the constructor of <code>NotRegisteredException</code> class with exception and cause arguments.
   * </p>
   *
   * @param exception the error exception.
   */
  public NotRegisteredException(String exception) {
    super(exception);
  }

}
