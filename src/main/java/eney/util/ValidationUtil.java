package eney.util;

import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import eney.exception.InvalidDataException;

@Component
public class ValidationUtil {
    private final Logger LOGGER = LoggerFactory.getLogger(ValidationUtil.class);
    private final Validator javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private final SpringValidatorAdapter validator = new SpringValidatorAdapter(javaxValidator);

    public Object validate(Object entry) {
        Errors errors = new BeanPropertyBindingResult(entry, entry.getClass().getName());
        validator.validate(entry, errors);
        if (errors == null || errors.getAllErrors().isEmpty())
            return entry;
        else {
        	LOGGER.info(errors.toString());
            throw new InvalidDataException(errors.getAllErrors().toString(), errors);
        }
    }

    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
