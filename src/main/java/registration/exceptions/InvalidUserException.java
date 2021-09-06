package registration.exceptions;

public class InvalidUserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidUserException(int age, String countryOfResidence) {
		super("User is not an adult French resident. Age : " + age + ", country of residence : " + countryOfResidence
				+ ".");
	}
}
