package registration.exceptions;

/**
 * 
 * Exception raised when a phone number is not in french format
 * 
 * @author simon
 *
 */
public class WrongPhoneNumberFormatException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongPhoneNumberFormatException(String phoneNumber) {
		super("Wrong phone number format : " + phoneNumber
				+ ", french format is '+33 1 01 01 01 01' or '01 01 01 01 01'");
	}
}
