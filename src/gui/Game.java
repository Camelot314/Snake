package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Game loop class. Has the game loop that calls the render and tick.
 * Copyright (c) 2021. Singleton class.
 * @author Jaraad Kamal
 *
 */
public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 242446397740476223L;
	public static final int WIDTH = 900, HEIGHT = WIDTH / 12 * 9;
	private static Game instance = null;
	public double ticksPerSecond = 15.0, timeBetweenTick = 1000000000 / ticksPerSecond;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private Window window;
	private GameState state = GameState.MENU;

	/**
	 * Private constructor for singleton class.
	 */
	private Game() {
		handler = Handler.getInstance(WIDTH, HEIGHT, this, false);
		
		this.addKeyListener(KeyInput.getInstance(handler));
		this.addMouseListener(MouseInput.getInstance());
	}
	
	/**
	 * Public getInstance method that returns the one instance of the class.
	 * @return Game instance.
	 */
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
			instance.window = Window.getInstance(WIDTH + 16, HEIGHT + 37, "Snake", instance);
			instance.window.addGame();
		}
		return instance;
	}
	
	
	
	/**
	 * single threaded game. Fine for smaller games not so for larger ones.
	 */
	public synchronized void start() {
		if (!running) {
			thread = new Thread(this);
			thread.start();
		}
		running = true;
	}
	
	/**
	 * This is the stop method that stops the game loop.
	 */
	public synchronized void stop() {
		if (running) {
			try {
				thread.join();
				running = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Method called by the thread; this is the game loop
	 */
	public void run() {
		long lastTime = System.nanoTime();
		timeBetweenTick = 1000000000 / ticksPerSecond;
		double delta = 0;
		long timer = System.currentTimeMillis();
//		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / timeBetweenTick;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta --;
			}
			if (running) {
				render();
			}
//			frames ++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println("FPS: " + frames);
//				frames = 0;
			}
		}
		stop();
		
		
	}
	
	/**
	 * Sets the game sate to the given parameter.
	 * @param state
	 */
	public void setState(GameState state) {
		this.state = state;
	}
	
	/**
	 * sets the tick rate to the given value.
	 * @param ticksPerSecond
	 */
	public void setTickRate(double ticksPerSecond) {
		if (ticksPerSecond == 0) {
			throw new IllegalArgumentException("ticksPerSecond cannot be 0");
		}
		this.ticksPerSecond = ticksPerSecond; 
		this.timeBetweenTick = 1000000000 / ticksPerSecond;
	}
	
	/**
	 * Gets the tick rate.
	 * @return the current tick rate.
	 */
	public double getTickRate() {
		return ticksPerSecond;
	}
	
	/**
	 * Gets the current game state. Whether it is running the game logic
	 * or in the menu or otherwise. 
	 * @return enumerator State indicating the game state. 
	 */
	public GameState getState() {
		return state;
	}
	
	/**
	 * Performs the logic for the next frame.
	 */
	private void tick() {
		handler.tick();
	}
	
	/**
	 * Creates a buffer and renders.
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
}
