package registration.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import registration.exceptions.InvalidUserException;
import registration.exceptions.UserMandatoryAttributeIsNullException;
import registration.exceptions.UserNotFoundException;
import registration.exceptions.WrongPhoneNumberFormatException;
import registration.logging.LogProcessingTime;
import registration.model.User;
import registration.model.UserRepository;

@RestController
public class UserController {

	private final UserRepository repository;

	UserController(UserRepository repository) {
		this.repository = repository;
	}

	/**
	 * 
	 * @return details of every Users in the database
	 */
	@GetMapping("/users")
	@LogProcessingTime
	List<User> all() {
		return repository.findAll();
	}

	/**
	 * Add a new User to the database
	 * 
	 * @param newUser
	 * @return
	 */
	@PostMapping("/users")
	@LogProcessingTime
	User newUser(@RequestBody User newUser) {
		// Check mandatory attributes
		mandatoryAttributesNotNull(newUser);

		// Check is adult French resident
		isAdultFrenchResident(newUser.getBirthdate(), newUser.getCountryOfResidence());

		// Check phone number format
		phoneNumberIsWellFormatted(newUser.getPhoneNumber());

		return repository.save(newUser);
	}

	/**
	 * 
	 * @param id
	 * @return details of the user with id {id}
	 */
	@GetMapping("/users/id={id}")
	@LogProcessingTime
	User one(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	/**
	 * @param name
	 * @return details of the users in the database with the name {name}
	 */
	@GetMapping("/users/name={name}")
	@LogProcessingTime
	List<User> multiple(@PathVariable String name) {
		List<User> users = repository.findAll().stream()
				.filter(u -> u.getName().toLowerCase().replace(" ", "").equals(name.toLowerCase().replace(" ", "")))
				.collect(Collectors.toList());

		if (users.isEmpty())
			throw new UserNotFoundException(name);

		return users;
	}

	private void mandatoryAttributesNotNull(User newUser) {
		if (newUser.getName() == null || newUser.getName().isEmpty())
			throw new UserMandatoryAttributeIsNullException("name");
		if (newUser.getBirthdate() == null)
			throw new UserMandatoryAttributeIsNullException("birth date");
		if (newUser.getCountryOfResidence() == null || newUser.getCountryOfResidence().isEmpty())
			throw new UserMandatoryAttributeIsNullException("country of residence");
	}

	private void isAdultFrenchResident(Date birthDate, String countryOfResidence) {
		LocalDate now = LocalDateTime.now().toLocalDate();
		LocalDate birth = LocalDateTime.ofInstant(birthDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
		int age = Period.between(birth, now).getYears();
		if (age < 18 || !countryOfResidence.equals("France"))
			throw new InvalidUserException(age, countryOfResidence);
	}

	private void phoneNumberIsWellFormatted(String phoneNumber) {
		Pattern phoneNumberPattern = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
		Matcher phoneNumberMatcher = phoneNumberPattern.matcher(phoneNumber);
		if (!phoneNumberMatcher.matches())
			throw new WrongPhoneNumberFormatException(phoneNumber);
	}
}