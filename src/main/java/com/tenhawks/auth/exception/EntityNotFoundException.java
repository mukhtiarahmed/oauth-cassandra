/*
 * Copyright (C) 2016 Beaver Technologies., All Rights Reserved.
 */
package com.tenhawks.auth.exception;

/**
 * This is exception is thrown when an entity can not be found in the persistence.
 *
 * @author Mukhtiar Ahmed
 * @version 1.0
 */
public class EntityNotFoundException extends SystemException {

    /**
	 * The serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
     * <p>
     * This is the constructor of <code>EntityNotFoundException</code> class with message argument.
     * </p>
     *
     * @param message the error message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * <p>
     * This is the constructor of <code>EntityNotFoundException</code> class with message and cause arguments.
     * </p>
     *
     * @param message the error message.
     * @param cause the cause of the exception.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
