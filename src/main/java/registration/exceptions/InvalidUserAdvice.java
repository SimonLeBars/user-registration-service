package registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidUserAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidUserException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String employeeNotFoundHandler(InvalidUserException e) {
		return e.getMessage();
	}
}
