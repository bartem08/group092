package com.interview.validation.annotation;

import com.interview.validation.ExistedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Artem Baranovskiy
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ExistedValidator.class)
public @interface Existed {

    String message() default "Id does not exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String collection();
    boolean empty() default true;

}
