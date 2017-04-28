package de.heavenhr.recruitement.service.exception;

/**
 * The class <code>DataException</code> is custom exception used for all data issues.
 *
 * @author Hossam Yahya
 */
public class DataException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 5193386822014963277L;

	/**
	 * Creates an instance with message string
	 * @param msg the error message
	 */
	public DataException(String msg) {
		super(msg);
	}

	/**
	 * Creates an instance with message string and an underlying exception
	 * @param msg the error message
	 * @param t the underlying exception
	 */
	public DataException(String msg, Throwable t) {
		super(msg, t);
	}
}
