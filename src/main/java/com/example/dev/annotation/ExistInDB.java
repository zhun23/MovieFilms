package com.example.dev.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.example.dev.annotation.validator.ExistInDBValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ExistInDBValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface ExistInDB {

	String message() default "Nombre de usuario o correo electrónico no disponible";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
