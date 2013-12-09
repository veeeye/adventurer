package com.asms.adventure;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperty;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import com.asms.adventure.activities.GameActivity;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import android.content.res.AssetManager;

public class Sign extends Sprite {
	
	final private Sign self = this;
	
	final static private FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0, 0);

	private String text;
	
	private static BitmapTexture bitmapTexture;
	private static TextureRegion textureRegion;
	final private static String resourcePathName = "gfx/objects/sign.png";
	
	public Sign(int pX, int pY, int zIndex, VertexBufferObjectManager vbo) {
		super(pX, pY, textureRegion, vbo);
		this.setZIndex(zIndex);
	}

	public static void createResources(final AssetManager assetManager, TextureManager textureManager) {
		try {
			bitmapTexture = new BitmapTexture(textureManager, new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return assetManager.open(resourcePathName);
				}
			});
			bitmapTexture.load();
			textureRegion = TextureRegionFactory.extractFromTexture(bitmapTexture);
		} catch(Exception e) {
			Debug.d("Failed to load texture. Resource: " + resourcePathName);
		}
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void displayText() {
		this.detachSelf();
	}
	
	public Runnable onClickListener = new Runnable() {
		@Override
		public void run() {
			self.displayText();
		}
		
	};
	
	public static void loadSignsFromTMXObjectGroup(TMXObjectGroup objectGroup, VertexBufferObjectManager vbo) {
		int zIndex = 1; //default value
		String text = "";
		
		ArrayList<TMXObject> objects = objectGroup.getTMXObjects();
		for(TMXProperty property : objectGroup.getTMXObjectGroupProperties()) {
			String propertyName = property.getName();
			if(propertyName.equals("zIndex")) {
				zIndex = Integer.parseInt(property.getValue());
			}
		}
		
		for(TMXObject object : objects) {
			for(TMXProperty property : object.getTMXObjectProperties()) {
				String propertyName = property.getName();
				if(propertyName.equals("zIndex")) {
					zIndex = Integer.parseInt(property.getValue());
				}
				if(propertyName.equals("text")) {
					text = property.getValue();
				}
			}
			Sign sign = new Sign(object.getX(), object.getY(), zIndex, vbo);
			sign.setText(text);
			PhysicsFactory.createBoxBody(GameActivity.physicsWorld, sign, BodyType.StaticBody, FIXTURE_DEF).setUserData(sign.onClickListener);
			GameActivity.scene.attachChild(sign);
		}
	}
}
