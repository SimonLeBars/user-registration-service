package registration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetUsers() throws Exception {
		mockMvc.perform(get("/users")).andExpect(status().isOk());
	}

	@Test
	public void testAddUser() throws Exception {
		String json = "{\r\n" + "    \"name\": \"Test1\",\r\n" + "    \"birthdate\": \"1914-07-31\",\r\n"
				+ "    \"countryOfResidence\": \"France\",\r\n" + "    \"phoneNumber\": \"0101010101\",\r\n"
				+ "    \"gender\": \"MALE\"\r\n" + "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetUserById() throws Exception {
		mockMvc.perform(get("/users/id=10000")).andExpect(status().isNotFound());

		String json = "{\r\n" + "    \"name\": \"Test1\",\r\n" + "    \"birthdate\": \"1914-07-31\",\r\n"
				+ "    \"countryOfResidence\": \"France\",\r\n" + "    \"phoneNumber\": \"0101010101\",\r\n"
				+ "    \"gender\": \"MALE\"\r\n" + "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());

		mockMvc.perform(get("/users/id=1")).andExpect(status().isOk());
	}

	@Test
	public void testGetUsersByName() throws Exception {
		mockMvc.perform(get("/users/name=Test2")).andExpect(status().isNotFound());

		String json = "{\r\n" + "    \"name\": \"Test2\",\r\n" + "    \"birthdate\": \"1914-07-31\",\r\n"
				+ "    \"countryOfResidence\": \"France\",\r\n" + "    \"phoneNumber\": \"0101010101\",\r\n"
				+ "    \"gender\": \"MALE\"\r\n" + "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());

		mockMvc.perform(get("/users/name=Test2")).andExpect(status().isOk());
	}

	@Test
	public void testAddUserNoName() throws Exception {
		String json = "{\r\n" + "    \"birthdate\": \"1914-07-31\",\r\n" + "    \"countryOfResidence\": \"France\"\r\n"
				+ "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testAddUserNoBirthdate() throws Exception {
		String json = "{\r\n" + "    \"name\": \"Louis de Funès\",\r\n" + "    \"countryOfResidence\": \"France\"\r\n"
				+ "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testAddUserBirthdateWrongFormat() throws Exception {
		String json = "{\r\n" + "    \"name\": \"Louis de Funès\",\r\n" + "    \"birthdate\": \"1914-07\",\r\n"
				+ "    \"countryOfResidence\": \"France\"\r\n" + "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testAddUserPhoneNumberWrongFormat() throws Exception {
		String json = "{\r\n" + "    \"name\": \"Louis de Funès\",\r\n" + "    \"birthdate\": \"1914-07-31\",\r\n"
				+ "    \"countryOfResidence\": \"France\",\r\n" + "    \"phoneNumber\": \"+33 01 01 01 01 01\",\r\n"
				+ "    \"gender\": \"MALE\"\r\n" + "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testAddUserNotAdult() throws Exception {
		String json = "{\r\n" + "    \"name\": \"Pierre\",\r\n" + "    \"birthdate\": \"2020-01-01\",\r\n"
				+ "    \"countryOfResidence\": \"France\"\r\n" + "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isForbidden());
	}

	@Test
	public void testAddUserNotFrench() throws Exception {
		String json = "{\r\n" + "    \"name\": \"Rowan Atkinson\",\r\n" + "    \"birthdate\": \"1955-01-06\",\r\n"
				+ "    \"countryOfResidence\": \"United Kingdom\"\r\n" + "}";

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isForbidden());
	}

}