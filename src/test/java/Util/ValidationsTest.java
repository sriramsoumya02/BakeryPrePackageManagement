package Util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationsTest {

    @Test
    public void validateIsParsableInteger() {
        assertAll(
                () -> assertTrue(Validations.isParsableInteger("123")),
                () -> assertFalse(Validations.isParsableInteger("not integer"))
        );
    }

    @Test
    public void validateIsParsableDouble() {
        assertAll(
                () -> assertTrue(Validations.isParsableDouble("123.87875")),
                () -> assertFalse(Validations.isParsableInteger("not double"))
        );
    }
}
