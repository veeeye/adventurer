package com.asms.adventure;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

public class GameHUD extends HUD {
	
	private HUDBar healthBar;
	private HUDBar magicBar;
	private HUDBar staminaBar;
	
	final private float BAR_ALPHA = 0.5f;
	final private int BAR_X_START = 0;
	final private int BAR_WIDTH = 128;
	final private int BAR_HEIGHT = 8;
	final private int BAR_BORDER_WIDTH = 1;
	
	final private int BAR_Y_SPACING = 4;
	
	public GameHUD(VertexBufferObjectManager vbo) {
		healthBar = new HUDBar(this, BAR_X_START, BAR_Y_SPACING * (0 + 1) + BAR_HEIGHT * 0, BAR_WIDTH, BAR_HEIGHT, Color.RED, BAR_ALPHA, BAR_BORDER_WIDTH, Color.BLACK, vbo);
		magicBar = new HUDBar(this, BAR_X_START, BAR_Y_SPACING * (1 + 1) + BAR_HEIGHT * 1, BAR_WIDTH, BAR_HEIGHT, Color.BLUE, BAR_ALPHA, BAR_BORDER_WIDTH, Color.BLACK, vbo);
		staminaBar = new HUDBar(this, BAR_X_START, BAR_Y_SPACING * (2 + 1) + BAR_HEIGHT * 2, BAR_WIDTH, BAR_HEIGHT, Color.GREEN, BAR_ALPHA, BAR_BORDER_WIDTH, Color.BLACK, vbo);
	}
	
	public void setHealth(float fraction) {
		this.healthBar.set(fraction);
	}
	
	public void setMagic(float fraction) {
		this.magicBar.set(fraction);
	}
	
	public void setStamina(float fraction) {
		this.staminaBar.set(fraction);
	}
}
