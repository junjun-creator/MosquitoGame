package com.newlecture.mosquito.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.newlecture.mosquito.canvas.MenuCanvas;
import com.newlecture.mosquito.gui.listener.ButtonClickedListener;

public class Button {

	// 버튼 이름(버튼 구분을 위함. id 역할)
	private String name;
		
	// 화면상에서 버튼의 좌표 
	private double x;
	private double y;
	
	// 버튼의 크기
	private int width;
	private int height;
	
	// 이미지 파일에서 버튼에 적용할 이미지의 좌표 (source 좌표)
	private Image currentImg;					// 현재 버튼에서 전시되고 있는 이미지 파일
	
	private Image imgNormal;			// 평상시 버튼 이미지 파일
	private Image imgPressed;			// 평상시 버튼 이미지 파일
	
	// 이미지의 크기
	private int imgWidth;
	private int imgHeight;

	// 버튼을 사용할 곳에서 해당 리스너의 메소드를 오버라이드 해야 "버튼이 클릭 되었을 떄" 처리가 가능함
	private ButtonClickedListener clickListener;
	
	public Button(String name, Image img, double x, double y, int width, int height) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		// 따로 pressed, normal 구분해서 안받았으면 일단 Img로 셋팅
		this.currentImg = img;
		this.imgNormal = img;
		this.imgPressed = img;
		
		this.imgWidth = currentImg.getWidth(null);
		this.imgHeight = currentImg.getHeight(null);
		
		clickListener = null;
	}
	
	public Button(String name, Image normalImg, Image pressedImg, double x, double y, int width, int height) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.currentImg = normalImg;		// 현재 이미지는 normal로 설정. pressed 되면 pressedImg로 변경됨
		this.imgNormal = normalImg;
		this.imgPressed = pressedImg;
		
		this.imgWidth = currentImg.getWidth(null);
		this.imgHeight = currentImg.getHeight(null);
		
		clickListener = null;
	}
	
	public void paint(Graphics g) {// bg

		int w = this.getWidth();
		int h = this.getHeight();
		
		int x1 = (int)this.getX();
		int y1 = (int)this.getY();
		int x2 = (int)x1 + w;
		int y2 = (int)y1 + h;
		
		
		g.drawImage(currentImg, (int)x, (int)y, (int)x+width , (int)y+height, 
				(int)0, (int)0, (int)imgWidth, (int)imgHeight, null);				
	}
	
	public boolean contains(double x, double y) {
		boolean result = false;
		
		if( this.x <= x && x <= this.x+width 
				&& this.y <= y && y <= this.y+height  ) {
			result = true;
		}
		
		return result;
	}
	
	// 외부(ex:캔버스)에서 버튼이 클릭 됐을 때 호출
	public void onButtonClicked() {
		if(null != clickListener) {
			clickListener.onClicked(this);		// 버튼이 클릭 되면 리스너 함수가 실행된다.  => 캔버스 말고도 이 리스너에 등록된 함수들이 호출됨
		}
	}
	
	public void update() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	public Image getCurrentImg() {
		return currentImg;
	}

	public void setCurrentImg(Image currentImg) {
		this.currentImg = currentImg;
		// 이미지가 바뀌면 이미지 사이즈도 같이 바꿔줘야함
		this.imgWidth = currentImg.getWidth(null);
		this.imgHeight = currentImg.getHeight(null);		
	}

	
	public Image getImgNormal() {
		return imgNormal;
	}

	public void setImgNormal(Image imgNormal) {
		this.imgNormal = imgNormal;
	}
	

	public void setImgPressed(Image imgPressed) {
		this.imgPressed = imgPressed;
	}

	public Image getImgPressed() {
		// TODO Auto-generated method stub
		return imgPressed;
	}
	
	
	public ButtonClickedListener getClickListener() {
		return clickListener;
	}

	public void addClickListener(ButtonClickedListener clickListener) {
		this.clickListener = clickListener;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}
	

	

}
