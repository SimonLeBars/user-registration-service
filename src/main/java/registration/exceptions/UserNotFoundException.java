package registration.exceptions;

public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String name) {
		super("Could not find user with name : " + name);
	}

	public UserNotFoundException(Long id) {
		super("Could not find user with id : " + id);
	}
}