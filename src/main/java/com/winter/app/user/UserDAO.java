package com.winter.app.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
	
	public int join(UserVO userVO)throws Exception;

}
