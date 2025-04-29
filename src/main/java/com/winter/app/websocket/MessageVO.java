package com.winter.app.websocket;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MessageVO {
	
	private Long chatNum;
	private Long roomNum;
	private String sender;
	private String body;
	private String receiver;
	private String date;
	private String status;

}
