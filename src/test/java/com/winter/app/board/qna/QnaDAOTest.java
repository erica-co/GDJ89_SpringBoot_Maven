package com.winter.app.board.qna;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional//(rollbackFor = Exception.class)
class QnaDAOTest {
	
	@Autowired
	private QnaDAO qnaDAO;

	@Test
	@Rollback(true)
	void testAdd() throws Exception {
		QnaVO qnaVO = new QnaVO();
		qnaVO.setUserName("user3");
		qnaVO.setBoardTitle("title3");
		qnaVO.setBoardContents("contents3");
		qnaDAO.add(qnaVO);
		qnaDAO.refUpdate(qnaVO);
	}

}
