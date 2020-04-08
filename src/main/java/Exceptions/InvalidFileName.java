package Exceptions;

/**
 * InvalidFileName class - You can use this code to retrieve localized error messages when file name is invalid
 *
 * @author Soumya Sriram
 */
public class InvalidFileName extends Exception {
    public InvalidFileName(String message) {
        super(message);
    }
}
