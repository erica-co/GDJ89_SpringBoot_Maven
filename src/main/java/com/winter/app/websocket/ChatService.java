package com.winter.app.websocket;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winter.app.user.UserVO;

@Service
public class ChatService {
	
	@Autowired
	private ChatDAO chatDAO;
	@Autowired
	private ChatHandler chatHandler;
	
	public List<UserVO> getList() throws Exception {
		return chatDAO.getList();
	}
	
	public List<MessageVO> room(MessageVO messageVO) throws Exception{
		List<MessageVO> list = chatDAO.room(messageVO);
		
		
		if(list.size()==0) {
			Calendar calendar = Calendar.getInstance();
			messageVO.setRoomNum(calendar.getTimeInMillis());
			chatDAO.addChat(messageVO);
			list = chatDAO.room(messageVO);
		}

		
		
		return list;
	}
}
