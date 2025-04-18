package com.winter.app.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.winter.app.board.BoardFileVO;
import com.winter.app.board.BoardService;
import com.winter.app.board.BoardVO;
import com.winter.app.files.FileManager;
import com.winter.app.home.util.Pager;

@Service
public class NoticeService implements BoardService{
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private FileManager fileManager;
	
	@Value("${menu.board.notice.name}")
	private String kind;
	
	@Value("${app.files.base}")
	private String path;

	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
		// TODO Auto-generated method stub
		pager.make(noticeDAO.getTotalCount(pager));
		pager.makeNum();
		List<BoardVO> ar = noticeDAO.getList(pager); 
		return ar;
	}

	@Override
	public BoardVO getDetail(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.getDetail(boardVO);
	}

	@Override
	public int add(BoardVO boardVO, MultipartFile [] multipartFiles) throws Exception {
		// TODO Auto-generated method stub
		int result = noticeDAO.add(boardVO);
		
		
		//파일을 HDD에 저장
		if(multipartFiles != null) {
			for(MultipartFile f:multipartFiles) {
				if(f.isEmpty()) {
					continue;
				}
				
				String fileName = fileManager.fileSave(path.concat(kind), f);
				//저장된 파일명을 DB에 저장
				BoardFileVO boardFileVO = new BoardFileVO();
				boardFileVO.setFileName(fileName);
				boardFileVO.setOldName(f.getOriginalFilename());
				boardFileVO.setBoardNum(boardVO.getBoardNum());
				
				result = noticeDAO.addFile(boardFileVO);
			}
		}
		
		
		
		return result;
	}
	
	@Override
	public BoardFileVO getFileDetail(BoardFileVO boardFileVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.getFileDetail(boardFileVO);
	}
	
	

}
