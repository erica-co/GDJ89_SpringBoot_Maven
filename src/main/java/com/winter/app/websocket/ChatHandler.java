package com.winter.app.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winter.app.user.UserVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ChatHandler implements WebSocketHandler{
	
	//socket으로 연결된 전체 유저 저장(WebSocketSession을 활용해서)
	//BroadCast (list 안써도 됨)
	private List<WebSocketSession> list = new ArrayList<>();
	
	//socket으로 연결된 전체 유저
	//key : username , value: websocketSession
	private Map<String, WebSocketSession> users = new HashMap<>();
	//대화방의 대화 내용을 임시 저장(DB에 저장)
	//현재 사용 x
	private Map<Long, List<MessageVO>> messages = new HashMap<>();
	
	@Autowired
	private ChatDAO chatDAO;
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		//client가 websocket 연결시 실행
		
		//log.info("client : {}", session);
		log.info("p: {}", session.getPrincipal());
		//연결되면 session정보를 list에 담기
		list.add(session);
		//UserVO userVO = (UserVO)session.getPrincipal();
		log.info("{}", session.getPrincipal().getName());
		users.put(session.getPrincipal().getName(), session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		//webSocket으로 연결된 client가 메세지를 송신했을 때
		
		//log.info("message : {}", message);
		//log.info("m : {}", message.getPayload()); //json 형태의 문자열 (js)
		
		ObjectMapper objectMapper = new ObjectMapper();
		MessageVO messageVO = objectMapper.readValue(message.getPayload().toString(), MessageVO.class);
		log.info("messageVO : {}", messageVO);
		
		messageVO.setSender(session.getPrincipal().getName());
		log.info("sender : {}", messageVO.getSender());
		
		//1:1 통신 시 DB에서 room정보 조회
		List<MessageVO> rooms = chatDAO.room(messageVO);
		
		messageVO.setRoomNum(rooms.get(0).getRoomNum());//messageVO에 roomNum을 넣자
		
		if(!messages.containsKey(messageVO.getRoomNum())) {
			
			List<MessageVO> list = new ArrayList<>();
			list.add(messageVO);
			messages.put(messageVO.getRoomNum(), list);
		}else {
			//list 가 있을 경우
			messages.get(messageVO.getRoomNum()).add(messageVO);
		}
		
		chatDAO.addChat(messageVO);
		
		try {
			users.get(messageVO.getReceiver()).sendMessage(message);
		}catch (Exception e) {
			e.printStackTrace();
		}
		users.get(messageVO.getSender()).sendMessage(message);
		
		/*
		 * if(messages.get(messageVO.getRoomNum()).size()>2) { List<MessageVO> copy =
		 * messages.get(messageVO.getRoomNum()); messages.put(messageVO.getRoomNum(),
		 * new ArrayList<>()); int result = chatDAO.test(copy);
		 * 
		 * }
		 */
		
		/*
		 * if(messageVO.getRoomNum()==null) { // 방번호 만들기 Calendar calendar =
		 * Calendar.getInstance(); long rm = calendar.getTimeInMillis();
		 * messages.put(rm, null); }
		 */
		
	//	list.forEach(s ->{
	//		try {
	//			s.sendMessage(message);
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
		}
	

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		//webSocket오류가 발생했을 때
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
		//websocket 연결이 종료되었을 때
		list.remove(session);
		users.remove(session.getPrincipal().getName());
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		//용량이 큰 데이터를 나눠서 받을 수 있는지 여부를 결정
		//true이면서 톰캣이 지원을 하면 handleMessage를 여러번 호출
		return false;
	}
		
	
		
}
