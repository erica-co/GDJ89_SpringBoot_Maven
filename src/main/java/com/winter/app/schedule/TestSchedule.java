package com.winter.app.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.winter.app.user.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestSchedule {
	
	@Autowired
	private UserDAO userDAO;
	
	//@Scheduled(fixedRate = 1000, initialDelay = 3000 )
	public void useFixRate() {
		log.info("USE FixRate");
		//언제 종료 되는지 상관하지 않고 1초 후 바로 실행
	}
	
	//@Scheduled(fixedDelay = 1000, initialDelay = 4000)
	public void useFixDelay () {
		log.info("USE FiXDelay");
		//메서드가 종료 되고 1초 후 실행
	}
	
	/**
	 * 		*(초 0~59) *(분 0~59) *(시 0~23) *(일1~31) *(월 1~12) *(요일)
	 * 		10 	  		*		  *           *        *        * 
	 * 		요일 : 0(일요일) ~ 7(일요일)
	 * 
	 * 
	 * */
	
	//@Scheduled(cron = "10 * * * * *")
	//@Scheduled(cron = "0 0  9 * * 1-5") 평일 9시
	@Scheduled(cron = "0 0 0 25 12 *")
	public void useCron() {
		log.info("USE Cron");
		
	}
	
	
	@Scheduled(cron = "0 0 1 1 1 *")
	public void useCron2() {
		log.info("USE Cron");
		
	}
	
	
	
	
	
}
