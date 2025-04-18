package com.winter.app.board.notice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.winter.app.board.BoardDAO;
import com.winter.app.board.BoardVO;

@Mapper
public interface NoticeDAO extends BoardDAO{
	
	
	public int test(List<BoardVO> list)throws Exception;

}
