package com.winter.app;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostVO {
	
	private int userId;
	private int id;
	private String title;
	private String body;

}
