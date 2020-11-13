package com.newlecture.mosquito.entity;

public class Free {   
   
   public int stageIndex;         // 현재 스테이지 인덱스
   public int limitTime;         // 스테이지 시간
   
   public int mosqCreateCount;      // 스테이지 당 최대 생성 가능한 모기 수
   public int mosqMaxCount;      	// 생성 주기에 따라 생성되는 모기 수
   public int mosqCreateTime;      // 모기 생성주기
   public int mosqHP;
   public int mosqMinSpeed;
   public int mosqMaxSpeed;
   
   public int buttMaxCount;      // 스테이지 당 최대 생성 가능한 나비 수
   public int buttCreateCount;      // 생성 주기에 따라 생성되는 나비 수
   public int buttCreateTime;       // 나비 생성 주기
   public int buttHP;
   public int buttMinSpeed;
   public int buttMaxSpeed;
   public int killScore;
//   public int winScore;
   

   


public Free() {
   super();
   mosqMaxCount = 0;
   mosqCreateCount = 0;
   mosqCreateTime = 0;
   mosqHP = 0;
   mosqMinSpeed = 0;  
   mosqMaxSpeed = 0; 
   
   buttMaxCount = 0;
   buttCreateCount = 0;
   buttCreateTime = 0;
   buttHP = 0;
   buttMinSpeed = 0;  
   buttMaxSpeed = 0;  
   
//   killScore = 0;
    
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


public int getMosqCreateTime() {
	// TODO Auto-generated method stub
	return mosqCreateTime;
}


public int getMosqHP() {
	return mosqHP;
}


public void setMosqHP(int mosqHP) {
	this.mosqHP = mosqHP;
}


public int getMosqMinSpeed() {
	return mosqMinSpeed;
}


public void setMosqMinSpeed(int mosqMinSpeed) {
	this.mosqMinSpeed = mosqMinSpeed;
}


public int getMosqMaxSpeed() {
	return mosqMaxSpeed;
}


public void setMosqMaxSpeed(int mosqMaxSpeed) {
	this.mosqMaxSpeed = mosqMaxSpeed;
}


public int getButtHP() {
	return buttHP;
}


public void setButtHP(int buttHP) {
	this.buttHP = buttHP;
}


public int getButtMinSpeed() {
	return buttMinSpeed;
}


public void setButtMinSpeed(int buttMinSpeed) {
	this.buttMinSpeed = buttMinSpeed;
}


public int getButtMaxSpeed() {
	return buttMaxSpeed;
}


public void setButtMaxSpeed(int buttMaxSpeed) {
	this.buttMaxSpeed = buttMaxSpeed;
}





}