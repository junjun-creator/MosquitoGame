package com.newlecture.mosquito.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.entity.Bug;
import com.newlecture.mosquito.entity.Butterfly;
import com.newlecture.mosquito.entity.Miss;
import com.newlecture.mosquito.entity.Mosquito;
import com.newlecture.mosquito.entity.Player;
import com.newlecture.mosquito.entity.Score;
import com.newlecture.mosquito.entity.Stage;
import com.newlecture.mosquito.entity.Timer;
import com.newlecture.mosquito.gui.Button;
import com.newlecture.mosquito.gui.GameClear;
import com.newlecture.mosquito.gui.GameOver;
import com.newlecture.mosquito.gui.PlayerHpBar;
import com.newlecture.mosquito.gui.WeaponButton;
import com.newlecture.mosquito.gui.listener.ButtonClickedAdapter;
import com.newlecture.mosquito.gui.listener.ButtonClickedListener;
import com.newlecture.mosquito.service.DataService;
import com.newlecture.mosquito.service.ImageLoader;
import com.newlecture.mosquito.service.StageService;
import com.newlecture.mosquito.weapon.RiceStraw;
import com.newlecture.mosquito.weapon.Spear;
import com.newlecture.mosquito.weapon.StrawShoes;
import com.newlecture.mosquito.weapon.Weapon;


public class StageCanvas extends GameCanvas {

	private Image weapon1;
	private Image weapon2;
	private Image weapon22;
	private Image[] weaponImg;
	private boolean isLevelUp = false;
	private boolean isClearSound = true;

	// ü
	public static Canvas instance;
	Thread th;//

	private Clip bgClip;
	private Clip effectClip;
	private AudioInputStream bgAis;
	private boolean clearSound = true;
	private boolean overSound = true;
	private AudioInputStream effectAis;
	private Clip mosClip;
	private AudioInputStream mosAis;
	
	private int showLevelupTime=0;

	/// 여기서 보유무기 이미지 stageService에서 받아오고,

	private StageService stageService;
	private Timer timer;
	private Player player;
	private PlayerHpBar hpBar;

	private WeaponButton weapons;
	private boolean isTypedTab = false;
	
	private ArrayList<Miss> missList;
	private Score score;
	private int stageStep;
	private int userLevel;
	private int userScore;

	// 현재 스테이지를 표시하는 텍스트 이미지
	private Image stageText;
	private Image stageNumber;

	private int killCount = 0;

	private ButtonClickedListener clickListener;
	private Image levelUp;

	public StageCanvas() {//

		instance = this;

		stageStep = 1;

		mosSound("res/sound/mos.wav");

		stageService = new StageService();
		timer = stageService.getTimer();
		player = stageService.getP1();
		hpBar = stageService.getHpBar();
		missList = stageService.getMissList();
		// 현재 스테이지에 맞는 백그라운드를 가져옴
		stageText = ImageLoader.stageText;
		stageNumber = ImageLoader.stageNumber;

		weaponImg = stageService.getWeaponImg();
		
		System.out.println(player.getUserLevel());
		weapons = stageService.getWeapons();
		
		// 이벤트 발생시 웨폰버튼에서 이름 가져오고
		// p1.current 정보변경
		score = new Score();
		userLevel = DataService.getInstance().getPlayerIntValue(GameFrame.getInstance().getUserName(), "level");
		userScore = player.getUserTotalScore();
		stageService.getGameOver().addClickListener(new ButtonClickedAdapter() {
			// 이벤트 리스너 객체가 캔버스 생성할때는 되지만 스테이지 2로 넘어가면서 새로
			// 생성한 stageService에서는 객체 생성을 안하고 있음... 그래서 문제
			@Override
			public void onClicked(GameOver gameOver) {
				try {
					GameFrame.getInstance().switchCanvas(StageCanvas.this, MenuCanvas.class,true);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		});

		stageService.getGameClear().addClickListener(new ButtonClickedAdapter() {

			@Override
			public void onClicked(GameClear gameClear) {
				System.out.println("저장중");

				try {
					DataService.getInstance().save(player.getUserName(), userLevel, player.getUserTotalScore());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				stageStep++;//2
				// stageService.changeStage(stageStep);
				ButtonClickedListener gameOverListener = stageService.getGameOver().getClickListener();
				ButtonClickedListener gameClearListener = stageService.getGameClear().getClickListener();
				int hp = hpBar.getHp();
				stageService = new StageService(stageStep);
				stageService.setTimer(new Timer(stageService.getStageIndex()));
				weapons = stageService.getWeapons();
				timer = stageService.getTimer();
				player = stageService.getP1();
				hpBar = stageService.getHpBar();
				hpBar.setHp(hp);
				stageService.getGameOver().addClickListener(gameOverListener);
				stageService.getGameClear().addClickListener(gameClearListener);
				killCount = 0;
				clearSound = true;
			}

		});

		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 9) {
					isTypedTab = false;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {
				case 49://1
					player.setCurrentWp(player.getWeapons()[0]);

					player.getCurrentWp().setX(getMousePosition().x);
					player.getCurrentWp().setY(getMousePosition().y);
					break;
				case 50:
					player.setCurrentWp(player.getWeapons()[1]);

					player.getCurrentWp().setX(getMousePosition().x);
					player.getCurrentWp().setY(getMousePosition().y);
					break;
				case 51:
					player.setCurrentWp(player.getWeapons()[2]);

					player.getCurrentWp().setX(getMousePosition().x);
					player.getCurrentWp().setY(getMousePosition().y);
					break;
				case 9:
					isTypedTab = true;
					break;
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				player.getCurrentWp().setX(e.getX());
				player.getCurrentWp().setY(e.getY());
			}
		});

		// addMouseMotionListener를 사용하면 기존에 override 해놓은 mouseDown 메소드가 안먹힘.
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				player.getCurrentWp().setImgLoading(true);
				
				int x = e.getX();
				int y = e.getY();
				
				if (((timer.getOneCount() == 0 && timer.getTenCount() == 0) || player.getHp() <= 0)
						&& killCount != stageService.getMosqMaxCount()) {
					mosSoundOff();

					if (stageService.getGameOver().contains(x, y)) {
						stageService.setGameOver(false);
						stageService.getGameOver().getClickListener().onClicked(stageService.getGameOver());
					}

				} else if (killCount == stageService.getMosqMaxCount() && stageService.isGameOver() == false) {
					
					mosSoundOff();
				//	clearSound("res/sound/gameclear.wav");
					if (stageService.getGameClear().contains(x, y)) {
						 stageService.setGameClear(false);
		                  killCount = 0;
		                  isClearSound = true;
		                  clearSoundOff();
		                  mosSound("res/sound/mos.wav");
		                  stageService.getGameClear().getClickListener().onClicked(stageService.getGameClear());
		            }

				} else if (true == player.getCurrentWp().isClickable()) {
					player.getCurrentWp().AttackSound();
					// 클릭 좌표를 중심으로 range안에 들어어오는 벌레를 잡음
					// 클릭 범위 설정 해야함.(타이머위치, 보유무기 위치)
					// 무기 영역과 비교해서 걸리는 모든 객체 갖고오기 => 범위공격 고려해서 범위에 걸린 모든 벌레 반환
					Mosquito selectedMosq = null;
					Butterfly selectedButt = null;

					int mosqSize = stageService.getMosqs().size();
					for (int i = 0; i < mosqSize; i++) {
						Mosquito mosq = stageService.getMosqs().get(i);
						boolean isWeaponRange = player.getCurrentWp().isAttackRange(mosq);
						if (true == isWeaponRange) {
							selectedMosq = mosq;
						}
					}

					int buttSize = stageService.getButts().size();
					for (int i = 0; i < buttSize; i++) {
						Butterfly butt = stageService.getButts().get(i);
						boolean isWeaponRange = player.getCurrentWp().isAttackRange(butt);
						if (true == isWeaponRange) {
							selectedButt = butt;
						}
					}

					boolean isMiss = false;

					if (selectedMosq != null) { // null이 아니면 찾은거임
						System.out.println("모기 클릭 성공");
						isMiss = player.attack(selectedMosq);
					}

					if (selectedButt != null) {
						isMiss = player.attack(selectedButt);
						System.out.println("아얏!");

					}
					if (isMiss == true && stageService.isCreatableMiss()) {
						missList.add(new Miss(x, y));
						effectSound("res/sound/miss.wav");
						System.out.println("빗나감");

					}/*
					else if(isMiss == true && selectedButt.isClickable()) {
						missList.add(new Miss(x, y));
						effectSound("res/sound/miss.wav");
						System.out.println("빗나감");
					}*/
					else {
						if (selectedMosq != null) {
							if (selectedMosq.getHp() <= 0 && selectedMosq.getCurrentDir() != 2) {

								effectSound("res/sound/mosdie.wav");
								killCount++;
								String stageName = "stage" + stageService.getStageIndex();
								int killScore = DataService.getInstance().getGameIntValue(stageName, "killScore");
								int nowScore = score.getScore();
								score.setScore(nowScore += killScore);
								player.setUserTotalScore(player.getUserTotalScore() + killScore);
								int levelBound = DataService.getInstance().getGameIntValue("default", "levelBound");
								levelBound *= stageService.getP1().getUserLevel();
								if(player.getUserTotalScore() >= levelBound) { 
									effectSound("res/sound/Levelup.wav");
									System.out.println("레벨 업! 현재 레벨 : " + (++userLevel));
									stageService.getP1().setUserLevel(userLevel);
									stageService.getP1().setWeapons();
									player = stageService.getP1();
									stageService.setWeaponButton();
									weapons = stageService.getWeapons();
									isLevelUp = true;
								}
								
//								if (player.getUserTotalScore() % levelBound == 0 && player.getUserTotalScore() / 100 != 0) {
//									System.out.println("레벨 업! 현재 레벨 : " + (++userLevel));
//								}
								selectedMosq.setMovIndex(4);
								selectedMosq.setCurrentDir(2);
								selectedMosq.setClickable(false);
								
								stageService.setCreatableMiss(false);
								//missList.clear();
							}

						} else if (selectedButt != null) {

							if (selectedButt.getHp() <= 0) {
								System.out.println("나비 사망");
								System.out.println("10초 감소");
								selectedButt.setCurrentDir(2);
								selectedButt.setMovIndex(4);
								selectedButt.setClickable(false);
								stageService.setCreatableMiss(false);
								if(timer.getTenCount() == 0)
									timer.setOneCount(0);
								else
									timer.setTenCount(timer.getTenCount()-1);
							}
							System.out.println("공격");
						}
					}
				}
			}
		});
	}

	private void mosSound(String file) {

		try {
			mosAis = AudioSystem.getAudioInputStream(new File(file));
			mosClip = AudioSystem.getClip();

			mosClip.open(mosAis);
			mosClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mosSoundOff() {
		mosClip.stop();
	}
	
	 public void clearSoundOff() {
	      bgClip.stop();
	 }

	private void effectSound(String file) {

		try {
			effectAis = AudioSystem.getAudioInputStream(new File(file));
			effectClip = AudioSystem.getClip();

			effectClip.open(effectAis);
			effectClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearSound(String file) {

		try {
			bgAis = AudioSystem.getAudioInputStream(new File(file));
			bgClip = AudioSystem.getClip();

			bgClip.open(bgAis);
			bgClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {

		Image buf = this.createImage(this.getWidth(), this.getHeight());
		Graphics bg = buf.getGraphics();
		
	    bg.drawImage(stageService.getBackground(), 0, 0, null);

	    {
			bg.drawImage(stageText, 30, 30, null);
			
			int index = stageService.getStageIndex();

			int x = 20 + stageText.getWidth(null);
			int tenValue = index/10;

			if(tenValue != 0) {
				int sX1 = 70 * tenValue;
				int sY1 = 70 * (tenValue/6);
				int sX2 = sX1 + 70;
				int sY2 = sY1 + 70;
				
				bg.drawImage(stageNumber, x, 30, x + 70, 100, 
						sX1, sY1, sX2, sY2, null);
				x += 40;
			} 

			
			int oneValue = index%10;
			int sX1 = 70 * (oneValue%5);				
			int sY1 = 70 * (oneValue/5);
			int sX2 = sX1 + 70;
			int sY2 = sY1 + 70;
				
			bg.drawImage(stageNumber, x, 30,  x + 70, 100, 
						sX1, sY1, sX2, sY2, null);
		}
	    
	    {
		    int level = stageService.getP1().getUserLevel();
		    
		    bg.setColor(Color.BLACK);
		    bg.setFont(new Font("돋움", Font.BOLD, 20));
		    bg.drawString("계급 : " + level, 700, 890);
	    }
	    
		if (((timer.getOneCount() == 0 && timer.getTenCount() == 0) || player.getHp() <= 0)
				&& stageService.isGameClear() == false) {
			
			mosSoundOff();
			stageService.setGameOver(true);
			stageService.getGameOver().paint(bg);

		} else if (killCount == stageService.getMosqMaxCount() && stageService.isGameOver() == false) {
			if(isClearSound) {
	            mosSoundOff();
	            clearSound("res/sound/gameclear.wav");
	            isClearSound = false;
	         }

			stageService.setGameClear(true);
			stageService.getGameClear().paint(bg);
		} else {
			timer.paint(bg);
			score.paint(bg);

			int mosqSize = stageService.getMosqs().size();
			for (int i = 0; i < mosqSize; i++) {
				stageService.getMosqs().get(i).paint(bg);
			}

			int buttSize = stageService.getButts().size();
			for (int i = 0; i < buttSize; i++) {
				stageService.getButts().get(i).paint(bg);
			}
			if (missList != null) {
				int missSize = missList.size();
				for (int i = 0; i < missSize; i++) {
					missList.get(i).paint(bg);
				}
			}
			if (isTypedTab) {
				weapons.paint(bg);
			}
			
			if(isLevelUp) {
	            levelUp = ImageLoader.Levelup;
	            bg.drawImage(levelUp, 500, 500, StageCanvas.instance);
	         }


			player.getCurrentWp().paint(bg);

			hpBar.paint(bg);
		}

		g.drawImage(buf,0,0,this);
	}

	@Override
	public void gameUpdate() {
		
		showLevelupTime++;

		if (showLevelupTime >= 500) {
			isLevelUp = false;
			showLevelupTime = 0;
		}
		
		timer.update();

		int mosqSize = stageService.getMosqs().size();
		for (int i = 0; i < mosqSize; i++) {
			stageService.getMosqs().get(i).update();
		}

		int buttSize = stageService.getButts().size();
		for (int i = 0; i < buttSize; i++) {
			stageService.getButts().get(i).update();
		}

		if (missList != null) {

			for (int i = 0; i < missList.size(); i++) {

				missList.get(i).update();

			}
			for (int i = 0; i < missList.size(); i++) {
				if (missList.get(i).getDelTime() < 0) {
					missList.remove(i);
				}
			}
		}

		player.getCurrentWp().update();

		stageService.update();
	}
	
}