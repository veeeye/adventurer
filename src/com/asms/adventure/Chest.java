package com.asms.adventure;

import java.util.ArrayList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperty;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.asms.adventure.activities.GameActivity;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Chest extends AnimatedSprite {
	
	final Chest self = this;
	
	private static BitmapTextureAtlas bitmapTextureAtlas;
	private static TiledTextureRegion textureRegion;
	private static GameActivity pThis;
	
	private boolean isAnimating = false;
	private boolean isOpen = false;
	
	final static public FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0, 0);
	
	public Runnable onClickListener = new Runnable() {
		Chest chest = self;
		@Override
		public void run() {
			chest.toggle();
		}
	};
	
	public static void createResources(GameActivity p) {
		pThis = p;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/objects/");
		bitmapTextureAtlas = new BitmapTextureAtlas(pThis.getTextureManager(), 104, 56, TextureOptions.DEFAULT);
		textureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bitmapTextureAtlas, pThis, "wooden-chest.png", 0, 0, 4, 2);
		bitmapTextureAtlas.load();
	}
	
	public Chest(int pX, int pY, int pZ) {
		super(pX, pY, 26, 28, textureRegion.deepCopy(), pThis.getVertexBufferObjectManager());
		this.setZIndex(pZ);
	}
	
	public void toggle() {
		if(!isAnimating) {
			if(isOpen) {
				this.close();
			} else {
				this.open();
			}
		}
	}
	
	public void open() {
		if(!isAnimating && !isOpen)
		this.animate(new long[] {50, 50, 50, 50}, new int[] {0, 1, 2, 3}, false, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
					int pInitialLoopCount) {
				isAnimating = true;
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
					int pOldFrameIndex, int pNewFrameIndex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
					int pRemainingLoopCount, int pInitialLoopCount) {
				
			}

			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				isAnimating = false;
				isOpen = true;
			}});
	}
	
	public void close() {
		if(!isAnimating && isOpen)
		this.animate(new long[] {50, 50, 50, 50}, new int[] {3, 2, 1, 0} , false, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
					int pInitialLoopCount) {
				isAnimating = true;
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
					int pOldFrameIndex, int pNewFrameIndex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
					int pRemainingLoopCount, int pInitialLoopCount) {
				
				
			}

			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				isAnimating = false;
				isOpen = false;
			}});
	}

	public static void loadChestsFromTMXObjectGroup(TMXObjectGroup objectGroup) {
		int zIndex = 1; //default value
		
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
			}
			Chest chest = new Chest(object.getX(), object.getY(), zIndex);
			PhysicsFactory.createBoxBody(GameActivity.physicsWorld, chest, BodyType.StaticBody, FIXTURE_DEF).setUserData(chest.onClickListener);
			GameActivity.scene.attachChild(chest);
		}
	}
}
