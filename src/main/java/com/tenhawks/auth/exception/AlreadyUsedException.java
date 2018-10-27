package com.tenhawks.auth.exception;


/**
 * @author Mukhtiar
 */
public class AlreadyUsedException extends SystemException {

  /**
   * The serial version id.
   */
  private static final long serialVersionUID = 12112526L;

  /**
   * <p>
   * This is the constructor of <code>AlreadyUsedException</code> class with message argument.
   * </p>
   *
   * @param exception the error message.
   */
  public AlreadyUsedException(final String exception) {
    super(exception);
  }

  /**
   * <p>
   * This is the constructor of <code>AlreadyUsedException</code> class with message and cause arguments.
   * </p>
   *
   * @param message the error message.
   * @param exception the cause of the exception.
   */
  public AlreadyUsedException(String message, Throwable exception) {
    super(message, exception);
  }
}
