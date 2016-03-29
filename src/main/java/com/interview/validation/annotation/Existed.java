package com.interview.validation.annotation;

import com.interview.validation.ExistedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ExistedValidator.class)
public @interface Existed {

    String message() default "Does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String collection();

}
