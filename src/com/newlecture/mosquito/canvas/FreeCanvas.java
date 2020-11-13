package com.newlecture.mosquito.canvas;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.entity.Butterfly;
import com.newlecture.mosquito.entity.Miss;
import com.newlecture.mosquito.entity.Mosquito;
import com.newlecture.mosquito.entity.Player;
import com.newlecture.mosquito.entity.Score;
import com.newlecture.mosquito.entity.Timer;
import com.newlecture.mosquito.gui.Button;
import com.newlecture.mosquito.gui.GameClear;
import com.newlecture.mosquito.gui.GameOver;
import com.newlecture.mosquito.gui.PlayerHpBar;
import com.newlecture.mosquito.gui.WeaponButton;
import com.newlecture.mosquito.gui.listener.ButtonClickedAdapter;
import com.newlecture.mosquito.gui.listener.ButtonClickedListener;
import com.newlecture.mosquito.service.DataService;
import com.newlecture.mosquito.service.FreeService;
import com.newlecture.mosquito.service.ImageLoader;

public class FreeCanvas extends GameCanvas{
	//	3. 무기 구현 // 
	//	4. 데이터 저장 후 랭킹에 올리기


	public static Canvas instance;
	Thread th;

	private Clip bgClip;
	private Clip effectClip;
	private AudioInputStream bgAis;
	private AudioInputStream effectAis;


	private FreeService freeService;
	private Image[] weaponImg;
	private WeaponButton weapons;
	private boolean isTypedTab = false;

	private Timer timer;
	private Player player;
	private PlayerHpBar hpBar;
	private ArrayList<Miss> missList;
	private Score score;
	private Image background;
	private int userScore;
	private Random rand;
	private int userLevel;

	//	private WeaponButton weapons;
	private int killCount = 0;

	private ButtonClickedListener clickListener;

	public FreeCanvas() {
		instance = this;


		freeService = new FreeService();
		timer = freeService.getTimer();
		player = freeService.getP1();
		hpBar = freeService.getHpBar();
		//hpBar = new PlayerHpBar(player.getHp());
		missList = new ArrayList<Miss>();

		background = ImageLoader.stageBackgrounds[0];
		ArrayList wpDir = player.getArrWpDir();
		ArrayList wp = player.getArrWp();
		weaponImg = new Image[wpDir.size()];

		weaponImg[0] = ImageLoader.level1_weapon;
		weaponImg[1] = ImageLoader.level2_weapon;
		weaponImg[2] = ImageLoader.level3_weapon;
		mosSound("res/sound/mos.wav");

		// 해당 레벨에 보유한 무기 갯수만큼 for문 돌려서 버튼 생성. 버튼 생성 위치도 변수화 해야함.
		switch(player.getUserLevel()/10) {
		case 0:
			weapons = new WeaponButton("WeaponList", weaponImg[player.getUserLevel()/10], weaponImg[player.getUserLevel()/10], 400, 200, 732, 700);
			break;
		case 1:
			weapons = new WeaponButton("WeaponList", weaponImg[player.getUserLevel()/10], weaponImg[player.getUserLevel()/10], 400, 200, 732, 700);
			break;
		case 2:
			weapons = new WeaponButton("WeaponList", weaponImg[player.getUserLevel()/10], weaponImg[player.getUserLevel()/10], 400, 200, 732, 700);
			break;
		}
			score = new Score();
			userScore = player.getUserTotalScore();
			freeService.getGameOver().addClickListener(new ButtonClickedAdapter() {

				public void onClicked(GameOver gameOver) {
					try {
						GameFrame.getInstance().switchCanvas(FreeCanvas.this, MenuCanvas.class,true);

					}catch(InstantiationException e) {
						e.printStackTrace();

					}catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}

			});

			setFocusable(true);
			setFocusTraversalKeysEnabled(false);

			addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

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

			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					player.getCurrentWp().setImgLoading(true);
					// 커서 이미지 변경
					int x = e.getX();
					int y = e.getY();
					//		System.out.println(freeService.getMosqs().size());
					if ((timer.getOneCount() == 0 && timer.getTenCount() == 0) || player.getHp() <= 0)
							 {
						// 게임에서 졌을 때, 지방을 누르게 되면 메뉴캔버스로 돌아감

						if (freeService.getGameOver().contains(x, y)) {
							freeService.setGameOver(false);
							freeService.getGameOver().getClickListener().onClicked(freeService.getGameOver());
						}

					} else if (true == player.getCurrentWp().isClickable()) {
						player.getCurrentWp().AttackSound();
						// 클릭 좌표를 중심으로 range안에 들어어오는 벌레를 잡음
						// 클릭 범위 설정 해야함.(타이머위치, 보유무기 위치)
						// 무기 영역과 비교해서 걸리는 모든 객체 갖고오기 => 범위공격 고려해서 범위에 걸린 모든 벌레 반환
						Mosquito selectedMosq = null;
						Butterfly selectedButt = null;

						int mosqSize = freeService.getMosqs().size();
						for (int i = 0; i < mosqSize; i++) {
							Mosquito mosq = freeService.getMosqs().get(i);
							boolean isWeaponRange = player.getCurrentWp().isAttackRange(mosq);
							if (true == isWeaponRange) {
								selectedMosq = mosq;
							}
						}

						int buttSize = freeService.getButts().size();
						for (int i = 0; i < buttSize; i++) {
							Butterfly butt = freeService.getButts().get(i);
							boolean isWeaponRange = player.getCurrentWp().isAttackRange(butt);
							if (true == isWeaponRange) {
								selectedButt = butt;
							}
						}

						boolean isMiss = false;

						if (selectedMosq != null) { // null이 아니면 찾은거임
							System.out.println("모기 클릭 성공");
							isMiss = player.attack(selectedMosq);
							// System.out.println("공격");
						}

						if (selectedButt != null) {
							isMiss = player.attack(selectedButt);
							System.out.println("아얏!");

						}

						if (isMiss == true && freeService.isCreatableMiss()) {// 빗나감
							// miss뜨는 그림효과
							effectSound("res/sound/miss.wav");
							missList.add(new Miss(x, y));
							System.out.println("빗나감");

						} else {// 빗나간게 아니라면
							if (selectedMosq != null && selectedMosq.getCurrentDir() != 2) {
								if (selectedMosq.getHp() <= 0) {
									String stageName = "freeStage";
									System.out.println("모기죽임");
									int killScore = DataService.getInstance().getGameIntValue(stageName, "killScore");
									int nowScore = score.getScore();
									score.setScore(nowScore += killScore);
									player.setUserTotalScore(player.getUserTotalScore() + killScore);
									selectedMosq.setMovIndex(4);
									selectedMosq.setCurrentDir(2);
									
									selectedMosq.setClickable(false);
									freeService.setCreatableMiss(false);
									if(timer.getOneCount() <= 8 )
										timer.setOneCount(timer.getOneCount() + 1);
									else {
										timer.setTenCount(timer.getTenCount()+1);
										timer.setOneCount(timer.getOneCount()-9);
										timer.setAdded(true);
									}
								}

							} else if (selectedButt != null) {

								if (selectedButt.getHp() <= 0) {
									System.out.println("나비 사망");
									System.out.println("10초 감소");
									selectedButt.setCurrentDir(2);
									selectedButt.setMovIndex(4);
									selectedButt.setClickable(false);
									freeService.setCreatableMiss(false);
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
	

	// 모기 사운드
	private void mosSound(String file) {
		try {
			bgAis = AudioSystem.getAudioInputStream(new File(file));
			bgClip = AudioSystem.getClip();

			bgClip.open(bgAis);
			bgClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public void mosSoundOff() {
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

	@Override
	public void paint(Graphics g) {

		Image buf = this.createImage(this.getWidth(), this.getHeight());
		Graphics bg = buf.getGraphics();
		// 배경 그려주세요
		bg.drawImage(background, 0, 0, null);

		// 스테이지 텍스트 전시


		// 게임 실패시...
		if ((timer.getOneCount() == 0 && timer.getTenCount() == 0 ) || (player.getHp() <= 0)) {
			mosSoundOff();
			freeService.setGameOver(true);
			freeService.getGameOver().paint(bg);
			String name = GameFrame.getInstance().getUserName();
			try {
				DataService.getInstance().saveRank(name, player.getUserTotalScore());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//	mosSoundOff();
			// 지방
			// 토탈점수 그려주세요
		}

		else {
			timer.paint(bg);
			score.paint(bg);


			int mosqSize = freeService.getMosqs().size();
			for (int i = 0; i < mosqSize; i++) {
				freeService.getMosqs().get(i).paint(bg);
			}

			int buttSize = freeService.getButts().size();
			for (int i = 0; i < buttSize; i++) {
				freeService.getButts().get(i).paint(bg);
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

			player.getCurrentWp().paint(bg);

			hpBar.paint(bg);
		}

		g.drawImage(buf, 0, 0, this);
	}

	@Override
	public void gameUpdate() {
		timer.update();

		int mosqSize = freeService.getMosqs().size();
		for (int i = 0; i < mosqSize; i++) {
			freeService.getMosqs().get(i).update();
		}

		int buttSize = freeService.getButts().size();
		for (int i = 0; i < buttSize; i++) {
			freeService.getButts().get(i).update();
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
		freeService.update();
	}
	
}
