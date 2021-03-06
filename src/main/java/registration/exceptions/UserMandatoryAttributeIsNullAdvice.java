package registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UserMandatoryAttributeIsNullAdvice {

	@ResponseBody
	@ExceptionHandler(UserMandatoryAttributeIsNullException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String employeeNotFoundHandler(UserMandatoryAttributeIsNullException e) {
		return e.getMessage();
	}
}
