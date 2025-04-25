package com.winter.app.webclient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.winter.app.PostVO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
@Slf4j
class WebTest {
	
	@Test
	void test2() {
		//요청 객체 생성 - 재사용가능
		WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com/");
		
		Flux<PostVO> ar = webClient
				.get()
				.uri("posts")
				.retrieve()//retrieve로 응답을 받겠다
				.bodyToFlux(PostVO.class)//응답타입을 postVO로 바꿔라
				;
		
		//Flux안에 postvo가 여러개 있는거 -> 하나씩 꺼내야함(몇개인지 모름) -> 반복문으로 꺼내기
		//ar(postVO가 여러개 있는)에서 하나 꺼낸 걸 s에 넣겠다
		ar.subscribe((s)->{
			//s = PostVO
			log.info("{} : ", s);
		});
		
		Mono<PostVO> res = webClient
				.get()
				.uri("posts/{num}",1)
				.retrieve()
				.bodyToMono(PostVO.class)//body에서 모노를 하나 꺼내라
				;
			
			PostVO postVO = res.block();
		
//		Mono<ResponseEntity<PostVO>> res = webClient
//			.get()
//			.uri("posts/{num}",1)
//			.retrieve()
//			.toEntity(PostVO.class)
//			;
//		
		
		log.info("{}", postVO);
	}

	@Test
	void test() {
		
		WebClient webClient = WebClient.create();
			//응답 하나 받을 때는 Mono
			//여러개 받을 때는 Flux
			Mono<ResponseEntity<String>> res = webClient //ResponseEntity 제네릭에 응답받을 타입 넣기
				.get()
				.uri("https://jsonplaceholder.typicode.com/posts/{num}",3)
				.retrieve()
				.toEntity(String.class)//응답을 문자열로 받겠다
				;//이것들을 보내면 응답을 받아야함
			
			String result = res.block().getBody(); //위에 string으로 설정해놔서 getBody 응답도 string임
			log.info(result);
			 
				
			
	}

}
