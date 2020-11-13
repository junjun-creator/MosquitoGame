package com.newlecture.mosquito.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.gui.Button;
import com.newlecture.mosquito.gui.listener.ButtonClickedAdapter;
import com.newlecture.mosquito.gui.listener.ButtonClickedListener;
import com.newlecture.mosquito.service.ImageLoader;

public class MenuCanvas extends GameCanvas {
	public static Canvas instance;

	private Thread th; // 메뉴 화면이 사라지면 메뉴화면용 스레드도 종료할것이기 때문에 스레드를 멤버변수로 갖고 있을것
	private Button[] buttons;
	private Button stageButton;
	private Button freeButton;
	private Button exitButton;
	private Button rankButton;

	private Image stageBtnNormal;
	private Image stageBtnPressed;
	private Image freeBtnNormal;
	private Image freeBtnPressed;
	private Image exitBtnNormal;
	private Image exitBtnPressed;
	private Image rankBtnNormal;
	private Image rankBtnPressed;


	private Image menuBackground;

	private Clip bgClip;
	private AudioInputStream bgAis;
	

	public MenuCanvas() {
		// TODO Auto-generated constructor stub
		instance = this;

		int btnWidth = 230;
		int btnHeight = 417;
		double sx = 150;				// 200;
		double sy = 530;
		double space = 80;				// 180;
		
		mainSound("res/sound/mainBgm.wav");

		// 메뉴 버튼의 이미지를 받아옴
		stageBtnNormal = ImageLoader.menuStageBtnNormal;
		stageBtnPressed = ImageLoader.menuStageBtnPressed;

		freeBtnNormal = ImageLoader.menuFreeBtnNormal;
		freeBtnPressed = ImageLoader.menuFreeBtnPressed;

		rankBtnNormal=ImageLoader.menuRankBtnNormal;
		rankBtnPressed=ImageLoader.menuRankBtnPressed;

		exitBtnNormal = ImageLoader.menuExitBtnNormal;
		exitBtnPressed = ImageLoader.menuExitBtnPressed;
		
		menuBackground = ImageLoader.menuBackground;

		// 메뉴 버튼 생성
		stageButton = new Button("stage", stageBtnNormal, stageBtnPressed, sx, sy, btnWidth, btnHeight);
		freeButton = new Button("free", freeBtnNormal, freeBtnPressed, sx + btnWidth + space, sy, btnWidth, btnHeight);
		rankButton = new Button("rank", rankBtnNormal, rankBtnPressed, sx + (btnWidth + space) * 2, sy, btnWidth,
				btnHeight);
		exitButton = new Button("exit", exitBtnNormal, exitBtnPressed, sx + (btnWidth + space) * 3, sy, btnWidth,
				btnHeight);

		// 버튼 배열에 넣음
		buttons = new Button[4];
		buttons[0] = stageButton;
		buttons[1] = freeButton;
		buttons[2] = exitButton;
		buttons[3] = rankButton;

		// 캔버스에서 마우스 이벤트 발생 처리
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				for (int i = 0; i < buttons.length; i++) {
					if (true == buttons[i].contains(e.getX(), e.getY())) {
						buttons[i].getClickListener().onReleased(buttons[i]);
						buttons[i].getClickListener().onClicked(buttons[i]); // 메뉴버튼 안눌리는 현상이 있어서 메뉴버튼은 Release 쪽으로 넘김
						// 버튼 클릭시 메인사운드 off

						mainSoundOff();

					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				for (int i = 0; i < buttons.length; i++) {
					if (true == buttons[i].contains(e.getX(), e.getY())) {
						buttons[i].getClickListener().onPressed(buttons[i]);
					}
				}
			}
		});

		// 버튼 배열에 있는 버튼들에게 이벤트를 등록해줌
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].addClickListener(new ButtonClickedAdapter() {
				@Override
				public void onClicked(Button target) {

					switch (target.getName()) {
					case "stage":
						try {													//class의 모든 정보를 인자로(매개변수) 전달
							GameFrame.getInstance().switchCanvas(MenuCanvas.this, StageCanvas.class,true);
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "free":
						try {
							GameFrame.getInstance().switchCanvas(MenuCanvas.this, FreeCanvas.class, true);
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "rank":
						try {
							GameFrame.getInstance().switchCanvas(MenuCanvas.this, RankCanvas.class, false);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "exit":
						int result = JOptionPane.showConfirmDialog(MenuCanvas.this, "게임을 종료하시겠습니까?", "게임종료",
								JOptionPane.OK_CANCEL_OPTION);
						if (0 == result) { // 사용자가 '예'를 눌렀으면
							System.out.println(result);
							System.exit(0);
						}
						break;
					}

				}
			});
		}

	}
	
	@Override
	public void closeCanvas() {
		int input = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		if(input == 0) {
			System.exit(0);
		}		
	}

	@Override
	public void paint(Graphics g) {
		Image buf = this.createImage(this.getWidth(), getHeight());
		Graphics bg = buf.getGraphics();

		bg.drawImage(menuBackground, 0, 0, this); // 배경이미지
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].paint(bg);
		}

		g.drawImage(buf, 0, 0, this);
	}

	public void mainSound(String file) {

		try {
			bgAis = AudioSystem.getAudioInputStream(new File(file));
			bgClip = AudioSystem.getClip();

			bgClip.open(bgAis);
			bgClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void mainSoundOff() {
		bgClip.stop();
	}

}
