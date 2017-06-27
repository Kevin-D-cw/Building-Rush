package com.mc.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.awt.*;

public class BuildingRush extends ApplicationAdapter {
	SpriteBatch batch;
	
	Texture menuBg, newgameButton, tutorialButton, title, //basic & stat textures
	map1, rightStat, topStat, menuPane, musicPlayButton, 
	musicStopButton, soundPlayButton, soundStopButton, pauseButton, buyTruckButton, 
	sellTruckButton, truckStat, postOffPic, truckIcon, moneyIcon, 
	profitIcon, oneStar, twoStar, threeStar, buildCostBox, 
	moneyStatBox, timeBox, truckPic, resumeButton, restartButton, 
	quitButton, tut1, tut2, tut3, 
	
	yourPostOffice, blueHouse, greenHouse, brownFactoryBack, brownFactoryFront, //buildings and decor textures
	brownFactorySide, bush, flowerO, flowerPi, flowerY, 
	flowerB, flowerPu, fountain, grayFactoryFront, grayFactorySide, 
	grayFactorySideFlip, hospital, icecreamShop, library, mailbox,
	museum, mud1, mud2, mud3, mudGroup, 
	shedBack, shedFront, tree, orderBubble, truckUp, truckRight, truckDown, truckLeft;
	
	
	Rectangle newgameButtonArea, continueButtonArea, tutorialButtonArea, profitStatBox, bronzeBar, 
	silverBar, goldBar, buyTruckButtonArea, sellTruckButtonArea, resumeButtonArea,
	restartButtonArea, quitButtonArea, tutArea;
	
	Circle musicButtonArea, soundButtonArea, pauseButtonArea;
	
	int page, moneyTotal, futureEarnings, profit, buildCost, 
	buyTruckPrice, sellTruckPrice, musicState, soundState, postOfficeIndex, 
	remainMins, remainSecs, bronze, silver, gold, 
	availableTruck, tutPicIndex;
	
	long tStart, tEnd, tDelta, nextTick, tStart2,
	tEnd2, tDelta2;
	double elapsedSeconds;
	BitmapFont fontCB20, fontCB40, fontAJ20, fontAJ20Y, fontAJ18, fontAJ18Y, fontAJ18O;
	FreeTypeFontGenerator coopBl, arJul;
	FreeTypeFontParameter parameter;
	ShapeRenderer shapeRenderer;
	Music menuMusic, themeMusic, receivedSound;
	ScheduledExecutorService executorService;
	Point[] customers;
	Point[] postOffices;
	ArrayList<Order> currentOrders;
	ArrayList<Truck> truckList;
	Random rand;
	Path path;
	Texture[] tut;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		page = 0;
		moneyTotal = 300;
		futureEarnings = 0;
		profit = 0;
		buildCost = 20;
		buyTruckPrice = 30;
		sellTruckPrice = 15;
		musicState = 1;
		soundState = 1;
		postOfficeIndex = 16;
		bronze = 235 * 3;
		silver = 410 * 3;
		gold = 580 * 3;
		availableTruck = 0;
		tutPicIndex = -1;
		
		menuBg = new Texture("menuBg.jpg");
		title = new Texture("title2.png");
		newgameButton = new Texture("newgameButton.png");
		newgameButtonArea = new Rectangle(100, 330, newgameButton.getWidth(), newgameButton.getHeight());
		tutorialButton = new Texture("tutorialButton.png");
		tutorialButtonArea = new Rectangle(100, 180, tutorialButton.getWidth(), tutorialButton.getHeight());
		rightStat = new Texture("rightStats.png");
		topStat = new Texture("topStats.png");
		menuPane = new Texture("mainButtonPane.png");
		musicPlayButton = new Texture("buttons3015.png");
		musicStopButton = new Texture("buttons3017.png");
		soundPlayButton = new Texture("buttons3018.png");
		soundStopButton = new Texture("buttons3019.png");
		pauseButton = new Texture("buttons3008.png");
		musicButtonArea = new Circle(1206, 800, 22);
		soundButtonArea = new Circle(1252, 800, 22);
		pauseButtonArea = new Circle(1229, 757, 22);
		buyTruckButton = new Texture("buttons2012.png");
		sellTruckButton = new Texture("buttons2004.png");
		buyTruckButtonArea = new Rectangle(1110, 520, 75, 75);
		sellTruckButtonArea = new Rectangle(1190, 520, 75, 75);
		truckStat = new Texture("buttons2017.png");
		postOffPic = new Texture("postOffIcon.png");
		truckIcon = new Texture("truckIcon2.png");
		moneyIcon = new Texture("moneyIcon.png");
		profitIcon = new Texture("profitIcon.png");
		oneStar = new Texture("buttons3021.png");
		twoStar = new Texture("buttons3022.png");
		threeStar = new Texture("buttons3023.png");
		buildCostBox = new Texture("BlackStatSmall.png");
		moneyStatBox = new Texture("BlackStatLong.png");
		timeBox = new Texture("BlackStatTime.png");
		yourPostOffice = new Texture("postOffice.png");
		blueHouse = new Texture("blueHouse.png");
		greenHouse = new Texture("greenHouse.png");
		brownFactoryBack = new Texture("brownFactoryBack.png");
		brownFactoryFront = new Texture("brownFactoryFront.png");
		brownFactorySide = new Texture("brownFactorySide.png");
		bush = new Texture("bush.png");
		flowerO = new Texture("flowers001.png");
		flowerPi = new Texture("flowers002.png");
		flowerY = new Texture("flowers003.png");
		flowerB = new Texture("flowers004.png");
		flowerPu = new Texture("flowers005.png");
		fountain = new Texture("fountain.png");
		grayFactoryFront = new Texture("grayFactoryFront.png");
		grayFactorySide = new Texture("grayFactorySide.png");
		grayFactorySideFlip = new Texture("grayFactorySideFlip.png");
		hospital = new Texture("hospital.png");
		icecreamShop = new Texture("icecreamShop.png");
		library = new Texture("library.png");
		mailbox = new Texture("mailbox.png");
		museum = new Texture("museum.png");
		mud1 = new Texture("mud1.png");
		mud2 = new Texture("mud2.png");
		mud3 = new Texture("mud3.png");
		mudGroup = new Texture("mudGroup.png");
		shedBack = new Texture("shedBack.png");
		shedFront = new Texture("shedFront.png");
		tree = new Texture("tree.png");
		orderBubble = new Texture("speechBubble.png");
		map1 = new Texture("map1.jpg");
		truckUp = new Texture("truckUp.png");
		truckRight = new Texture("truckRight.png");
		truckDown = new Texture("truckDown.png");
		truckLeft = new Texture("truckLeft.png");
		tut1 = new Texture("TutorialPic1.png");
		tut2 = new Texture("TutorialPic2.png");
		tut3 = new Texture("TutorialPic3.png");
		resumeButton = new Texture("buttons2002.png");
		restartButton = new Texture("buttons2006.png");
		quitButton = new Texture("buttons2010.png");
		resumeButtonArea = new Rectangle(350, 600, 324, 98);
		restartButtonArea = new Rectangle(350, 400, 324, 98);
		quitButtonArea = new Rectangle(350, 200, 324, 98);
		
		coopBl = new FreeTypeFontGenerator(Gdx.files.internal("COOPBL.ttf"));
		arJul = new FreeTypeFontGenerator(Gdx.files.internal("ARJULIAN.ttf"));
		parameter = new FreeTypeFontParameter();
		
		parameter.size = 20;
		fontCB20 = coopBl.generateFont(parameter);
		fontCB20.setColor(Color.WHITE);
		
		parameter.size = 40;
		fontCB40 = coopBl.generateFont(parameter);
		fontCB40.setColor(Color.WHITE);
		
		parameter.size = 20;
		fontAJ20 = arJul.generateFont(parameter);
		fontAJ20.setColor(Color.WHITE);
		
		fontAJ20Y = arJul.generateFont(parameter);
		fontAJ20Y.setColor(Color.GOLD);
		
		parameter.size = 18;
		fontAJ18 = arJul.generateFont(parameter);
		fontAJ18.setColor(Color.BLACK);
		
		fontAJ18Y = arJul.generateFont(parameter);
		fontAJ18Y.setColor(Color.GOLD);
		
		fontAJ18O = arJul.generateFont(parameter);
		fontAJ18O.setColor(Color.ORANGE);
		
		shapeRenderer = new ShapeRenderer();
		
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("[Drumstep] - Tristam & Braken - Flight [Monstercat Release].mp3"));
		themeMusic = Gdx.audio.newMusic(Gdx.files.internal("[Drumstep] - Tristam & Braken - Flight [Monstercat Release].mp3"));
		receivedSound = Gdx.audio.newMusic(Gdx.files.internal("right.mp3"));
		
		customers = new Point[] {new Point(5,10), new Point(11,10), new Point(9,8), new Point(12,8), new Point(6,7), 
				new Point(11,6), new Point(15,6), new Point(6,4), new Point(8,4), new Point(13,4), new Point(15,4), new Point(3,3), 
				new Point(8,2), new Point(13,2), new Point(17,2)};
		
		currentOrders= new ArrayList<Order>();
		truckList= new ArrayList<Truck>();
		truckList.add(new Truck(1));
		
		postOffices = new Point[] {new Point(15,2), new Point(9,6), new Point(6,2)};
		
		rand = new Random();
		
		executorService = Executors.newSingleThreadScheduledExecutor();
		
		path = new Path();
		
		
		
		tut = new Texture[3];
		tut[0] = tut1;
		tut[1] = tut2;
		tut[2] = tut3;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (page == 0) {
			menu();
		}
		
		if (page == 1) {
			tutorial();
		}
		
		if (page == 2) {
			game();
		}
		
		if (page == 3) {
			pause();
		}
		
		if (page == 4) {
			result();
		}
	}
	
	@Override
	public void dispose () {
		
	}
	
	public void menu () {
		batch.begin();
		batch.draw(menuBg, 0, 0);
		batch.draw(title, 740, 20);
		
		if (tutorialButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				tutPicIndex = -1;
		        page = 1;
		    }
		}
		
		batch.draw(tutorialButton, 100, 180);
		
		if (newgameButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
		        page = 2;
		        tStart = System.currentTimeMillis();
				nextTick = System.currentTimeMillis() + 1000;
		    }
		}
		
		batch.draw(newgameButton, 100, 330);
		batch.end();
		
		//Borders for menu buttons
		Gdx.gl.glLineWidth(3);
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 1, 1);
		
		if (newgameButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.rect(100, 330, newgameButton.getWidth(), newgameButton.getHeight());
		}
		
		if (tutorialButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.rect(100, 180, tutorialButton.getWidth(), tutorialButton.getHeight());
		}
		
		shapeRenderer.end();
	}
	
	public void tutorial () {
		
		batch.begin();
		
		if (Gdx.input.justTouched()) {
			tutPicIndex += 1;
		}
		
		if (tutPicIndex == 0) {
			batch.draw(tut[0], 0, 0, 1280, 832);
			
		}
		
		if (tutPicIndex == 1) {
			batch.draw(tut[1], 0, 0, 1280, 832);
		}
		
		if (tutPicIndex == 2) {
			batch.draw(tut[2], 0, 0, 1280, 832);
		}
		
		if (tutPicIndex == 3) {
			page = 0;
		}
		
		batch.end();
	}

	public void game () {
		updateOrders();
		draw1();
		musicCheck();
		soundCheck();
		countTruck();
		draw2();
		remainTime();
		draw3();
		drawTruck();
		draw4();
		gamebuttonCheck();
		drawOrders();
		checkTime();
		checkLife();
		pickOffice();
		checkResult();
	}

	public void pause () {
		batch.begin();
		batch.draw(menuBg, 0, 0);
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(1, 1, 1, 0.5f);
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.end();
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		batch.begin();
		batch.draw(resumeButton, 350, 600);
		batch.draw(restartButton, 350, 400);
		batch.draw(quitButton, 350, 200);
		fontCB40.draw(batch, "Resume", 450, 675);
		fontCB40.draw(batch, "Restart", 450, 475);
		fontCB40.draw(batch, "Quit", 450, 275);
		batch.end();
		
		shapeRenderer.begin(ShapeType.Line);
		
		if (resumeButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.rect(350, 600, 324, 98);
			if (Gdx.input.justTouched()) {
				tEnd2 = System.currentTimeMillis();
				tDelta2 = tEnd2- tStart2;
				tStart += tDelta2;
				page = 2;
		    }
		}
		
		if (restartButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.rect(350, 400, 324, 98);
			if (Gdx.input.justTouched()) {
		        create();
				page = 2;
				tStart = System.currentTimeMillis();
				nextTick = System.currentTimeMillis() + 1000;
		    }
		}
		
		if (quitButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.rect(350, 200, 324, 98);
			if (Gdx.input.justTouched()) {
		        create();
				page = 0;
		    }
		}
		
		shapeRenderer.end();
	}

	public void result() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(218/255f, 165/255f, 32/255f, 1);
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.end();
		
		batch.begin();
		
		if (profit >= bronze) {
			fontCB40.draw(batch, "You win :)",500, 500);
			fontCB40.draw(batch, "You profit is: $" + profit, 500, 450);
			if (profit >= gold) {
				batch.draw(threeStar, 600,200);
			}
			else if (profit >= silver) {
				batch.draw(twoStar, 600,200);
			}
			else {
				batch.draw(oneStar, 600,200);
			}
		}
		else {
			fontCB40.draw(batch, "You lose :(", 500, 500);
			fontCB40.draw(batch, "You profit is: $" + profit, 500, 450);
		}
		
		batch.end();
	}
	
	public void updateOrders() {
		while(currentOrders.size()<5) { //max 5 orders at any given time
			boolean free=true; //for checking if the random location is already ordering
			int orders = rand.nextInt(15); //picks random index from customers list
			Order hello = new Order(customers[orders]); //creates an order for selected location
			for(Order curr : currentOrders) {
				if(curr.getLocation().equals(customers[orders])) { //if that location is already ordering 
					free=false; 
					break;
				}
			}
			if(free) { //if customer is available to order
				currentOrders.add(hello); //add it to the current orders list
			}
		}
	}
	
	public void draw1() {
		batch.begin();
		batch.draw(map1, 0, 0);
		batch.draw(rightStat, 1088, 512);
		batch.draw(topStat, 125, 747);
		batch.draw(menuPane, 1178, 728, 100, 100);
		batch.draw(moneyStatBox, 1100, 710, 70, 115);
		batch.end();
	}
	
	//Checks the state of the music (depends on state of music button)
	//stop/plays music & draws representative icon accordingly 
	public void musicCheck() {
		batch.begin();
		if (musicState == 1) {
			menuMusic.play();
			batch.draw(musicStopButton, 1184, 778, 43, 43);
		}
		else {
			menuMusic.stop();
			batch.draw(musicPlayButton, 1184, 778, 43, 43);
		}
		batch.end();
	}
	
	public void soundCheck() {
		batch.begin();
		if (soundState == 0) {
			batch.draw(soundPlayButton, 1230, 778, 43, 43);
		}
		else {
			batch.draw(soundStopButton, 1230, 778, 43, 43);
		}
		batch.end();
	}
	
	public void countTruck() {
		availableTruck = 0;
		for (Truck i : truckList) {
			if (i.isAvailable == true) {
				availableTruck += 1;
			}
		}
	}
	
	public void draw2() {
		batch.begin();
		batch.draw(pauseButton, 1207, 735, 43, 43);
		batch.draw(buyTruckButton, 1110, 520, 75, 75);
		batch.draw(sellTruckButton, 1190, 520, 75, 75);
		fontAJ20.draw(batch, "$" + buyTruckPrice, 1130, 550);
		fontAJ20.draw(batch, "$" + sellTruckPrice, 1215, 550);
		fontCB20.draw(batch, "BUY", 1122.5f, 580);
		fontCB20.draw(batch, "SELL", 1200, 580);
		fontAJ18Y.draw(batch, "$"+ moneyTotal, 1110, 800);
		fontAJ18O.draw(batch, "+$"+ futureEarnings, 1110, 760);
		batch.draw(truckStat,1102,600,170,50);
		fontAJ20.draw(batch,availableTruck + " / " + truckList.size(), 1200, 630);
		batch.draw(postOffPic,1195,660,60,60);
		batch.draw(truckIcon, 1110, 595, 50, 50);
		batch.draw(moneyIcon, 1050, 760, 64, 64);
		batch.end();
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.DARK_GRAY);
		shapeRenderer.rect(350, 770, 235, 45);
		shapeRenderer.rect(590, 770, 175, 45);
		shapeRenderer.rect(770, 770, 160, 45);
		shapeRenderer.rect(155, 767, 150, 48);
		shapeRenderer.setColor(new Color(255f/256, 215f/256, 0, 1));
		if (profit < 570 * 3) {
			shapeRenderer.rect(350, 770, (float) profit / gold * 580, 45);
		}
		else {
			shapeRenderer.rect(350, 770, 580, 45);
		}
		
		shapeRenderer.end();
	}
	
	public void remainTime() {
		tEnd = System.currentTimeMillis();
		tDelta = tEnd - tStart;
		elapsedSeconds = tDelta / 1000.0;
		remainMins = (int) ((300 - elapsedSeconds) / 60);
		remainSecs = (int) ((300 - elapsedSeconds) % 60);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.arc(0, 832, 120, 270, (float) elapsedSeconds / 300 * 90);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.arc(0, 832, 100, 270, 90);
		shapeRenderer.end();
		
		batch.begin();
		batch.draw(timeBox, 0, 765, 70, 50);
		if (remainSecs < 10) {
			fontAJ20Y.draw(batch, remainMins + ":0" + remainSecs, 10, 800);
		}
		else {
			fontAJ20Y.draw(batch, remainMins + ":" + remainSecs, 10, 800);
		}
		batch.end();
	}
	
	public void draw3() {
		batch.begin();
		batch.draw(oneStar, 560, 750, 55, 55);
		batch.draw(twoStar, 740, 750, 55, 55);
		batch.draw(threeStar, 900, 750, 55, 55);
		batch.draw(profitIcon, 135, 760, 64, 64);
		fontAJ20Y.draw(batch, "$" + profit, 220, 800);
		batch.draw(buildCostBox, 1110, 665, 70, 35);
		fontAJ20.draw(batch, "$" + buildCost, 1130, 690);
		batch.draw(yourPostOffice, 575, 380, 64, 70);
		batch.draw(yourPostOffice, 384, 125, 64, 70);
		batch.draw(yourPostOffice, 960, 125, 64, 70);
		batch.draw(blueHouse, 706, 380, 60, 60);
		batch.draw(greenHouse, 514, 256, 60, 60);
		batch.draw(greenHouse, 898, 638, 60, 60);
		batch.draw(brownFactoryBack, 765, 470, 70, 100);
		batch.draw(brownFactoryFront, 1085, 125, 70, 100);
		batch.draw(brownFactorySide, 384, 448, 60, 100);
		batch.draw(brownFactorySide, 192, 185, 60, 100);
		batch.draw(bush, 650, 380, 20, 20);
		batch.draw(bush, 340, 340, 20, 20);
		batch.draw(bush, 30, 150, 20, 20);
		batch.draw(bush, 600, 210, 20, 20);
		batch.draw(bush, 723, 210, 20, 20);
		batch.draw(flowerO, 630, 470);
		batch.draw(mudGroup, 625, 265);
		batch.draw(flowerPi, 665, 275);
		batch.draw(flowerY, 645, 275);
		batch.draw(flowerB, 685, 275);
		batch.draw(flowerPu, 650, 480);
		batch.draw(fountain, 641, 192);
		batch.draw(fountain, 192, 320);
		batch.draw(fountain, 192, 64);
		batch.draw(grayFactoryFront, 820, 125, 70, 100);
		batch.draw(grayFactorySide, 384, 256, 60, 100);
		batch.draw(grayFactorySideFlip, 962, 380, 60, 100);
		batch.draw(hospital, 962, 256, 60, 80);
		batch.draw(icecreamShop, 832, 380, 60, 60);
		batch.draw(icecreamShop, 320, 635, 61, 60);
		batch.draw(library, 640, 640, 128, 80);
		//batch.draw(mailbox);
		batch.draw(museum, 514, 126, 60, 78);
		batch.draw(shedBack, 832, 256, 60, 60);
		batch.draw(shedBack, 576, 512, 60, 60);
		batch.draw(shedFront, 450, 640, 60, 60);
		batch.end();
	}
	
	public void drawTruck() {
		//draws trucks every 1 second
		batch.begin();
		for (Truck tr : truckList) {
			if (tr.isAvailable == false) {
				tr.updateTruck();
				
				//rotate sprite truck based on direction
				if (tr.getDir() == 0) { //up
					batch.draw(truckUp, tr.getX()+10, tr.getY()+10);
				}
				
				if (tr.getDir() == 1) { //left
					batch.draw(truckLeft, tr.getX()+10, tr.getY()+10);
				}
				
				else if (tr.getDir() == 2) { //right
					batch.draw(truckRight, tr.getX()+10, tr.getY()+10);
				}
				
				else if(tr.getDir() == 3){ //down
					batch.draw(truckDown, tr.getX()+10, tr.getY()+10);
				}
				
				if (tr.destCheck()) { //if the truck reached its customer
					playSound();
					for(Order curr : currentOrders) {
						if(curr.getLocation().equals(customers[tr.getDestNode()])) {
							curr.addRecieved();
							curr.checkCompletion();
							profit += curr.getPrice()- curr.getMaterialCost();
							futureEarnings -= curr.getPrice()- curr.getMaterialCost();
							moneyTotal += curr.getPrice();
							curr.addTime();
							
						}
					}
				}
			}
		}
		batch.end();
	}
	
	//Checks the state of the sound effect (depends on state of sound button)
	//plays if sound is is set to on
	public void playSound() {
		if (soundState == 1) {
			receivedSound.play();
		}
	}
	
	public void draw4() {
		batch.begin();
		batch.draw(tree, 600, 150);
		batch.draw(tree, 630, 150);
		batch.draw(tree, 660, 150);
		batch.draw(tree, 690, 150);
		batch.draw(tree, 720, 150);
		batch.end();
	}
	
	//checks the status of different buttons on our game screen
	//performs different actions if clicked
	public void gamebuttonCheck() {
		Gdx.gl.glLineWidth(3);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 0, 0, 1); //red border when hovering over buttons
		
		if (musicButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.circle(1206, 800, 22);
			if (Gdx.input.justTouched()) {
		        musicState = 1 - musicState;
		    }
		}
		
		if (soundButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.circle(1252, 800, 22);
			if (Gdx.input.justTouched()) {
		        soundState = 1 - soundState;
		    }
		}
		
		if (pauseButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.circle(1229, 757, 22);
			if (Gdx.input.justTouched()) {
		        page = 3;
		        //////////////////////////
		        tStart2 = System.currentTimeMillis();
		        //////////////////////////
		    }
		}
		
		for (Order curr : currentOrders) {
			if (curr.getArea().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
				ArrayList<Integer> newPath = path.getPath(postOfficeIndex, Arrays.asList(customers).indexOf(curr.getLocation()));
				
				for (int j = 0; j < newPath.size() - 1; j++) {
					Gdx.gl.glLineWidth(10);
					shapeRenderer.setColor(Color.ORANGE);
					shapeRenderer.line(64 * path.getVerticies()[newPath.get(j)].x + 32, 64 * path.getVerticies()[newPath.get(j)].y + 24, 64 * path.getVerticies()[newPath.get(j + 1)].x + 32, 64 * path.getVerticies()[newPath.get(j + 1)].y + 24);
					System.out.println(path.getVerticies()[newPath.get(j)].x + ", " + path.getVerticies()[newPath.get(j)].y + ", " + path.getVerticies()[newPath.get(j + 1)].x + ", " + path.getVerticies()[newPath.get(j + 1)].y);
				}
				
				Gdx.gl.glLineWidth(3);
				shapeRenderer.setColor(Color.BLUE);
				shapeRenderer.rect(64 * curr.getLocation().x, 64 * curr.getLocation().y + 40, 78, 60);
				shapeRenderer.end();
				
				if (Gdx.input.justTouched() && moneyTotal >= curr.getMaterialCost()) {
					for (Truck tr : truckList) {
						if (tr.isAvailable) {
							tr.setTruckPath(newPath);
							futureEarnings += curr.getPrice()- curr.getMaterialCost();
							moneyTotal -= curr.getMaterialCost();
							break;
						}
					}
				}
			}
		}
		
		shapeRenderer.setColor(1, 1, 1, 1); //white border when hovering over buttons
		
		if (buyTruckButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.rect(1110, 520, 75, 75);
			if (Gdx.input.justTouched() && moneyTotal >= buyTruckPrice) {
		        truckList.add(new Truck(truckList.size() + 1));
		        System.out.println(truckList);
		        moneyTotal -= buyTruckPrice;
		    }
		}
		
		if (sellTruckButtonArea.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			shapeRenderer.rect(1190, 520, 75, 75);
			if (Gdx.input.justTouched() && truckList.size() != 0) {
		        truckList.remove(truckList.size() - 1);
		        System.out.println(truckList);
		        moneyTotal += sellTruckPrice;
		    }
		}
		
		shapeRenderer.end();
	}
	
	public void drawOrders() {
		for (Order curr : currentOrders) {
			batch.begin();
			batch.draw(orderBubble, 64 * curr.getLocation().x, 64 * curr.getLocation().y + 20, 80, 80);
			fontAJ18.draw(batch, curr.getReceived() + "/" + curr.getRequested(), 64 * curr.getLocation().x + 20, 64 * curr.getLocation().y + 90);
			fontAJ18.draw(batch, "$" + curr.getPrice(), 64 * curr.getLocation().x + 20, 64 * curr.getLocation().y + 65);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(Color.LIGHT_GRAY);
			shapeRenderer.rect(64 * curr.getLocation().x + 62, 64 * curr.getLocation().y + 46, 10, 50);
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.rect(64 * curr.getLocation().x + 62, 64 * curr.getLocation().y + 46, 10, 50 * curr.getTimeLim() / 30);
			shapeRenderer.end();
		}
	}
	
	//every 1 sec
	//go through the for loop to check currOrders
	//update timer for each Order
	public void checkTime() {
		if (System. currentTimeMillis() >= nextTick) {//Check to see if it is time or past time to update
	        nextTick += 1000;
			for(Order curr : currentOrders) {
				curr.updateTime();
			}
		}
	}
	
	//Checks if the order time limits are up
	public void checkLife() {
		Iterator<Order> iter = currentOrders.iterator();
		while (iter.hasNext()) {
		    Order curr = iter.next();
		    if (curr.getIsFinished()) {
		        iter.remove();
		    }
		}
	}
	
	public void pickOffice() {	
		shapeRenderer.begin(ShapeType.Line);
		
		for (Point i : postOffices) {
			shapeRenderer.setColor(1 , 0, 0, 1);
			shapeRenderer.rect(i.x * 64, i.y * 64, 64, 70);
			Rectangle temp = new Rectangle(i.x * 64, i.y * 64, 64, 70);
			if (temp.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
				shapeRenderer.setColor(1, 1, 0, 1);
				shapeRenderer.rect(i.x * 64, i.y * 64, 64, 70);
				if (Gdx.input.justTouched()) {
					postOfficeIndex = Arrays.asList(postOffices).indexOf(i) + 15; //temporary number,post office node number
			    }
			}
		}
		
		shapeRenderer.end();
	}

	public void checkResult() {
		if (elapsedSeconds >= 300) {
			page = 4;
		}
	}
}