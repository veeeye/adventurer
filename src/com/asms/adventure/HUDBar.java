package com.asms.adventure;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

public class HUDBar {
	
	private int width;
	private Rectangle bar;
	private Rectangle borderTop;
	private Rectangle borderRight;
	private Rectangle borderBottom;
	
	public HUDBar(GameHUD gamehud, int pX, int pY, int width, int height, Color barColor, float barAlpha, int borderWidth, Color borderColor, VertexBufferObjectManager vbo) {
		this.width = width;
		bar = new Rectangle(pX, pY, width, height, vbo);
		
		bar.setColor(barColor);
		bar.setAlpha(barAlpha);
		
		borderTop = new Rectangle(pX, pY - borderWidth, width + borderWidth, borderWidth, vbo);
		borderRight = new Rectangle(pX + width, pY - borderWidth, borderWidth, height + 2 * borderWidth, vbo);
		borderBottom = new Rectangle(pX, pY + height, width + borderWidth, borderWidth, vbo);
		
		borderTop.setColor(borderColor);
		borderRight.setColor(borderColor);
		borderBottom.setColor(borderColor);
		
		gamehud.attachChild(bar);
		gamehud.attachChild(borderTop);
		gamehud.attachChild(borderRight);
		gamehud.attachChild(borderBottom);
	}
	
	public void set(float fraction) {
		bar.setWidth(width * fraction);
	}
}
