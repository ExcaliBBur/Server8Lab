package Exceptions;

/**
 * Represents all exceptions that occur during deserialization.
 */
public abstract class DeserializationException extends RuntimeException {
    private String message;

    public DeserializationException() {

    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Incorrect parameters were entered into the file.
     */
    public static class InvalidParameterException extends DeserializationException {
        public InvalidParameterException() {
            super.message = "Something is wrong with parameters.";
        }
    }

    /**
     * There are some issues with contents of the file.
     */
    public static class LoadException extends DeserializationException {
        public LoadException() {
            super.message = "Some contents of the collection cannot be loaded.";
        }
    }

    /**
     * Not having permissions to read contents of the file.
     */
    public static class NoRightsException extends DeserializationException {
        public NoRightsException() {
            super.message = "Unable to open this file.";
        }
    }
}
