package registration.exceptions;

/**
 * 
 * Exception raised on attempt to add a user without name, birth date or country
 * of residence
 * 
 * @author simon
 *
 */
public class UserMandatoryAttributeIsNullException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserMandatoryAttributeIsNullException(String attribute) {
		super("Mandatory attribute was not provided : " + attribute);
	}
}
