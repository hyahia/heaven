package de.heavenhr.recruitement.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.heavenhr.recruitement.service.exception.DataException;
import de.heavenhr.recruitement.service.exception.NoDataFoundException;

/**
 * The class <code>GlobalExceptionContoller</code> contains basic exception handling.
 *
 * @author Hossam Yahya
 */
@ControllerAdvice
class GlobalExceptionContoller {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
		
    /**
     * Handles DataException
     */
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data issue")	// 409
	@ExceptionHandler(DataException.class)
    public void handleDataException() {
    	logger.info("Throw DataException");
    }
    
    /**
     * Handles NoDataFoundException
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Data Found")	// 404
	@ExceptionHandler(NoDataFoundException.class)
    public void handleNoDataFoundException() {
    	logger.info("Throw NoDataFoundException");
    }
    
    /**
     * Handles other exceptions
     */
	@ExceptionHandler(Exception.class)
    public void handleUnhandledException(Exception exception) throws Exception {
		logger.info("UnhandledException happened: " + exception.getMessage());
		throw exception;
	}
}