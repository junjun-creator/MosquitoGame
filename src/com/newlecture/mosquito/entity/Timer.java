package com.newlecture.mosquito.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.newlecture.mosquito.canvas.StageCanvas;
import com.newlecture.mosquito.service.DataService;
import com.newlecture.mosquito.service.ImageLoader;



public class Timer {
	
	private Image img;
	private Image dot;
	
	private int x=0;
	private int y=0;
	private int w = 44;
	private int h = 68;
	//private int totalCount=100;
	private int timeForTimer = 60;
	private int timeForDecimal = 6;
	private int limitTime;
	private int tenCount;
	private int oneCount;
	private int decimalCount = 10;
	private boolean isAdded = false;
	
	public Timer() {
		this(0);
	}
	
	public Timer(String freeStage) {
		img = ImageLoader.timerNumber;  
		dot = ImageLoader.timerDot;
		
		limitTime = DataService.getInstance().getGameIntValue(freeStage, "limitTime");
		tenCount = limitTime/10;
		oneCount = limitTime%10;//0~9
	}
	
	public Timer(int stageIndex) {
		// TODO Auto-generated constructor stub
		img = ImageLoader.timerNumber;  
		dot = ImageLoader.timerDot;
		
		limitTime = DataService.getInstance().getGameIntValue("stage"+stageIndex, "limitTime");
		tenCount = limitTime/10;
		oneCount = limitTime%10;
	}

	public void paint(Graphics g) {
		/*if(oneCount == 0) {
			tenCount--;
			oneCount = 10;
		}*/
		//System.out.println("지금 남은시간 : "+(tenCount*10+oneCount)+"."+(decimalCount%10));
		g.drawImage(img, 100-w, 100, 100-w+w, 100+h, (tenCount)*w, y, (tenCount)*w+w, y+h, StageCanvas.instance);
		g.drawImage(img, 100, 100, 100+w, 100+h, (oneCount)*w, y, (oneCount)*w+w, y+h, StageCanvas.instance);
		g.drawImage(dot, (100+w), 100, (100+w)+15, 100+h, 0, 0, 0+15, 0+h, StageCanvas.instance);
		g.drawImage(img, (100+w+15), 100+h/2, (100+w+15)+w, 100+h, (decimalCount%10)*w, y, (decimalCount%10)*w+w, y+h, StageCanvas.instance);
		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
	}
	
	public void update() {
		//1초당 이미지 좌표 변경
		timeForTimer--;
		timeForDecimal--;
		
		if(tenCount*10+oneCount == limitTime && false == isAdded)//카운트의 첫 시작일때에만 수행하는 구문
			if(oneCount != 0) {			//60.0 -> 59.9로 만들기 위함
				tenCount--;
				oneCount--;
				decimalCount--;
			}
			else {
				tenCount--;
				oneCount=9;
				decimalCount--;
			}
				
		
		if (timeForDecimal == 0) {

			if (decimalCount > 0)
				decimalCount--;
			else if (decimalCount == 0 && (oneCount == 0 && tenCount == 0))
				decimalCount = 0;//마지막 0.0에서는 decimal이 영원히 0이 찍혀서 더이상 시간이 변화하지 않는다. 여기서 게임타임아웃 지정하면 될듯
			else if (decimalCount == 0)
				decimalCount = 9;

			timeForDecimal = 6;
		}
		
		if(timeForTimer == 0) {
			//totalCount--;
		
			//십의자리 일의자리 좌표 세팅
			if(tenCount >= 0 && oneCount > 0){//일의자리와 십의자리가 모두 0보다 클때(00~99)
				oneCount--;
				//System.out.println(oneCount);
			}
			else if(oneCount == 0 && tenCount > 0) {//십의자리가 0보다 크고, 1의자리가 0이되면 
				//System.out.println(tenCount);
				tenCount--;
				oneCount = 9;
			}
	
			timeForTimer = 60;
		}
		
		
		
	}

	public int getTenCount() {
		return tenCount;
	}

	public void setTenCount(int tenCount) {
		this.tenCount = tenCount;
	}

	public int getOneCount() {
		return oneCount;
	}

	public void setOneCount(int oneCount) {
		this.oneCount = oneCount;
	}

	public int getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}

	public boolean isAdded() {
		return isAdded;
	}

	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}
	
	
}
