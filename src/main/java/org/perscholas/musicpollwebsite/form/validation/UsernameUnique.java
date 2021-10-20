package org.perscholas.musicpollwebsite.form.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameUniqueImpl.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {

	String message() default "{UserEmailUnique}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}