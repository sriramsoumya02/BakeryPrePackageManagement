package Util;

/**
 * Validations class contains all utility methods used in validations
 *
 * @author Soumya Sriram
 */
public class Validations {
    public static boolean isParsableInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isParsableDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
