package eney.exception;

import org.springframework.validation.Errors;

/**
 * This exception is thrown when the dao has invalid data.
 */
public class InvalidDataException extends RuntimeException {
	
	private static final long serialVersionUID = -7071976017363617059L;
	private Errors errors;

    public InvalidDataException(String msg, Errors errors) {
        super(msg);
        setErrors(errors);
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
