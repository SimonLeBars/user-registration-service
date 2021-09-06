package registration.model;

import java.util.Arrays;

public enum Gender {
	FEMALE, MALE;

	public static Gender fromString(String str) {
		String normalized = str.toUpperCase();
		return Arrays.stream(Gender.values()).filter(g -> g.name().startsWith(normalized)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No gender starting with " + str));
	}
}