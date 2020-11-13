
package com.newlecture.mosquito.entity;

import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.gui.listener.UserNameListener;
import com.newlecture.mosquito.service.DataService;
import com.newlecture.mosquito.weapon.Hand;
import com.newlecture.mosquito.weapon.Spear;
import com.newlecture.mosquito.weapon.Weapon;

public class Player {
	
	//private Weapon[] weapon;
	private int money;
	private int tier;
	private Weapon wp;
	private Weapon currentWp;
	private int hp;
	private int score;
	private Weapon[] weapons;
	private String userName;
	private int userTotalScore;
	private int userLevel;
	private ArrayList arrWp;
	private ArrayList arrWpDir;
	
	
	//player에 점수를 넣어놓고 인터페이스 구현해서 스테이지 클리어 시점만 알려주게끔 하면 되지 않을까?
	
	public Player(String name,int mode) {
		
		tier = 1;//추후 파일 입출력으로 구현
		money = 0;
		//weapon = new Weapon[100];
		hp = 100;
		userName = name;
		
		userLevel = DataService.getInstance().getPlayerIntValue(userName, "level");
		
		/*
		arrWp = DataService.getInstance().getWeaponList(userLevel);
		
		arrWpDir = new ArrayList();
		
		for(int i=0;i<arrWp.size();i++) {								//"level1"
			arrWpDir.add(DataService.getInstance().getWeaponStringValue("level"+userLevel, (String)arrWp.get(i)));
		}
		*/
		
		/*
		for(int i=0;i<arrWp.size();i++) {
			System.out.println(arrWpDir.get(i));
		}*/
		
		setWeapons();
		/*
		weapons = new Weapon[arrWp.size()];
		
		for(int i=0;i<arrWp.size();i++) {
			String weaponName = "com.newlecture.mosquito.weapon."+arrWp.get(i);
			Class t=null;
			
			try {
				t = Class.forName(weaponName);
				try {
					Object newObj = t.newInstance();
					weapons[i] = (Weapon)newObj;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		currentWp = weapons[0];
		*/
		
		
		
		//wp = new Hand();
		//currentWp = wp;
//		weapons = new Weapon[3];
//		weapons[0] = currentWp;
//		weapons[1] = new Spear();
//		weapons[2] = new FlySwatter();
//		
		if(mode == 1)
			userTotalScore=DataService.getInstance().getPlayerIntValue(userName, "totalScore");
		else
			userTotalScore = 0;
		
		System.out.println("현재 스코어"+userTotalScore);
		//setImg;
		
		//나중에 정리해야함 일단 기능만 확인
		
		
	}
	
	public void setWeapons() {
		arrWp = DataService.getInstance().getWeaponList(userLevel);
		/*
		for(int i=0;i<arrWp.size();i++) {
			System.out.println(arrWp.get(i));
		}
		*/
		
		arrWpDir = new ArrayList();
		
		for(int i=0;i<arrWp.size();i++) {								//"level1"
			arrWpDir.add(DataService.getInstance().getWeaponStringValue("level"+userLevel, (String)arrWp.get(i)));
		}
		
		weapons = new Weapon[arrWp.size()];

		for (int i = 0; i < arrWp.size(); i++) {
			String weaponName = "com.newlecture.mosquito.weapon." + arrWp.get(i);
			Class t = null;

			try {
				t = Class.forName(weaponName);
				try {
					Object newObj = t.newInstance();
					weapons[i] = (Weapon) newObj;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		currentWp = weapons[0];
	}
	
	
	public boolean attack(Bug bug) {
		
		bug.chageAttackImg();
		
		//무기 확률 체크
		double prob = Math.random();//0~1 랜덤 실수
		if(prob <= currentWp.getProb()) {
			//System.out.println("모기죽이기 성공");
			int bugHp = bug.getHp();
			bugHp -= currentWp.getDamage();
			bug.setHp(bugHp);
			//System.out.println("남은 hp : " + bug.getHp());
			return false;
		}else {
			//System.out.println("모기 죽이기 ㄴ실패");
			return true;
		}
		
		//currentwp.~~~~~~~~~();
		//mosq.attak(30);		// 해당 모기의 체력을 30 감소시킨당
	}
	
	public void changeWeapon() {
		
	}


	public int getMoney() {
		return money;
	}


	public void setMoney(int money) {
		this.money = money;
	}


	public int getTier() {
		return tier;
	}


	public void setTier(int tier) {
		this.tier = tier;
	}


	public Weapon getCurrentWp() {
		return currentWp;
	}


	public void setCurrentWp(Weapon currentWp) {
		this.currentWp = currentWp;
	}


	public int getHp() {
		return hp;
	}


	public void setHp(int hp) {
		this.hp = hp;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}
	
	public Weapon[] getWeapons() {
		return weapons;
	}


	public void setWeapons(Weapon[] weapons) {
		this.weapons = weapons;
	}


	public Weapon getWp() {
		return wp;
	}


	public void setWp(Weapon wp) {
		this.wp = wp;
	}
	
	public int getUserTotalScore() {
		return userTotalScore;
	}

	public void setUserTotalScore(int userTotalScore) {
		this.userTotalScore = userTotalScore;
	}

	
	public ArrayList getArrWp() {
		return arrWp;
	}


	public void setArrWp(ArrayList arrWp) {
		this.arrWp = arrWp;
	}


	public void setArrWpDir(ArrayList arrWpDir) {
		this.arrWpDir = arrWpDir;
	}


	public ArrayList getArrWpDir() {
		return arrWpDir;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getUserLevel() {
		return userLevel;
	}


	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	
	
}
