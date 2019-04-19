package test.helpers.strings;

import java.security.SecureRandom;
import java.util.Random;

public class RandomStringGenerator {
    private static final int STRING_LENGTH = 8;
    private static final Random RANDOM = new SecureRandom();
    private static final String SOURCE = "0123456789ABCDEFGHIJKLMNOPRSTUVWXYZabcdefghijklmnoprstuvwxyz";

    public static String generate() //generates a random alphanum string
    {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < STRING_LENGTH; i++) {
            str.append(SOURCE.charAt(RANDOM.nextInt(SOURCE.length())));
        }

        return new String(str);
    }
}
