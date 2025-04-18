package com.winter.app.board;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class BoardVO {
	//DTO : Data Transfer Object
	//VO  : Value Object (읽기 전용)
	private Long boardNum;
	private String boardTitle;
	private String boardContents;
	private Date boardDate;
	private Long boardHit;
	private String userName;
	
	private List<BoardFileVO> boardFileVOs;
	

}
