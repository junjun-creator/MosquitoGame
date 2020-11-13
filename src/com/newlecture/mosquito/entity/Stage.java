package com.newlecture.mosquito.entity;

import com.newlecture.mosquito.service.DataService;

//값을 담기 위해서만 쓰이는 구조체 같은 정보 클래스
//값을 담고 있는 것 외에는 별다른 기능이 없어
//변수의 접근제어자를 public으로 설정함. 

public class Stage {
	public int stageIndex;			// 현재 스테이지 인덱스
	public int limitTime;			// 스테이지 시간
	
	public int mosqCreateCount;		// 스테이지 당 최대 생성 가능한 모기 수
	public int mosqMaxCount;		// 생성 주기에 따라 생성되는 모기 수
	public int mosqCreateTime;		// 모기 생성주기
	public int mosqMinSpeed;		// 모기 최소 스피드
	public int mosqMaxSpeed;		// 모기 최대 스피드
	public int mosqHP;				// 스테이지 별 모기 체력
	
	public int buttMaxCount;		// 스테이지 당 최대 생성 가능한 나비 수
	public int buttCreateCount;		// 생성 주기에 따라 생성되는 나비 수
	public int buttCreateTime; 		// 나비 생성 주기
	public int buttMinSpeed;		// 나비 최소 스피드
	public int buttMaxSpeed;		// 나비 최대 스피드
	public int buttHP;				// 스테이지 별 나비 체력
	
	public int killScore;
	
	
	public Stage() {
		super();
		mosqMaxCount = 0;
		mosqCreateCount = 0;
		mosqCreateTime = 0;
		mosqMinSpeed = 0;
		mosqMaxSpeed = 0;
		mosqHP = 0;
		
		buttMaxCount = 0;
		buttCreateCount = 0;
		buttCreateTime = 0;
		buttMinSpeed = 0;
		buttMaxSpeed = 0;
		buttHP = 0;
		killScore = 0;
	}


	public int getStageIndex() {
		return stageIndex;
	}


	public void setStageIndex(int stageIndex) {
		this.stageIndex = stageIndex;
	}


	public int getMosqCreateCount() {
		return mosqCreateCount;
	}


	public void setMosqCreateCount(int mosqCreateCount) {
		this.mosqCreateCount = mosqCreateCount;
	}


	public int getMosqMaxCount() {
		return mosqMaxCount;
	}


	public void setMosqMaxCount(int mosqMaxCount) {
		this.mosqMaxCount = mosqMaxCount;
	}


	public int getMosqCreateTime() {
		return mosqCreateTime;
	}


	public void setMosqCreateTime(int mosqCreateTime) {
		this.mosqCreateTime = mosqCreateTime;
	}


	public int getButtMaxCount() {
		return buttMaxCount;
	}


	public void setButtMaxCount(int buttMaxCount) {
		this.buttMaxCount = buttMaxCount;
	}


	public int getButtCreateCount() {
		return buttCreateCount;
	}


	public void setButtCreateCount(int buttCreateCount) {
		this.buttCreateCount = buttCreateCount;
	}


	public int getButtCreateTime() {
		return buttCreateTime;
	}


	public void setButtCreateTime(int buttCreateTime) {
		this.buttCreateTime = buttCreateTime;
	}


	public int getKillScore() {
		return killScore;
	}


	public void setKillScore(int killScore) {
		this.killScore = killScore;
	}

}
