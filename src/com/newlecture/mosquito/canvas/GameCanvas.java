package com.newlecture.mosquito.canvas;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import com.newlecture.mosquito.GameFrame;


public class GameCanvas extends Canvas {

	private final int sleepTime = 17; 
	private boolean isRunThread = true;
	
	
	public GameCanvas() {
		// TODO Auto-generated constructor stub
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					closeCanvas();	
				}
			}
		});
	}
	
	public void start() {
		Runnable sub = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (isRunThread) {
					gameUpdate();	
					repaint();
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					}
				}

			}
		};
		
		Thread th = new Thread(sub);
		th.start();
	}
	
	public void closeCanvas() {
		int input = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "메인 메뉴로 돌아가시겠습니까?", "돌아가기", JOptionPane.OK_CANCEL_OPTION);
		if(input == 0) {
			try {
				GameFrame.getInstance().switchCanvas(GameCanvas.this, MenuCanvas.class, false);
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		paint(g);
	}
	
	public boolean isRunThread() {
		return isRunThread;
	}

	public void stop() {
		this.isRunThread = false;
	}

	// update 스레드에서 처리 해줘야 할 업데이트 내용을 구현 (없을 수도 있음)
	public void gameUpdate() {
		
	}
}
