package de.heavenhr.recruitement.service.exception;

/**
 * The class <code>NoDataFoundException</code> is custom exception used when no data is found.
 *
 * @author Hossam Yahya
 */
public class NoDataFoundException extends Exception {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 5193386822015628477L;

	/**
	 * Creates an instance with message string
	 * @param msg the error message
	 */
	public NoDataFoundException(String msg) {
		super(msg);
	}

	/**
	 * Creates an instance with message string and an underlying exception
	 * @param msg the error message
	 * @param t the underlying exception
	 */
	public NoDataFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}