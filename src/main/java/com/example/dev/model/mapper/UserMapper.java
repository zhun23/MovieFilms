package com.example.dev.model.mapper;

import com.example.dev.dto.UserDto;
import com.example.dev.model.UserCt;

public class UserMapper {

	public static UserCt toEntity(UserDto userDto) {
		UserCt userCt = new UserCt();

		userCt.setUserid   ( userDto.getUserid()    );
		userCt.setNickname ( userDto.getNickname()  );
		userCt.setFirstname( userDto.getFirstname() );
		userCt.setLastname ( userDto.getLastname()  );
		userCt.setMail	   ( userDto.getMail()      );
		userCt.setCredit   ( userDto.getCredit()    );

		return userCt;
	}
}
