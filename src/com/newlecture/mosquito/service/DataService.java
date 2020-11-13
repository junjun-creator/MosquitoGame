package com.newlecture.mosquito.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.newlecture.mosquito.entity.Butterfly;
import com.newlecture.mosquito.entity.Free;
import com.newlecture.mosquito.entity.Mosquito;
import com.newlecture.mosquito.entity.Stage;

// 데이터 입출력을 담당하는 서비스 객체
public class DataService {

	// [default] :
	// [stageN] : N번째 스테이지의 기본 정보를 저장.
	private static String gameFileName;
	private static String userFileName;
	private static String weaponFileName;
	private static String rankFileName;
	private static ArrayList<String> userDatas;

	// map이란 -> Key(이름), Value(데이터)로 자료를 저장 할 수 있는 컬렉션의 일종
	// TreeMap<[defalut], TreeMap<playerLevel,1>>
	// HashMap : 데이터 정렬이 없음. vs TreeMap : 키 값 기준으로 정렬
	// HashMap을 써도 되지만 혹시 모르니 file의 순서를 유지하고 싶어서 LinkedHashMap 사용
	private LinkedHashMap<String, LinkedHashMap<String, String>> allGameDatas;
	private LinkedHashMap<String, LinkedHashMap<String, String>> allUserDatas;
	private LinkedHashMap<String, LinkedHashMap<String, String>> allWeaponDatas;
	private static LinkedHashMap<String, LinkedHashMap<String, String>> allRankDatas;
	private static DataService instance;

	public DataService() {
		super();
		instance = this;

		allGameDatas = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		allUserDatas = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		allWeaponDatas = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		allRankDatas = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		
		gameFileName = "data/gameConfig.txt";
		userFileName = "data/userConfig.txt";
		weaponFileName = "data/weaponConfig.txt";
		rankFileName = "data/rankData.txt";
		try {
			loadConfig(gameFileName); // GameConfig.txt 파일 읽어옴
			loadConfig(userFileName); // UserConfig.txt 파일을 읽어옴
			loadConfig(weaponFileName);
			loadConfig(rankFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean checkId(String id) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream("data/userConfig.txt");
		Scanner scan = new Scanner(fis);

		userDatas = new ArrayList<>();
		boolean isContain = false;

		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			userDatas.add(line);

			String name = line.substring(1, line.length()-1);
			if (name.equals(id))
				isContain = true;
		}
		
		if (isContain == false) {
			userDatas.add("[" + id + "]");
			userDatas.add("level=1");
			userDatas.add("totalScore=0");

			saveUserData(userDatas);
		}
		
		try {
			loadConfig("data/userConfig.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isContain;
	}
	
	public void saveUserData(ArrayList userDatas) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(userFileName);
		pw.print("");
		int count = 1;
		for (Object data : userDatas) {
			System.out.println((String) data);
			if(count < userDatas.size())
				pw.append((String) data + "\n");
			else
				pw.append((String) data);
			count++;
		}
		
		pw.close();
		
		allUserDatas.clear();
		try {
			loadConfig(userFileName);
			System.out.println(getPlayerIntValue("jun", "level"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DataService getInstance() {
		return instance;
	}

	// data 폴더에 존제하는 gameConfig 파일을 읽어옴
	private void loadConfig(String filePath) throws IOException {
		FileInputStream fis = new FileInputStream(filePath);
		Scanner scan = new Scanner(fis);

		LinkedHashMap<String, LinkedHashMap<String, String>> allDatas;

		if (true == filePath.equals(userFileName)) {
			allDatas = allUserDatas;
		} else if (true == filePath.equals(gameFileName)) {
			allDatas = allGameDatas;
		} else if(true == filePath.equals(weaponFileName)) {
			allDatas = allWeaponDatas;
		} else {
			allDatas = allRankDatas;
		}

		String title = "";
		LinkedHashMap<String, String> datas = null;

		while (scan.hasNextLine()) {
			String line = scan.nextLine();

			line.trim();//앞 뒤 공백 제거
			String firstText = line.substring(0, 1);
			if (true == firstText.equals("[")) { // [로 시작하는 라인은 어떤 항목인지를 알려주는 텍스트
				// 새로운 항목을 넣을 건데 기존 항목 데이터가 있으면 전체 데이터에 넣어줌
				if (datas != null) {
					// 전체 데이터에 추가
					allDatas.put(title, datas);
					datas = null;
					title = "";
				}
				//[김병준]  5
				title = line.substring(1, line.length() - 1);
				//System.out.println("change Title : " + title);

			} else if (false == title.equals("")) {
				if (datas == null) { // 세부 항목 읽어올거라 담을 공간 만들어놓음
					datas = new LinkedHashMap<String, String>();
				}
				String[] contents = line.split("=");
				datas.put(contents[0], contents[1]);

			}
		}

		// 다음줄이 없거나, 타이틀이 바뀌면
		if (false == title.equals("") || null != datas) {
			allDatas.put(title, datas);
			datas = null;
			title = "";
		}

		// 출력 확인용
//		for (String key : allRankDatas.keySet()) {
//
//			System.out.println("[" + key + "]");
//			LinkedHashMap<String, String> contents = allRankDatas.get(key);
//			for (String key2 : contents.keySet()) {
//				String value = contents.get(key2);
//				System.out.printf("%s = %s\n", key2, value);
//			}
//		}

		scan.close();
		fis.close();
	}

	public void save(String userName, int level, int totalScore) throws IOException {
	      // 추후 개발 예정
	      PrintWriter pw = new PrintWriter(userFileName);
	      pw.print("");
	      
	      
	      for(int i=0;i<userDatas.size();i++) {
	         if(userDatas.get(i).contains(userName)) {
	            userDatas.set(i+1, "level="+level);
	            userDatas.set(i+2, "totalScore="+totalScore);
	            break;
	         }
	      }
	      
	      for(Object data : userDatas) {
	         pw.append((String)data+"\n");
	      }
	      
	      pw.close();
	      
	      allUserDatas.clear();
			try {
				loadConfig(userFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	
	public void saveRank(String name, int score) throws IOException {
		// 추후 개발 예정
		LinkedHashMap<String, String> datas = allRankDatas.get("rank");
			
		PrintWriter pw = new PrintWriter(rankFileName);
		pw.println("[rank]");
		if(datas != null) {
			// 이전 데이터 저장
			for(String key : datas.keySet()) {
				pw.println(key+"=" + datas.get(key));			
			}	
		}	
		// 추가된 데이터 저장
		pw.print(name+"=" + score);
		pw.close();
		
		allRankDatas.clear();
		loadConfig(rankFileName);
	}

	public ArrayList getWeaponList(int level) {

		ArrayList list = new ArrayList();
		String key = "";
		if (level < 10)
			key = "level" + 1;
		else
			key = "level" + (level / 10 * 10);

		LinkedHashMap<String, String> datas = allWeaponDatas.get(key);
		if (null != datas) {
			for (String s : datas.keySet()) {
				list.add(s);
			}
		}

		return list;
	}

	public ArrayList getWeaponImg(String key) {
		ArrayList list = new ArrayList();

		return list;
	}

	public Stage getStageValue(int stageIndex) {
		Stage stage = new Stage();

		String key = "stage" + stageIndex;
		LinkedHashMap<String, String> datas = allGameDatas.get(key);
		if (null != datas) {
			String limitTime = datas.get("limitTime");
			String mosqCreateCount = datas.get("mosqCreateCount");
			String mosqMaxCount = datas.get("mosqMaxCount");
			String mosqCreateTime = datas.get("mosqCreateTime");
			String mosqMinSpeed = datas.get("mosqMinSpeed");
			String mosqMaxSpeed = datas.get("mosqMaxSpeed");
			String mosqHP = datas.get("mosqHP");
			
			String buttMaxCount = datas.get("buttMaxCount");
			String buttCreateCount = datas.get("buttCreateCount");
			String buttCreateTime = datas.get("buttCreateTime");
			String buttMinSpeed = datas.get("buttMinSpeed");
			String buttMaxSpeed = datas.get("buttMaxSpeed");
			String buttHP = datas.get("buttHP");

			String killScore = datas.get("killScore");

			// 값 넣기
			stage.limitTime = Integer.parseInt(limitTime);
			stage.mosqCreateCount = Integer.parseInt(mosqCreateCount);
			stage.mosqMaxCount = Integer.parseInt(mosqMaxCount);
			stage.mosqCreateTime = Integer.parseInt(mosqCreateTime);
			stage.mosqMinSpeed = Integer.parseInt(mosqMinSpeed);
			stage.mosqMaxSpeed = Integer.parseInt(mosqMaxSpeed);
			stage.mosqHP = Integer.parseInt(mosqHP);

			// 값 넣기
			stage.buttMaxCount = Integer.parseInt(buttMaxCount);
			stage.buttCreateCount = Integer.parseInt(buttCreateCount);
			stage.buttCreateTime = Integer.parseInt(buttCreateTime);
			stage.buttMinSpeed = Integer.parseInt(buttMinSpeed);
			stage.buttMaxSpeed = Integer.parseInt(buttMaxSpeed);
			stage.buttHP = Integer.parseInt(buttHP);
			
			stage.killScore = Integer.parseInt(killScore);
		}

		return stage;
	}

	public int getGameIntValue(String key, String attribute) {
		String data = allGameDatas.get(key).get(attribute);
		int value = 0;
		if (false == data.equals("")) {
			value = Integer.parseInt(data);
		}
		return value;
	}

	public int getPlayerIntValue(String key, String attribute) {
		
		for (String name : allUserDatas.keySet()) {

			System.out.println("[" + name + "]");
			LinkedHashMap<String, String> contents = allUserDatas.get(name);
			for (String key2 : contents.keySet()) {
				String value = contents.get(key2);
				System.out.printf("%s = %s\n", key2, value);
			}
		}
		
		String data = allUserDatas.get(key).get(attribute);
		int value = 0;
		if (false == data.equals("")) {
			value = Integer.parseInt(data);
		}
		return value;
	}

	public String getGameStringValue(String key, String attribute) {
		String result = allGameDatas.get(key).get(attribute);
		return result;
	}

	public String getWeaponStringValue(String key, String attribute) {
		String result = allWeaponDatas.get(key).get(attribute);
		return result;
	}

	public int getUserIntValue(String key, String attribute) {
		String data = allUserDatas.get(key).get(attribute);
		int value = 0;
		if (false == data.equals("")) {
			value = Integer.parseInt(data);
		}
		return value;
	}

	public String getUserStringValue(String key, String attribute) {
		String result = allUserDatas.get(key).get(attribute);
		return result;
	}

	public Free getFreeValue(String freeStage) {
		Free free = new Free();

		LinkedHashMap<String, String> datas = allGameDatas.get(freeStage);
		if (null != datas) {
			String limitTime = datas.get("limitTime");
			String mosqCreateCount = datas.get("mosqCreateCount");
			String mosqMaxCount = datas.get("mosqMaxCount");
			String mosqCreateTime = datas.get("mosqCreateTime");
			String mosqMinSpeed = datas.get("mosqMinSpeed");
			String mosqMaxSpeed = datas.get("mosqMaxSpeed");
			String mosqHP = datas.get("mosqHP");

			String buttMaxCount = datas.get("buttMaxCount");
			String buttCreateCount = datas.get("buttCreateCount");
			String buttCreateTime = datas.get("buttCreateTime");
			String buttMinSpeed = datas.get("buttMinSpeed");
			String buttMaxSpeed = datas.get("buttMaxSpeed");
			String buttHP = datas.get("buttHP");
			
			String killScore = datas.get("killScore");

			// 값 넣기
			free.limitTime = Integer.parseInt(limitTime);
			free.mosqCreateCount = Integer.parseInt(mosqCreateCount);
			free.mosqMaxCount = Integer.parseInt(mosqMaxCount);
			free.mosqCreateTime = Integer.parseInt(mosqCreateTime);
			free.mosqMinSpeed = Integer.parseInt(mosqMinSpeed);
			free.mosqMaxSpeed = Integer.parseInt(mosqMaxSpeed);
			free.mosqHP = Integer.parseInt(mosqHP);

			// 값 넣기
			free.buttMaxCount = Integer.parseInt(buttMaxCount);
			free.buttCreateCount = Integer.parseInt(buttCreateCount);
			free.buttCreateTime = Integer.parseInt(buttCreateTime);
			free.buttMinSpeed = Integer.parseInt(buttMinSpeed);
			free.buttMaxSpeed = Integer.parseInt(buttMaxSpeed);
			free.buttHP = Integer.parseInt(buttHP);
			free.killScore = Integer.parseInt(killScore);
		}

		return free;
	}
	
	public LinkedHashMap<String, Integer> getAllRankData() {
		// 이름과 점수를 보냄
		LinkedHashMap<String, String> datas = allRankDatas.get("rank");
		
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		if(datas != null) {
			for(String key : datas.keySet()) {
				String value = datas.get(key);
				int score = Integer.parseInt(value);
				result.put(key, score);
			}
		}	
		
		return result;
	}
	
}
