package com.newlecture.mosquito.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Button;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.gui.listener.IntroListener;

public class IntroPanel extends JPanel {
	private Image img;
	private AudioInputStream introAis;
	private Clip introClip;

	private IntroListener introListener;
	
	public IntroPanel() throws MalformedURLException {
		img = Toolkit.getDefaultToolkit().createImage("res/introMovie.gif");
		introSound("res/sound/introMovie.wav");
		
		Button skipButton = new Button("SKIP");
		skipButton.setLocation(1000, 10);
		skipButton.setSize(new Dimension(50,30));
		skipButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(introListener != null) {
					introListener.onIntroEnd();	
				}
			}
		});
		add(skipButton);
	}
	
	public IntroListener getIntroListener() {
		return introListener;
	}

	public void setIntroListener(IntroListener introListener) {
		this.introListener = introListener;
	}

	@Override
	public void paint(Graphics g) {

		paintComponent(g);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null) {
			g.drawImage(img, 0, 0, GameFrame.canvasWidth, GameFrame.canvasHeight, this);
		}
	}
	
	private void introSound(String file) {

		try {
			introAis = AudioSystem.getAudioInputStream(new File(file));
			introClip = AudioSystem.getClip();

			introClip.open(introAis);
			introClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void introSoundOff() {
		introClip.stop();
	}
	

}
