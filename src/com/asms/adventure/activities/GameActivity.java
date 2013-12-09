package com.asms.adventure.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine.EngineLock;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
//import org.andengine.examples.TMXTiledMapExample;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.Constants;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseSineInOut;

import com.asms.adventure.Chest;
import com.asms.adventure.GameHUD;
import com.asms.adventure.Map;
import com.asms.adventure.Sign;
import com.asms.adventure.SwingWeapon;
import com.asms.adventure.characters.Knight;
import com.asms.adventure.characters.Mage;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.os.Bundle;
import android.widget.Toast;

public class GameActivity extends SimpleBaseGameActivity {
	
	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 320;
	
	private Font mFont;
	
	//Map Loader
	private Map map;

	private TMXTiledMap mTMXTiledMap;
	
	// Scene, Camera
	public static Scene scene;
	private BoundCamera mBoundChaseCamera;
	
	// Sound & Music
	private Sound slashSound;
	private Music mMusic;
	
	// Physics (Collisions)
	public static PhysicsWorld physicsWorld;
	final private FixtureDef genericFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0);
	final private FixtureDef playerFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 2f, false);
	final private FixtureDef weaponFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0, true);
	
	//HUD & Controls
	private GameHUD gameHUD;
	private BitmapTextureAtlas mOnScreenControlTexture;
	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITextureRegion mOnScreenControlKnobTextureRegion;
	private DigitalOnScreenControl mDigitalOnScreenControl;

	private BitmapTextureAtlas mButtonRedTexture;
	private BitmapTextureAtlas mButtonBlueTexture;
	private TextureRegion mButtonBlueTextureRegion;
	private TextureRegion mButtonRedTextureRegion;

	// Character Classes
	private BitmapTextureAtlas mageBitmapTextureAtlas;
	private BitmapTextureAtlas fighterBitmapTextureAtlas;
	private BitmapTextureAtlas healerBitmapTextureAtlas;
	private BitmapTextureAtlas thiefBitmapTextureAtlas;
	
	private TiledTextureRegion fighterTextureRegion;
	private TiledTextureRegion mageTextureRegion;
	private TiledTextureRegion thiefTextureRegion;
	private TiledTextureRegion healerTextureRegion;
	
	
	// Items
	private Chest[] chest;

	private BitmapTexture testBitmapTexture;
	private TextureRegion testItemTextureRegion;

	//Testing
	private SwingWeapon sword;
	private TextureRegion magicalTextureRegion;
	private String characterId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String id = (String) getIntent().getExtras().get("id");
		this.characterId = id;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mBoundChaseCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mBoundChaseCamera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		
		//Create Object Resources
		Chest.createResources(this);
		Sign.createResources(getAssets(), getTextureManager());
		
		// text
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 16);
		this.mFont.load();
		
		//Controls
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/controls/");

		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.DEFAULT);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);
		this.mOnScreenControlTexture.load();

		this.mButtonRedTexture = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.DEFAULT);
		this.mButtonBlueTexture = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.DEFAULT);

		this.mButtonRedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mButtonRedTexture, this, "button-red.png", 0, 0);
		this.mButtonBlueTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mButtonBlueTexture, this, "button-blue.png", 0, 0);

		this.mButtonRedTexture.load();
		this.mButtonBlueTexture.load();

		//Player Sprite
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/characters/");
		
		healerBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 87, 180, TextureOptions.DEFAULT);
		mageBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 87, 180, TextureOptions.DEFAULT);
		
		fighterBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 54, 100, TextureOptions.DEFAULT);
		thiefBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 54, 100, TextureOptions.DEFAULT);
		
		fighterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fighterBitmapTextureAtlas, this, "knight.png", 0, 0, 3, 4);
		thiefTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(thiefBitmapTextureAtlas, this, "thief.png", 0, 0, 3, 4);
		
		mageTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mageBitmapTextureAtlas, this, "mage.png", 0, 0, 3, 4);
		healerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(healerBitmapTextureAtlas, this, "healer.png", 0, 0, 3, 4);
		
		fighterBitmapTextureAtlas.load();
		thiefBitmapTextureAtlas.load();
		mageBitmapTextureAtlas.load();
		healerBitmapTextureAtlas.load();
	

		//Items
		try {
			testBitmapTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/objects/sword.png");
				}
			});
			testBitmapTexture.load();
			testItemTextureRegion = TextureRegionFactory.extractFromTexture(testBitmapTexture);
		} catch(Exception e) {
			Debug.d("testTexture was not loaded.");
		}

		
		// Sound
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.slashSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "slash.ogg");
			this.slashSound.setVolume(0.2f);
		} catch (final IOException e) {
			Debug.e(e);
		}
		
		// Music
		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "AmbientKingdom.mp3");
			this.mMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		VertexBufferObjectManager vbo = this.getVertexBufferObjectManager();
		this.mEngine.registerUpdateHandler(new FPSLogger());

		scene = new Scene();
		physicsWorld = new PhysicsWorld(new Vector2(), false);
		scene.registerUpdateHandler(physicsWorld);
		
		mMusic.play();
		
		map = new Map(getApplicationContext(), scene, physicsWorld, vbo, mEngine.getTextureManager());
		int mapID = 0;
		map.load(mapID);
		
		
		
		gameHUD = new GameHUD(getVertexBufferObjectManager());
		mBoundChaseCamera.setHUD(gameHUD);

		/* Make the camera not exceed the bounds of the TMXEntity. */
		this.mBoundChaseCamera.setBounds(0, 0, map.getHeight(), map.getWidth());
		this.mBoundChaseCamera.setBoundsEnabled(true);
		
		final Mage player = new Mage(mageTextureRegion, this.getVertexBufferObjectManager());
		
		
		this.mBoundChaseCamera.setChaseEntity(player);
		final Body playerBody = PhysicsFactory.createCircleBody(physicsWorld, player, BodyType.KinematicBody, playerFixtureDef);
		playerBody.setUserData("player");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(player, playerBody, true, true));
		
		final Text leftText = new Text(190, 50, this.mFont, "Hello Player!\nLet me know when you need help!", new TextOptions(HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
		
		sword = new SwingWeapon(player.getWidth() / 2, player.getHeight() * 3 / 4 - testItemTextureRegion.getHeight(), testItemTextureRegion, getVertexBufferObjectManager());
		player.equipWeapon(sword);

		// Add enemy
		final TiledSprite enemy = new TiledSprite(100, 100, 40, 40, this.fighterTextureRegion, this.getVertexBufferObjectManager())
		{
		@Override
	     protected void onManagedUpdate(float pSecondsElapsed)
	     {
	         if (sword.collidesWith(this))
	         {                                                              
	             this.setVisible(false);
	         }
	     };
		};
		final Body enemyBody = PhysicsFactory.createCircleBody(physicsWorld, enemy, BodyType.DynamicBody, playerFixtureDef);
		playerBody.setUserData("enemy");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(enemy, enemyBody, true, true));
		
		// Add healer
		final TiledSprite healer = new TiledSprite(200, 200, this.healerTextureRegion, vbo)
		{
			@Override
		     protected void onManagedUpdate(float pSecondsElapsed)
		     {
		         if (player.collidesWith(this))
		         {                                                              
		        	 leftText.setVisible(true);
		         }
		         else
		        	 leftText.setVisible(false);
		     };
		};
		final Body healerBody = PhysicsFactory.createCircleBody(physicsWorld, healer, BodyType.KinematicBody, playerFixtureDef);
		playerBody.setUserData("healer");
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(healer, healerBody, true, true));
		
		healer.setCurrentTileIndex(6);
		enemy.setCurrentTileIndex(4);
		
		scene.attachChild(player);
		scene.attachChild(healer);
		scene.attachChild(enemy);
		scene.attachChild(leftText);
		
		//Controls
		this.mDigitalOnScreenControl = new DigitalOnScreenControl(0, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mBoundChaseCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, this.getVertexBufferObjectManager(), new IOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				playerBody.setLinearVelocity(pValueX*2f, pValueY*2f);
				if(pValueX == 1) { //right
					player.moveRight();
				} else if(pValueX == -1) { //left
					player.moveLeft();
				} else if(pValueY == 1) { //down
					player.moveDown();
				} else if(pValueY == -1) { //up
					player.moveUp();
				} else {
					player.stopMovement();
				}
			}
		});
		this.mDigitalOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mDigitalOnScreenControl.getControlBase().setAlpha(0.5f);
		this.mDigitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
		//this.mDigitalOnScreenControl.getControlBase().setScale(1.25f);
		//this.mDigitalOnScreenControl.getControlKnob().setScale(1.25f);
		this.mDigitalOnScreenControl.refreshControlKnobPosition();

		Sprite blueButton = new Sprite((float) (CAMERA_WIDTH-128), (float) (CAMERA_HEIGHT-64), mButtonBlueTextureRegion, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				// THIS METHOD IS CALLED BY ANY ITEM THAT HAS A TOUCH LISTENER PUT ON IT

				if(pSceneTouchEvent.isActionDown()) {
					sword.attack();
					slashSound.play();
				}
				return false;
			}
		};
		Sprite redButton = new Sprite((float) (CAMERA_WIDTH-64), (float) (CAMERA_HEIGHT-128), mButtonRedTextureRegion, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				// THIS METHOD IS CALLED BY ANY ITEM THAT HAS A TOUCH LISTENER PUT ON IT

				if(pSceneTouchEvent.isActionDown()) {
					
					RayCastCallback pRayCastCallback = new RayCastCallback() {

						@Override
						public float reportRayFixture(Fixture fixture, Vector2 point,
								Vector2 normal, float fraction) {
							Vector2 realPoint = new Vector2(point.x * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
									point.y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
							Body body = fixture.getBody();
							if(body != null) {
								Object userData = body.getUserData();
								if(userData instanceof Runnable) {
									((Runnable) userData).run();
								}
							}
							return -1;
						}
					};
					Vector2 playerPosition = new Vector2((player.getX()+player.getWidth()/2), (player.getY()+player.getHeight()/2));
					Vector2 rayEndPoint = player.getDirectionNormalVector().mul(32f).add(playerPosition);
					playerPosition.div(PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
					rayEndPoint.div(PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);

					physicsWorld.rayCast(pRayCastCallback, playerPosition, rayEndPoint);
				}
				return false;
			}
		};

		
		this.mDigitalOnScreenControl.registerTouchArea(blueButton);
		this.mDigitalOnScreenControl.registerTouchArea(redButton);

		this.mDigitalOnScreenControl.attachChild(redButton);
		this.mDigitalOnScreenControl.attachChild(blueButton);

		scene.setChildScene(this.mDigitalOnScreenControl);


		scene.sortChildren();
		return scene;
	}


	@Override
	public synchronized void onResumeGame() {
		if(this.getEngine() != null) { //Prevents NullPointerException onResume
			super.onResumeGame();
		}
	}
}
