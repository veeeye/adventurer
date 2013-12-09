package com.asms.adventure;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import android.content.Context;
import android.content.res.AssetManager;

public class Map {
	private static Context context;
	
	private static Scene scene;
	private static PhysicsWorld physicsWorld;
	
	// Managers
	private static VertexBufferObjectManager vbo;
	private static TextureManager textureManager;
	private static TMXTiledMap tiledMap;
	
	// Fixture Definitions
	private FixtureDef obstacleFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0);//, false, (short) 0x0002, (short) 0x0004, (short) - 0x0016);
	private FixtureDef weaponFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0, true);
	private FixtureDef playerFixtureDef;
	private FixtureDef npcFixtureDef;
	private FixtureDef objectFixtureDef;
	
	// Values
	private static int width;
	private static int height;
	
	public Map(Context context, Scene scene, PhysicsWorld physicsWorld, VertexBufferObjectManager vbo, TextureManager textureManager) {
		this.context = context;
		this.scene = scene;
		this.physicsWorld = physicsWorld;
		this.vbo = vbo;
		this.textureManager = textureManager;
	}
	
	public void load(int mapID) {
		try {
			AssetManager assetManager = context.getAssets();
			InputStream xmlFile = assetManager.open("maps/" + mapID + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			Element root = doc.getDocumentElement();
			String name = root.getAttribute("name");
			int tmxId = Integer.parseInt(root.getAttribute("tmxId"));
			
			loadTMXTileMap(tmxId);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadTMXTileMap(int tmxId) {
		//Add Tile Map
		try {
			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), textureManager, TextureOptions.BILINEAR_PREMULTIPLYALPHA, vbo, new ITMXTilePropertiesListener() {
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
					if(pTMXTileProperties.containsTMXProperty("obstacle", "true")) {
						final Rectangle rect = new Rectangle(pTMXTile.getTileX(), pTMXTile.getTileY(), pTMXTile.getTileWidth(), pTMXTile.getTileHeight(), vbo);
						PhysicsFactory.createBoxBody(physicsWorld, rect, BodyType.StaticBody, obstacleFixtureDef).setUserData("obstacle");
						rect.setVisible(false);
						scene.attachChild(rect);
					}
				}
			});

			tiledMap = tmxLoader.loadFromAsset("tmx/" + tmxId + ".tmx");
			/*
			ArrayList<TMXObjectGroup> objectGroups = tiledMap.getTMXObjectGroups();

			for(TMXObjectGroup objectGroup : objectGroups) {
				String objectGroupName = objectGroup.getName();
				if(objectGroupName.equals("chests")) {
					Chest.loadChestsFromTMXObjectGroup(objectGroup);
				} else if(objectGroupName.equals("signs")) {
					Sign.loadSignsFromTMXObjectGroup(objectGroup, vbo);
				}
			}
			*/
			
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}

		final TMXLayer groundLayer = tiledMap.getTMXLayers().get(0);
		groundLayer.setZIndex(0);
		//final TMXLayer treeLayer = this.mTMXTiledMap.getTMXLayers().get(1);
		//treeLayer.setZIndex(2);
		//final TMXLayer structuresLayer = this.mTMXTiledMap.getTMXLayers().get(2);
		//structuresLayer.setZIndex(4);
		scene.attachChild(groundLayer);
		
		height = groundLayer.getHeight();
		width = groundLayer.getWidth();
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	
}
