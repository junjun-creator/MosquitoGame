package com.newlecture.mosquito.service;

import java.awt.Canvas;
import java.awt.Image;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.entity.Butterfly;
import com.newlecture.mosquito.entity.Free;
import com.newlecture.mosquito.entity.Miss;
import com.newlecture.mosquito.entity.MosqAttackListener;
import com.newlecture.mosquito.entity.Mosquito;
import com.newlecture.mosquito.entity.Player;
import com.newlecture.mosquito.entity.Score;
import com.newlecture.mosquito.entity.Stage;
import com.newlecture.mosquito.entity.Timer;
import com.newlecture.mosquito.gui.GameClear;
import com.newlecture.mosquito.gui.GameOver;
import com.newlecture.mosquito.gui.PlayerHpBar;
import com.newlecture.mosquito.gui.listener.ButtonClickedListener;

public class FreeService {
	private ArrayList<Mosquito> mosqs;
	private ArrayList<Butterfly> butts;
	private Free free;
	private Timer timer;
	private Player p1;
	private int totalScore = 0;
	private GameOver gameOver;
	private GameClear gameClear;
	private Image gameOverBtn = ImageLoader.gameOverBtn;
	private Image gameClearBtn = ImageLoader.gameClearBtn;
	private String freeStage;
	private boolean isGameOver;
	private boolean isGameClear;
	private boolean isCreatableMiss = true;

	private int currentMosqCount;		// 현재 생성 된 모기 수
	private int mosqDeltaTime;
	private int mosqMaxCount;
	private int mosqCreateCount;
	private int mosqCreateTime;
	private int mosqMinSpeed;
	private int mosqMaxSpeed;
	private int mosqHP;

	private int currentButtCount;		// 현재 생성 된 나비 수
	private int buttDeltaTime;
	private int buttMaxCount;
	private int buttCreateCount;
	private int buttCreateTime;
	private int buttMinSpeed;
	private int buttMaxSpeed;
	private int buttHP;
	

	private PlayerHpBar hpBar;

	public FreeService() {
		freeStage = "freeStage";
		timer = new Timer(freeStage);
		p1 = new Player(GameFrame.getInstance().getUserName(),2);
		hpBar = new PlayerHpBar(p1.getHp());
		
		
		isGameClear = false;
		isGameOver = false;
		
		gameOver = new GameOver("gameOver",gameOverBtn, gameOverBtn, 642, 359, 216, 283);
		gameClear = new GameClear("gameClear",gameClearBtn, gameClearBtn, 450, 327, 599, 347);
	
		freeStage();
	}

	public GameClear getGameClear() {
		return gameClear;
	}

	public void freeStage() {
		this.freeStage = freeStage;
		if(mosqs == null) {
			mosqs = new ArrayList<Mosquito>();
			butts = new ArrayList<Butterfly>();
		} else {
			mosqs.clear();
			butts.clear();
		}

		free = DataService.getInstance().getFreeValue(freeStage);

		//모기 & 나비 생성		
		currentMosqCount = 0;		// 현재 생성 된 모기 수
		mosqDeltaTime = 0;
		mosqMaxCount = free.getMosqMaxCount();
		mosqCreateCount = free.getMosqCreateCount();
		mosqCreateTime = free.getMosqCreateTime() * 60;			// 60FPS라서 60을 곱함
		mosqMinSpeed = free.getMosqMinSpeed();
		mosqMaxSpeed = free.getMosqMaxSpeed();
		mosqHP = free.getMosqHP();
		createMosquito();

		currentButtCount = 0;		// 현재 생성 된 나비 수
		buttDeltaTime = 0;
		buttMaxCount = free.getButtMaxCount();
		buttCreateCount = free.getButtCreateCount();
		buttCreateTime = free.getButtCreateTime() * 60;			// 60FPS라서 60을 곱함
		buttMinSpeed = free.getButtMinSpeed();
		buttMaxSpeed = free.getButtMaxSpeed();
		buttHP = free.getButtHP();
		createButterfly();
	}
	
	public void setScore() {
		int killScore = free.getKillScore();
		totalScore += killScore;
	}

	public void createMosquito() {

		if( (currentMosqCount+mosqCreateCount) <= mosqMaxCount) {
			mosqDeltaTime = 0;
			currentMosqCount += mosqCreateCount;
			System.out.println("모기 생성 시작");
			System.out.println("mosqCreateCount : "+mosqCreateCount);
			for (int i = 0 ;i < mosqCreateCount; i++) {
				// 모기
				Mosquito m = new Mosquito();
				m.setMinSpeed(mosqMinSpeed);		// 스피드 설정
				m.setMaxSpeed(mosqMaxSpeed);		// 스피드 설정
				m.setHp(mosqHP);					// 체력 설정
				m.setMosqAttackListener(new MosqAttackListener() {

					@Override
					public void attackListener(int damage) {
						// TODO Auto-generated method stub
						p1.setHp(p1.getHp()-damage);
						hpBar.setHp(p1.getHp());
					}
				});
				mosqs.add(m);
			}
		}
	}
	
	public void createButterfly() {
		
		if( (currentButtCount+buttCreateCount) < buttMaxCount) {
			System.out.println("나비 생성");
			buttDeltaTime = 0;

			currentButtCount += buttCreateCount;
			
			for (int i = 0; i < buttCreateCount; i++) {		// 나비
				Butterfly butt = new Butterfly();
				butt.setMinSpeed(buttMinSpeed);		// 스피드 설정
				butt.setMaxSpeed(buttMaxSpeed);		// 스피드 설정
				butt.setHp(buttHP);					// 체력 설정
				butts.add(butt);
			}
		}
	}

	public void update() {
		if(mosqDeltaTime >= mosqCreateTime) {
			createMosquito();
		} else {
			mosqDeltaTime++;
		}
		
		if(buttDeltaTime >= buttCreateTime) {
			createButterfly();
		} else {
			buttDeltaTime++;
		}
		//int mosqCreateCount = free.getMosqCreateCount();
		//int buttCreateCount = free.getButtCreateCount();

		for(int i = 0; i<mosqCreateCount; i++) {
			if(mosqs.get(i).getCurrentDir() == 2) {
				int deleteTimer = mosqs.get(i).getDeleteTimer();
				deleteTimer--;
				mosqs.get(i).setDeleteTimer(deleteTimer);
			}

			if(mosqs.get(i).getDeleteTimer() == 0) {
				mosqs.get(i).setX(-1);
				mosqs.get(i).setY(-1);
				mosqs.get(i).setClickable(true);
				
				isCreatableMiss = true;
			}
		}
		
		for (int i = 0; i < buttCreateCount; i++) {		// 모기
			if(butts.get(i).getCurrentDir() == 2) {
				int deleteTimer = butts.get(i).getDeleteTimer();
				deleteTimer--;
				butts.get(i).setDeleteTimer(deleteTimer);
			}

			if(butts.get(i).getDeleteTimer() == 0) {
				butts.get(i).setX(-1);
				butts.get(i).setY(-1);
				butts.get(i).setClickable(true);
				isCreatableMiss = true;
			}
		}
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public Free getFree() {
		return free;
	}

	public void setFree(Free free) {
		this.free = free;
	}

	public ArrayList<Mosquito> getMosqs() {
		return mosqs;
	}

	public void setMosqs(ArrayList<Mosquito> mosqs) {
		this.mosqs = mosqs;
	}

	public ArrayList<Butterfly> getButts() {
		return butts;
	}

	public void setButts(ArrayList<Butterfly> butts) {
		this.butts = butts;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public GameOver getGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public void setGameClear(boolean isGameClear) {
		this.isGameClear = isGameClear;
	}

	public int getMosqMaxCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return isGameOver;
	}

	public PlayerHpBar getHpBar() {
		return hpBar;
	}

	public void setHpBar(PlayerHpBar hpBar) {
		this.hpBar = hpBar;
	}

	public boolean isCreatableMiss() {
		return isCreatableMiss;
	}

	public void setCreatableMiss(boolean isCreatableMiss) {
		this.isCreatableMiss = isCreatableMiss;
	}

	

}
