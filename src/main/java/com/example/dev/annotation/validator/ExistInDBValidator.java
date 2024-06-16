package com.example.dev.annotation.validator;

import org.springframework.stereotype.Component;

import com.example.dev.annotation.ExistInDB;
import com.example.dev.dao.IUserCtDao;
import com.example.dev.model.UserCt;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistInDBValidator implements ConstraintValidator<ExistInDB, String> {

	private final IUserCtDao userCtDao;

	public ExistInDBValidator(IUserCtDao userCtDao) {
		this.userCtDao = userCtDao;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		UserCt user = null;
		if (value.contains("@")) {
			return this.userCtDao.findByMail(value).isEmpty();
		} else {
			user = this.userCtDao.findByNickname(value);
		}
		return user == null;
	}

//	@Override
//	public boolean isValid(String value, ConstraintValidatorContext context) {
//	    UserCt user = null;
//	    String errorMessage = null;
//
//	    if (value.contains("@")) {
//	        if (!this.userCtDao.findByMail(value).isEmpty()) {
//	            errorMessage = "Correo electrónico ya registrado";
//	        }
//	    } else {
//	        user = this.userCtDao.findByNickname(value);
//	        if (user != null) {
//	            errorMessage = "Nombre de usuario no disponible";
//	        }
//	    }
//
//	    if (errorMessage != null) {
//	        context.disableDefaultConstraintViolation();
//	        context.buildConstraintViolationWithTemplate(errorMessage)
//	               .addConstraintViolation();
//	        return false;
//	    }
//	    return true;
//	}
}
