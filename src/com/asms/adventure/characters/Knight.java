package com.asms.adventure.characters;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.asms.adventure.Character;
import com.badlogic.gdx.math.Vector2;

public class Knight extends Character {

	public Knight(ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	
}
