package com.tenhawks.auth.exception;

/**
 * @author Mukhtiar Ahmed
 */
public class AlreadyRegisteredException extends SystemException {

  /**
   * The serial version id.
   */
  private static final long serialVersionUID = 12112526L;

  /**
   * <p>
   * This is the constructor of <code>AlreadyRegisteredException</code> class with message argument.
   * </p>
   *
   * @param message the error message.   */

  public AlreadyRegisteredException(String message) {
    super(message);
  }

  /**
   * <p>
   * This is the constructor of <code>AlreadyRegisteredException</code> class with message and cause arguments.
   * </p>
   *
   * @param message the error message.
   * @param exception the cause of the exception.
   */
  public AlreadyRegisteredException(String message, Throwable exception) {
    super(message, exception);
  }
}
