package com.newlecture.mosquito.service;

import java.awt.Canvas;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.canvas.StageCanvas;
import com.newlecture.mosquito.entity.Butterfly;
import com.newlecture.mosquito.entity.Miss;
import com.newlecture.mosquito.entity.MosqAttackListener;
import com.newlecture.mosquito.entity.Mosquito;
import com.newlecture.mosquito.entity.Player;
import com.newlecture.mosquito.entity.Stage;
import com.newlecture.mosquito.entity.Timer;
import com.newlecture.mosquito.gui.GameClear;
import com.newlecture.mosquito.gui.GameOver;
import com.newlecture.mosquito.gui.PlayerHpBar;
import com.newlecture.mosquito.gui.WeaponButton;

public class StageService {
	private ArrayList<Mosquito> mosqs;//==null
	private ArrayList<Butterfly> butts;		// 스테이지에 생성되는 모든 모기와 나비
	private ArrayList<Miss> missList;
	private Image[] weaponImg;
	private Stage stage;
	private int stageIndex;
	private Timer timer;
	private Player p1;
	private WeaponButton weapons;
	private int totalScore=0;
	private boolean isCreatableMiss = true;
	
	private int currentMosqCount;		// 현재 생성 된 모기 수
	private int mosqDeltaTime;
	private int mosqMaxCount;
	private int mosqCreateCount;
	private int mosqCreateTime;
	
	private int currentButtCount;		// 현재 생성 된 나비 수
	private int buttDeltaTime;
	private int buttMaxCount;
	private int buttCreateCount;
	private int buttCreateTime;
	
			
	private GameOver gameOver;
	private GameClear gameClear;
	private Image gameOverBtn = ImageLoader.gameOverBtn;
	private Image gameClearBtn = ImageLoader.gameClearBtn;
	
	private PlayerHpBar hpBar;
	private boolean isGameClear;
	private boolean isGameOver;

	private Image background;
	
	public StageService() {
		this(1);
	}
	
	public StageService(int stageStep) {
		
		missList = new ArrayList<Miss>();
		
		stageIndex = stageStep;
		timer = new Timer(this.getStageIndex());
		p1 = new Player(GameFrame.getInstance().getUserName(),1);
		
		setWeaponButton();
		/*
		ArrayList wpDir = p1.getArrWpDir();//무기 이미지경로 리스트
		ArrayList wp = p1.getArrWp();//무기 이름 리스트
		weaponImg = new Image[wpDir.size()];

		weaponImg[0] = ImageLoader.level1_weapon;
		weaponImg[1] = ImageLoader.level2_weapon;
		weaponImg[2] = ImageLoader.level3_weapon;
		
		System.out.println(p1.getUserLevel());
		// 해당 레벨에 보유한 무기 갯수만큼 for문 돌려서 버튼 생성. 버튼 생성 위치도 변수화 해야함.
		switch(p1.getUserLevel()/10) {
		case 0:
			weapons = new WeaponButton("WeaponList", weaponImg[p1.getUserLevel()/10], weaponImg[p1.getUserLevel()/10], 400, 200, 732, 700);
			break;
		case 1:
			weapons = new WeaponButton("WeaponList", weaponImg[p1.getUserLevel()/10], weaponImg[p1.getUserLevel()/10], 400, 200, 732, 700);
			break;
		case 2:
			weapons = new WeaponButton("WeaponList", weaponImg[p1.getUserLevel()/10], weaponImg[p1.getUserLevel()/10], 400, 200, 732, 700);
			break;
		}
		*/
		
		hpBar = new PlayerHpBar(p1.getHp());
		isGameClear = false;
		isGameOver = false;
		gameOver = new GameOver("gameOver",gameOverBtn, gameOverBtn, 642, 359, 216, 283);
		gameClear = new GameClear("gameClear",gameClearBtn, gameClearBtn, 450, 327, 599, 347);
		//System.out.println("gameover, gameclear 객체 생성 완료");
		
		changeStage(stageStep);
	}
	
	public void setWeaponButton() {
		ArrayList wpDir = p1.getArrWpDir();//무기 이미지경로 리스트
		ArrayList wp = p1.getArrWp();//무기 이름 리스트
		weaponImg = new Image[wpDir.size()];

		weaponImg[0] = ImageLoader.level1_weapon;
		weaponImg[1] = ImageLoader.level2_weapon;
		weaponImg[2] = ImageLoader.level3_weapon;
		
		System.out.println(p1.getUserLevel());
		// 해당 레벨에 보유한 무기 갯수만큼 for문 돌려서 버튼 생성. 버튼 생성 위치도 변수화 해야함.
		switch(p1.getUserLevel()/10) {
		case 0:
			weapons = new WeaponButton("WeaponList", weaponImg[p1.getUserLevel()/10], weaponImg[p1.getUserLevel()/10], 400, 200, 732, 700);
			break;
		case 1:
			weapons = new WeaponButton("WeaponList", weaponImg[p1.getUserLevel()/10], weaponImg[p1.getUserLevel()/10], 400, 200, 732, 700);
			break;
		case 2:
			weapons = new WeaponButton("WeaponList", weaponImg[p1.getUserLevel()/10], weaponImg[p1.getUserLevel()/10], 400, 200, 732, 700);
			break;
		}
	}

	public GameClear getGameClear() {
		return gameClear;
	}

	public void changeStage(int stageIndex) {
		this.stageIndex = stageIndex;		// 현재 스테이지 바꾸고
	
		if(mosqs == null) {// = new X
			mosqs = new ArrayList<Mosquito>();	
			butts = new ArrayList<Butterfly>();	
		} else {
			mosqs.clear();
			butts.clear();
		}

		
		// 새로운 스테이지 정보 가져오기
		stage = DataService.getInstance().getStageValue(stageIndex);

		//모기 & 나비 생성		
		currentMosqCount = 0;		// 현재 생성 된 모기 수
		mosqDeltaTime = 0;
		mosqMaxCount = stage.getMosqMaxCount();
		mosqCreateCount = stage.getMosqCreateCount();
		mosqCreateTime = stage.getMosqCreateTime() * 60;			// 60FPS라서 60을 곱함
		createMosquito();
		
		currentButtCount = 0;		// 현재 생성 된 나비 수
		buttDeltaTime = 0;
		buttMaxCount = stage.getButtMaxCount();
		buttCreateCount = stage.getButtCreateCount();
		buttCreateTime = stage.getButtCreateTime() * 60;			// 60FPS라서 60을 곱함
		createButterfly();
		
		// 배경화면 갯수와 스테이지 갯수가 달라서, 스테이지3을 넘어가면 일단 랜덤으로 전시함.
	    int backgroundIndex = 0;
	    if (stageIndex > 3) {
	         Random rand = new Random();
	         backgroundIndex = rand.nextInt(3);
	    } else {
	         backgroundIndex = stageIndex - 1;
	    }
	    background = ImageLoader.stageBackgrounds[backgroundIndex];
	}

	public void setScore() {
		int killScore = stage.getKillScore();
		totalScore += killScore;

		/*스테이지 완료 및 스테이지 전환 구현하기.
		if(totalScore >= currentStage.getWinScore()) {
			currentStage = Dat
		}*/
	}
	
	public void createMosquito() {
		
		if( (currentMosqCount+mosqCreateCount) <= mosqMaxCount) {
			mosqDeltaTime = 0;
			currentMosqCount += mosqCreateCount;
			//System.out.println("모기 생성 시작");
			//System.out.println("mosqCreateCount : "+mosqCreateCount);
			for (int i = 0 ;i < mosqCreateCount; i++) {
				// 모기
				Mosquito m = new Mosquito();
				m.setMinSpeed(stage.mosqMinSpeed);		// 스피드 설정
				m.setMaxSpeed(stage.mosqMaxSpeed);		// 스피드 설정
				m.setHp(stage.mosqHP);					// 체력 설정
				
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
			buttDeltaTime = 0;

			currentButtCount += buttCreateCount;
			
			for (int i = 0; i < buttCreateCount; i++) {		// 나비
				Butterfly butt = new Butterfly();
				butt.setMinSpeed(stage.buttMinSpeed);		// 스피드 설정
				butt.setMaxSpeed(stage.buttMaxSpeed);		// 스피드 설정
				butt.setHp(stage.buttHP);					// 체력 설정
				
				butts.add(butt);
			}
		}
	}
	
	public void update() {//스레드에서 계속 호출
		//System.out.println("mosqDeltaTime : "+mosqDeltaTime);
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
		
		if(mosqs.size() > 0) {
			/// 모기 죽을 때 처리		
			//Iterator<Mosquito> it = mosqs.iterator();
			
			for (int i = 0; i < mosqs.size(); i++) {		// 모기
				if(mosqs.get(i).getCurrentDir() == 2) {
					int deleteTimer = mosqs.get(i).getDeleteTimer();
					deleteTimer--;
					mosqs.get(i).setDeleteTimer(deleteTimer);
				}
				
				if(mosqs.get(i).getDeleteTimer() == 0) {
					//mosqs.remove(i);
					mosqs.get(i).setX(-100);
					mosqs.get(i).setY(-100);
					mosqs.get(i).setClickable(true);
					
					isCreatableMiss = true;
					
//					for(Miss miss : missList) {
//						miss.setClickable(true);
//					}
					//stage.setMosqCreateCount(--mosqCreateCount);
				}
			}
		}
		
		
		if(butts.size() > 0) {
			
			for (int i = 0; i < butts.size(); i++) {		// 모기
				if(butts.get(i).getCurrentDir() == 2) {
					int deleteTimer = butts.get(i).getDeleteTimer();
					deleteTimer--;
					butts.get(i).setDeleteTimer(deleteTimer);
				}
				
				if(butts.get(i).getDeleteTimer() == 0) {
					butts.get(i).setX(-100);
					butts.get(i).setY(-100);
					butts.get(i).setClickable(true);
					isCreatableMiss = true;
				}
			}
		}
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
		// TODO Auto-generated method stub
		return gameOver;
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

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public int getStageIndex() {
		return stageIndex;
	}

	public void setStageIndex(int currentStageIndex) {
		this.stageIndex = currentStageIndex;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public PlayerHpBar getHpBar() {
		return hpBar;
	}

	public void setHpBar(PlayerHpBar hpBar) {
		this.hpBar = hpBar;
	}

	public boolean isGameClear() {
		return isGameClear;
	}

	public void setGameClear(boolean isGameClear) {
		this.isGameClear = isGameClear;
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

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public Image getBackground() {
		return background;
	}

	public void setBackground(Image background) {
		this.background = background;
	}

	public ArrayList<Miss> getMissList() {
		return missList;
	}

	public void setMissList(ArrayList<Miss> missList) {
		this.missList = missList;
	}

	public boolean isCreatableMiss() {
		return isCreatableMiss;
	}

	public void setCreatableMiss(boolean isCreatableMiss) {
		this.isCreatableMiss = isCreatableMiss;
	}

	public Image[] getWeaponImg() {
		return weaponImg;
	}

	public void setWeaponImg(Image[] weaponImg) {
		this.weaponImg = weaponImg;
	}

	public WeaponButton getWeapons() {
		return weapons;
	}

	public void setWeapons(WeaponButton weapons) {
		this.weapons = weapons;
	}

	public int getCurrentMosqCount() {
		return currentMosqCount;
	}

	public void setCurrentMosqCount(int currentMosqCount) {
		this.currentMosqCount = currentMosqCount;
	}

	public int getMosqDeltaTime() {
		return mosqDeltaTime;
	}

	public void setMosqDeltaTime(int mosqDeltaTime) {
		this.mosqDeltaTime = mosqDeltaTime;
	}

	public int getMosqCreateCount() {
		return mosqCreateCount;
	}

	public void setMosqCreateCount(int mosqCreateCount) {
		this.mosqCreateCount = mosqCreateCount;
	}

	public int getMosqCreateTime() {
		return mosqCreateTime;
	}

	public void setMosqCreateTime(int mosqCreateTime) {
		this.mosqCreateTime = mosqCreateTime;
	}

	public int getCurrentButtCount() {
		return currentButtCount;
	}

	public void setCurrentButtCount(int currentButtCount) {
		this.currentButtCount = currentButtCount;
	}

	public int getButtDeltaTime() {
		return buttDeltaTime;
	}

	public void setButtDeltaTime(int buttDeltaTime) {
		this.buttDeltaTime = buttDeltaTime;
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

	public Image getGameOverBtn() {
		return gameOverBtn;
	}

	public void setGameOverBtn(Image gameOverBtn) {
		this.gameOverBtn = gameOverBtn;
	}

	public Image getGameClearBtn() {
		return gameClearBtn;
	}

	public void setGameClearBtn(Image gameClearBtn) {
		this.gameClearBtn = gameClearBtn;
	}

	public void setGameOver(GameOver gameOver) {
		this.gameOver = gameOver;
	}

	public void setGameClear(GameClear gameClear) {
		this.gameClear = gameClear;
	}

	
	
}
