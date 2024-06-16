package com.example.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.dev.dto.CreateUserDto;
import com.example.dev.model.UserCt;
import com.example.dev.service.IUserCtService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserCtService userCtService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

//	TRABAJANDO EN EDITARLO DE OTRA MANERA
//	@PostMapping("/regUser")
//	public ModelAndView ss(UserCt userCt) {
//		ModelAndView mAV = new ModelAndView("/login");
//
//		this.userCtService.save(userCt);
//
//		return mAV;
//	}

	@PostMapping("/regUser")
	public ModelAndView ss(@Valid CreateUserDto createUserDto, BindingResult bindingResult) {
		if (!bindingResult.getAllErrors().isEmpty()) {
		    ModelAndView mAV = new ModelAndView("register");
		    mAV.addObject("errors", bindingResult.getAllErrors());
		    return mAV;
		}
		UserCt userToCreate = createUserDto.toEntity();
	    UserCt createdUser = this.userCtService.save(userToCreate);

	    ModelAndView mAV = new ModelAndView("register");

	    mAV.addObject("created", createdUser.getUserid() != null);

	    return mAV;
	}

//	@PostMapping("/regUser")
//	public ModelAndView ss(@Valid CreateUserDto createUserDto, BindingResult bindingResult) {
//	    if (!bindingResult.getAllErrors().isEmpty()) {
//	        ModelAndView mAV = new ModelAndView("register");
//	        if (bindingResult.getFieldError("nickname") != null) {
//	            mAV.addObject("error", "error2");
//	        } else {
//	            mAV.addObject("error", "error1");
//	        }
//	        return mAV;
//	    }
//
//	    // Mapear a UserCt
//	    UserCt userToCreate = createUserDto.toEntity();
//	    UserCt createdUser = this.userCtService.save(userToCreate);
//
//	    ModelAndView mAV = new ModelAndView("register");
//
//	    mAV.addObject("created", createdUser.getUserid() != null);
//
//	    return mAV;
//	}
}
