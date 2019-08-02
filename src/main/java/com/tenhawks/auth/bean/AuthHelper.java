package com.tenhawks.auth.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenhawks.auth.exception.ConfigurationException;
import com.tenhawks.auth.exception.EntityNotFoundException;
import com.tenhawks.auth.exception.InvalidDataException;
import com.tenhawks.auth.exception.SystemException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * This class has utilities related to application
 * @author Mukhtiar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthHelper {
    /*
     * <p>
	 * Represents the entrance message.
     * </p>
     */
    private static final String MESSAGE_ENTRANCE = "Entering method %1$s.";

    /**
     * <p>
     * Represents the exit message.
     * </p>
     */
    private static final String MESSAGE_EXIT = "Exiting method %1$s.";

    /**
     * <p>
     * Represents the error message.
     * </p>
     */
    private static final String MESSAGE_ERROR = "Error in method %1$s. Details:";

    /**
     * The object mapper.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final String ERROR_MESSAGE = "Internal Server Error";

    public static final String INVALID_REQUEST_MESSAGE = "HTTP URL or Method is not valid";

    public static final String MISSING_REQUEST_JSON_MESSAGE = "Missing Request Input JSON";

    public static final String AUTH_FAILED_MESSAGE = "Authentication Failed";

    public static final int ONE = 1;

    public static final int PAGE_SIZE = 10;

    public static final int RATING_MAX = 5;

    public static final int RATING_MIN = 1;

    public static final int STRENGTH = 12;


    public static String generateBCryptPassword(final String plainPassword) {
        return new BCryptPasswordEncoder(STRENGTH).encode(plainPassword);
    }

    public static boolean isPasswordMatched(final String plainPassword, final String bCryptHash) {
        return new BCryptPasswordEncoder(STRENGTH).matches(plainPassword, bCryptHash);
    }

    public static void main(String args[]) {
        System.out.println(generateBCryptPassword("secret"));
    }


    /**
     * It checks whether a given object is null.
     *
     * @param object the object to be checked
     * @param name the name of the object, used in the exception message
     * @throws InvalidDataException the exception thrown when the object is null
     */
    public static void checkNull(Object object, String name) throws InvalidDataException {
        if (object == null) {
            throw new InvalidDataException(name + " is not provided");
        }
    }

    /**
     * It checks whether a given string is null or empty.
     *
     * @param str the string to be checked
     * @param name the name of the object, used in the exception message
     * @throws InvalidDataException the exception thrown when the object is null
     */
    public static void checkNullOrEmpty(String str, String name) throws InvalidDataException {
        if (str == null || str.trim().isEmpty()) {
            throw new InvalidDataException("" + name + " is not provided");
        }
    }
    /**
     * It checks whether the given list is null or empty.
     *
     * @param list the list
     * @param name the name of the object, used in the exception message
     * @throws InvalidDataException  the exception thrown when the list is null or empty
     */
    public static void checkNullOrEmpty(Collection<?> list, String name) throws InvalidDataException {
        if (list == null || list.isEmpty()) {
            throw new InvalidDataException("" + name + " is not provided");
        }
    }

    /**
     * Check if the value is positive.
     *
     * @param value the value
     * @param name the name
     * @throws InvalidDataException if the value is not positive
     */
    public static void checkPositive(long value, String name) throws InvalidDataException {
        if (value <= 0) {
            throw new InvalidDataException(name + " should be a positive value.");
        }
    }

    /**
     * Check if the configuration state is true.
     *
     * @param state the state
     * @param message the error message if the state is not true
     * @throws ConfigurationException if the state is not true
     */
    public static void checkConfigState(boolean state, String message) {
        if (!state) {
            throw new ConfigurationException(message);
        }
    }

    /**
     * Check if the configuration is null or not.
     *
     * @param object the object
     * @param name the name
     * @throws ConfigurationException if the state is not true
     */
    public static void checkConfigNotNull(Object object, String name) {
        if (object == null) {
            throw new ConfigurationException(String.format("%s should be provided", name));
        }
    }

    /**
     * Check if an entity with a given ID exists.
     *
     * @param id the ID
     * @param entity the entity object
     * @throws EntityNotFoundException if the entity can not be found in DB
     */
    public static void checkEntityExist(Object entity, long id) throws EntityNotFoundException {
        if (entity == null) {
            throw new EntityNotFoundException("entity of ID=" + id + " can not be found.");
        }
    }

    /**
     * <p>
     * Logs  message with paramNames and values  of parameters  at <code>DEBUG</code> level.
     * </p>
     * @param logger  the log service.
     * @param message  the message
     * @param paramNames the names of parameters to log (not Null).
     * @param params  the values of parameters to log (not Null).
     */
    public static void logMessage(Logger logger, String message, String[] paramNames, Object[] params) {
        if (logger.isDebugEnabled()) {
            logger.debug( message + toString(paramNames, params));
        }
    }

    /**
     * <p>
     * Logs  message at <code>DEBUG</code> level.
     * </p>
     * @param logger  the log service.
     * @param message  the message
     */
    public static void logMessage(Logger logger, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    /**
     * <p>
     * Logs  message at <code>WARN</code> level.
     * </p>
     * @param logger the log service.
     * @param message  the message
     */
    public static void logWarn(Logger logger, String message) {
        logger.warn(message);
    }



    /**
     * <p>
     * Logs for entrance into public methods at <code>DEBUG</code> level.
     * </p>
     *
     * @param logger the log service.
     * @param signature the signature.
     * @param paramNames the names of parameters to log (not Null).
     * @param params the values of parameters to log (not Null).
     */
    public static void logEntrance(Logger logger, String signature, String[] paramNames, Object[] params) {
        if (logger.isDebugEnabled()) {
            String msg = String.format(MESSAGE_ENTRANCE, signature) + toString(paramNames, params);
            logger.debug(msg);
        }
    }

    /**
     * <p>
     * Logs for exit from public methods at <code>DEBUG</code> level.
     * </p>
     *
     * @param logger the log service.
     * @param signature the signature of the method to be logged.
     * @param value the return value to log.
     */
    public static void logExit(Logger logger, String signature, Object value) {
        if (logger.isDebugEnabled()) {
            StringBuilder msg = new  StringBuilder(String.format(MESSAGE_EXIT, signature));
            if (value != null) {
                msg.append(" Output parameter : ").append(value);
            }
            logger.debug(msg);
        }
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level.
     * </p>
     *
     * @param <T> the exception type.
     * @param logger the log service.
     * @param signature the signature of the method to log.
     * @param e the exception to log.
     */
    public static <T extends Throwable> void logException(Logger logger, String signature, T e) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_ERROR, signature))
                .append(e.getClass().getName())
                .append(": ")
                .append(e.getMessage());
        Throwable cause = e.getCause();
        final String lineSeparator = System.getProperty("line.separator");
        while (null != cause) {
            sb.append(lineSeparator)
                    .append(" Caused By -> ")
                    .append(cause.getClass().getName())
                    .append(": ")
                    .append(cause.getMessage());
            cause = cause.getCause();
        }

        if(e instanceof SystemException) {
            logger.error(sb.toString());
        } else {
            logger.error(sb.toString(), e);
        }

    }

    /**
     * <p>
     * Converts the parameters to string.
     * </p>
     *
     * @param paramNames the names of parameters.
     * @param params the values of parameters.
     * @return the string
     */
    private static String toString(String[] paramNames, Object[] params) {
        StringBuilder sb = new StringBuilder(" Input parameters: {");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(paramNames[i]).append(" -> ").append(toString(params[i]));
            }
        }
        sb.append("}.");
        return sb.toString();
    }

    /**
     * <p>
     * Converts the object to string.
     * </p>
     *
     * @param obj the object
     * @return the string representation of the object.
     */
    public static String toString(Object obj) {
        String result;
        try {
            if (obj instanceof HttpServletRequest) {
                result = "Spring provided servlet request";
            } else if (obj instanceof HttpServletResponse) {
                result = "Spring provided servlet response";
            } else if (obj instanceof ModelAndView) {
                result = "Spring provided model and view object";
            } else {
                result = MAPPER.writeValueAsString(obj);
            }

        } catch (JsonProcessingException e) {
            result = "The object can not be serialized by Jackson JSON mapper, error: " + e.getMessage();
        }
        return result;
    }
    /**
     * Send the object in JSON response.
     * @param object the to
     * @param response the HttpServletResponse response
     * @throws IOException
     */
    public static void sendJsonResponse(Object object, HttpServletResponse response) throws IOException {

        String jsonResponse;
        try {

            jsonResponse =  MAPPER.writeValueAsString(object);

        } catch (JsonProcessingException e1) {
            jsonResponse = "The object can not be serialized by Jackson JSON mapper";
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(jsonResponse);
        response.getWriter().close();
    }

    /**
     * Build the error response.
     *
     * @param e the exception
     * @param status the error status
     * @param message the error message
     * @return the error response
     */
    public static Map<String, Object> buildErrorResponse(Throwable e, int status, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", new Date().getTime());
        result.put("status", status);
        result.put("exception", e.getClass().getName());
        result.put("message", message);
        return result;
    }


    /**
     * Build the error response.
     *
     * @param status the error status
     * @param message the error message
     * @return the error response
     */
    public static Map<String, Object> buildErrorResponse(int status, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", new Date().getTime());
        result.put("status", status);
        result.put("message", message);
        return result;
    }

    /**
     *  Send the error response.
     *
     * @param e the exception
     * @param status the error status
     * @param response the HttpServletResponse response
     * @throws IOException
     */
    public static void sendJsonErrorResponse(Throwable e, int status, HttpServletResponse response) throws IOException {

        String errorMessage;
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", new Date().getTime());
        result.put("status", status );
        result.put("exception", e.getClass().getName());
        result.put("message", e.getMessage());

        try {
            errorMessage =  MAPPER.writeValueAsString(result);
        } catch (JsonProcessingException e1) {
            errorMessage = "The object can not be serialized by Jackson JSON mapper, error: " + e.getMessage();
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(errorMessage);
        response.getWriter().close();

    }

    /**
     * To get unique key String
     * @return unique key String
     */
    public static String getUniqueKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * Get Today date without time.
     * @return the Today Date.
     */
    public static Date getTodayDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis();
        return new Date(time);
    }




}
