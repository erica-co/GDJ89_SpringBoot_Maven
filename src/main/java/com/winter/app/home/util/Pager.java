package com.winter.app.home.util;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Pager {
	
	//시작 번호
	private Long startNum;
	
	//가져올 갯수
	private Long perPage=10L;
	
	//페이지 번호
	private Long page;
	
	//시작번호
	private Long start;
	//끝번호
	private Long end;
	
	//다음 block 유무
	private Boolean next=true;
	
	//검색 종류
	private String kind;
	
	//검색어
	private String search;
	
	
	
	//Pageing 번호 계산
	public void make(Long totalCount) {
		//1. totalPage 계산
		Long totalPage = totalCount/this.perPage;
		if(totalCount%this.perPage != 0) {
			totalPage++;
		}
		
		//2. totalBlock 계산
		//한페이지에 출력할 페이지 번호의 갯수
		Long perBlock=5L;
		Long totalBlock = totalPage/perBlock;
		if(totalPage%perBlock != 0) {
			totalBlock++;
		}
		
		//3. page 번호로 현재 Block번호
		Long curBlock = this.getPage()/perBlock;
		if(this.getPage()%perBlock != 0) {
			curBlock++;
		}
		
		//4. Block번호를 이용해서 시작, 끝 번호 계산
		this.start=(curBlock-1)*perBlock+1;
		this.end=curBlock*perBlock;
		
		//5. 현재 Block이 마지막 Block인가??
		if(curBlock==totalBlock) {
			this.end = totalPage;
			this.next=false;
		}
		
	}
	
	
	//DB
	public void makeNum() {
		this.startNum=(this.getPage()-1)*this.perPage;
	}

	
	public Long getStartNum() {
		if(this.startNum==null || this.startNum<0) {
			this.startNum=0L;
		}
		return startNum;
	}
	
	public Long getPage() {
		if(this.page==null || this.page<1) {
			this.page=1L;
		}
		
		return this.page;
	}
	
	public String getSearch() {
		if(this.search==null) {
			this.search="";
		}
		
		return this.search;
	}
	
	
	
	
	
	
	

}
