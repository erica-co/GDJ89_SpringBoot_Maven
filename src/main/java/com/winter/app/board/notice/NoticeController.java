package com.winter.app.board.notice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.winter.app.board.BoardFileVO;
import com.winter.app.board.BoardVO;
import com.winter.app.home.util.Pager;
import com.winter.app.user.UserVO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController //json
//@RequestMapping("/notice/*")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Value("${menu.board.notice.name}")
	private String name;
	
	@ModelAttribute("kind")
	public String getName() {
		return this.name;
	}
	
	//@GetMapping("list")
	@GetMapping("/notices")
	public Map<String, Object> getList(Pager pager, Model model)throws Exception{
		
		pager.setKind("k1");
		List<BoardVO> ar = noticeService.getList(pager);
		model.addAttribute("list", ar);
		model.addAttribute("pager", pager);
		
		//10개를 출력하고 그 다음 10개를 출력
		//list와 pager를 담는 map으로 출력하기
		Map<String, Object> map = new HashMap<>();
		map.put("list", ar);
		map.put("pager", pager);
		return map;

	}
	
	//@GetMapping("detail")
	@GetMapping("/notices/{page}")
	public BoardVO getDetail(@PathVariable(name = "page") Long page)throws Exception{
		//log.info("{}", boardVO);
		// 처음에는 restful 형식으로 설계를 안해서
		//찍어보면 다 null 나옴 -> boardVO 사용 불가 
		
		BoardVO boardVO = new BoardVO();
		boardVO.setBoardNum(page);
		boardVO = noticeService.getDetail(boardVO);
		
		if(boardVO == null) {
			
		}
		
		return boardVO;
		//json 형식으로 웹에 출력됨 
	}
	
	
	@PostMapping("/notices")
	public int add(NoticeVO noticeVO,@RequestParam(name="attaches") MultipartFile[] attaches)throws Exception{
		
		log.info("{}", noticeVO);
		for(MultipartFile m : attaches) {
			log.info("{}", m.getOriginalFilename());
		}
		
		//noticeVO.setUserName(name); //보안 강화
		
		int result = noticeService.add(noticeVO, attaches);

		return result;
	}
	
	@DeleteMapping("/notices/{boardNum}")
	public int delete(@PathVariable (name="boardNum") Long boardNum) throws Exception{
		log.info("DeleteNum : {} ", boardNum);
		
		return 1;
	}
	
	@PatchMapping("/notices")
	public int update (NoticeVO noticeVO,@RequestParam(name="attaches") MultipartFile[] attaches)throws Exception{
		
		for(MultipartFile m : attaches) {
			log.info("{}", m.getOriginalFilename());
		}
		
		
		//noticeVO.setUserName(name); //보안 강화
		
		//int result = noticeService.add(noticeVO, attaches);

		return 0; //result
	}
	
	@GetMapping("fileDown")
	public String getFileDetail(BoardFileVO boardFileVO, Model model)throws Exception{
		boardFileVO = noticeService.getFileDetail(boardFileVO);
		
		model.addAttribute("fileVO", boardFileVO);
		
		return "fileDownView";
	}

}
