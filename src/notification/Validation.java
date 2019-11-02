package notification;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import java.util.regex.Pattern;

public class Validation {
    private RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
    private DoubleValidator doubleValidator = new DoubleValidator();
    private IntegerValidator integerValidator = new IntegerValidator();
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static Validation validation;

    public static Validation getValidation() {
        if (validation == null)
            validation = new Validation();
        return validation;
    }

    private Validation() {
        JFXTextField jfxTextField = new JFXTextField();
//        jfxTextField
        integerValidator.setMessage("Ce champ doit contenir un nombre !");
        integerValidator.getStyleClass().add("error-label");

        requiredFieldValidator.setMessage("Ce champ est obligatoire !");
        requiredFieldValidator.getStyleClass().add("error-label");
        doubleValidator.setMessage("Ce champ doit contenir un nombre !");
        doubleValidator.getStyleClass().add("error-label");
    }

    public RequiredFieldValidator getRequiredFieldValidator() {
        return requiredFieldValidator;
    }

    public DoubleValidator getDoubleValidator() {
        return doubleValidator;
    }

    public void setDoubleValidator(DoubleValidator doubleValidator) {
        this.doubleValidator = doubleValidator;
    }

    public IntegerValidator getIntegerValidator() {
        return integerValidator;
    }
}
