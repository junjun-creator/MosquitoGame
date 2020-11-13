package com.newlecture.mosquito.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.service.DataService;
import com.newlecture.mosquito.service.StageService;

public abstract class Bug{
	
	
	private double x;
	private double y;
	private int hp;//추후 파일에서 읽어오기(개체별)

	
	// 애니메이션을 위한 변수
	private double vx;
	private double vy;
	private double dx;
	private double dy;
	private double width;			// 실제 전시 되는 벌레의 width
	private double height;			// 실제 전시 되는 벌레의 height
	
	private Image img;
	private double imgWidth;		// 이미지 파일에서 그릴 이미지의 width
	private double imgHeight;		// 이미지 파일에서 그릴 이미지의 height
	
	private int timeoutForMoving = 30;
	private int timeoutForAttack = 20;		//공격 이미지 변경 시 시간 체크를 위함
	private int movIndex ;
	private int speed;
	private int minSpeed;
	private int maxSpeed;
	private int walkTempo ;
	private int outRange =60;
	private int direction;
	private int imgDirection;		// 방향이 바뀔때 imgDirection기준으로 그릴 이미지가 변경 됨
	private int currentDir;

	private boolean isAttacked;		// 공격 당했을 때 일정 시간동안 잠깐 이미지를 바꾸기 때문에 상태 변수가 필요함 
	
	private Random rand = new Random();
	


	public Bug() {   // 모기, 나비 초기값.
		int w = GameFrame.canvasWidth;
		int h = GameFrame.canvasHeight;
		
		// 나비가 화면 바깥에서 나오게하기 위해 상하좌우 60만큼 좌표 추가
		
		int rand2 = (int)(Math.random()*2+1);
	
		this.x = (double) rand.nextInt(w + outRange * 2 + 1) - 60; 
		this.y = (double) rand.nextInt(h + outRange * 2 + 1) - 60;
		
		
		
		// 만약 화면 안쪽에 나비가 생성되었을 경우 좌표 다시 설정
		while (-30 < x && x < w + 30 && -30 < y && y < h + 30) {
			x = (double) rand.nextInt(w + outRange * 2 + 1) - 60;
			y = (double) rand.nextInt(h + outRange * 2 + 1) - 60;
		}
		
		hp = 10;
		movIndex = 0;
		speed = 1;
		minSpeed = 1;
		maxSpeed = 3;
		walkTempo = 6;
	}
	
	public void move(double x, double y) {
		int newSpeed = minSpeed + rand.nextInt(maxSpeed-minSpeed);
		setSpeed(newSpeed);		
		
	    this.dx = x;
		this.dy = y;

		// 동일한 속도로 이동하는 단위벡터
		double w = this.dx - this.x;
		double h = this.dy - this.y;
		double d = Math.sqrt(w*w + h*h);
		this.vx = w/d*getSpeed();
		this.vy = h/d*getSpeed();
	}

	public  void update() {
		timeoutForMoving--;
		if (timeoutForMoving == 0 && currentDir != 2) {
//			double width = (int) this.width;
//			double height = (int) this.height;

			double width = (int) this.imgWidth;
			double height = (int) this.imgHeight;
			
			int w = GameFrame.canvasWidth - (int) width;
			int h = GameFrame.canvasHeight - (int) height;
			int dx = rand.nextInt(w);
			int dy = rand.nextInt(h);
			//왼쪽
			
			if(this.x < dx && currentDir == 0) {//모기의 방향 설정
				currentDir = 1;
				if(direction == 0)
					direction = imgDirection;
				else
					direction = 0;
			}
			else if(this.x >= dx && currentDir == 1) {
				currentDir = 0;
				if(direction == 0)
					direction = imgDirection;
				else
					direction = 0;
			}
			this.move(dx, dy);
			
			timeoutForMoving = rand.nextInt(60) + 60;// 0~59+60 // 60~119
			
		}
		
		if(currentDir != 2) {//살아있을 때
			
			x += vx;
			y += vy;
		}
		
		if(true == isAttacked) {
			timeoutForAttack--;
			if (timeoutForAttack == 0) {
				isAttacked = false;
				timeoutForAttack = 30;
			}
		}

	}
	
	
	public abstract void paint(Graphics g);
	

	public void chageAttackImg() {
		// TODO Auto-generated method stub
		isAttacked = true;
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

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getMovIndex() {
		return movIndex;
	}

	public void setMovIndex(int movIndex) {
		this.movIndex = movIndex;
	}

	public int getSpeed() {
		return speed;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getWalkTempo() {
		return walkTempo;
	}	

	public int getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(int minSpeed) {
		this.minSpeed = minSpeed;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public void setWalkTempo(int walkTempo) {
		this.walkTempo = walkTempo;
	}
	public int getTimeoutForMoving() {
		return timeoutForMoving;
	}
	public void setTimeoutForMoving(int timeoutForMoving) {
		this.timeoutForMoving = timeoutForMoving;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}


	public double getImgWidth() {
		return imgWidth;
	}


	public void setImgWidth(double imgWidth) {
		this.imgWidth = imgWidth;
	}


	public double getImgHeight() {
		return imgHeight;
	}


	public void setImgHeight(double imgHeight) {
		this.imgHeight = imgHeight;
	}


	public int getImgDirection() {
		return imgDirection;
	}


	public void setImgDirection(int imgDirection) {
		this.imgDirection = imgDirection;
	}


	public int getCurrentDir() {
		return currentDir;
	}


	public void setCurrentDir(int currentDir) {
		this.currentDir = currentDir;
	}


	public boolean isAttacked() {
		return isAttacked;
	}

	
	
}



