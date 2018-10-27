/*
 * Copyright (C) 2016 Beaver Technologies., All Rights Reserved.
 */
package com.tenhawks.auth.exception;

/**
 * This exception is thrown when an configuration error occurs.
 *
 * @author Mukhtiar Ahmed
 * @version 1.0
 */
public class ConfigurationException extends SystemException {

    /**
	 * The serial version id.
	 */
	private static final long serialVersionUID = -152203116611411058L;

	/**
     * <p>
     * This is the constructor of <code>ConfigurationException</code> class with message argument.
     * </p>
     *
     * @param message the error message.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * This is the constructor of <code>ConfigurationException</code> class with message and cause arguments.
     * </p>
     *
     * @param message the error message.
     * @param cause the cause of the exception.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
