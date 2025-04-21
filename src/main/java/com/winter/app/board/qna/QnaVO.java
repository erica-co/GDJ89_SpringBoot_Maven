package com.winter.app.board.qna;

import com.winter.app.board.BoardVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QnaVO extends BoardVO {
	
	private Long boardRef;
	private Long boardStep;
	private Long boardDepth;

}
