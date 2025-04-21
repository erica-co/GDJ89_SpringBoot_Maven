package com.winter.app.board.qna;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.winter.app.board.BoardFileVO;
import com.winter.app.board.BoardService;
import com.winter.app.board.BoardVO;
import com.winter.app.files.FileManager;
import com.winter.app.home.util.Pager;

@Service
@Transactional(rollbackFor = Exception.class)
public class QnaService implements BoardService{
	
	@Autowired
	private QnaDAO qnaDAO;
	
	@Autowired
	private FileManager fileManager;
	
	@Value("${menu.board.notice.name}")
	private String kind;
	
	@Value("${app.files.base}")
	private String path;

	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
		// TODO Auto-generated method stub
		pager.make(qnaDAO.getTotalCount(pager));
		pager.makeNum();
		List<BoardVO> ar = qnaDAO.getList(pager); 
		return ar;
	}

	@Override
	public BoardVO getDetail(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return qnaDAO.getDetail(boardVO);
	}

	@Override
	public int add(BoardVO boardVO, MultipartFile [] multipartFiles) throws Exception {
		// TODO Auto-generated method stub
		int result = qnaDAO.add(boardVO);
		result = qnaDAO.refUpdate(boardVO);
		
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
				
				result = qnaDAO.addFile(boardFileVO);
			}
		}
		
		
		
		return result;
	}
	
	@Override
	public BoardFileVO getFileDetail(BoardFileVO boardFileVO) throws Exception {
		// TODO Auto-generated method stub
		return qnaDAO.getFileDetail(boardFileVO);
	}
	
	

}
