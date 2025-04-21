package com.winter.app.board.qna;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QnaDAOTest {
	
	@Autowired
	private QnaDAO qnaDAO;

	@Test
	void testAdd() throws Exception {
		QnaVO qnaVO = new QnaVO();
		qnaVO.setUserName("user1");
		qnaVO.setBoardTitle("title1");
		qnaVO.setBoardContents("contents1");
		qnaDAO.add(qnaVO);
		qnaDAO.refUpdate(qnaVO);
	}

}
