package com.asms.adventure;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.asms.adventure.activities.GameActivity;
import com.asms.adventure.enums.Direction;
import com.badlogic.gdx.math.Vector2;

public class Character extends AnimatedSprite {
	
	private static MoveModifier movementModifier;
	private Direction direction = Direction.UP;
	
	private int tileWidth = 32;
	private float stepRatio = 0.25f;
	private float stepDuration = 0.6f * stepRatio;
	private long[] stepDurations = {(long) (600 * stepRatio), (long) (600 * stepRatio), (long) (600 * stepRatio)};
	private Weapon equippedWeapon = null;
	
	protected Character(final ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super((GameActivity.CAMERA_WIDTH - pTiledTextureRegion.getWidth()) / 2,
			  (GameActivity.CAMERA_HEIGHT - pTiledTextureRegion.getHeight()) / 2,
			  pTiledTextureRegion,
			  pVertexBufferObjectManager);
		this.setZIndex(1);
		
	}
	
	public Vector2 getDirectionNormalVector() {
		float x = 0f;
		float y = 0f;
		if(direction == Direction.UP) {
			y = -1f;
		} else if(direction == Direction.DOWN) {
			y = 1f;
		} else if(direction == Direction.LEFT) {
			x = -1f;
		} else if(direction == Direction.RIGHT) {
			x = 1f;
		} else {
			throw new Error("No direction.");
		}
		return new Vector2(x, y);
	}
	
	public void equipWeapon(SwingWeapon weapon) {
		this.equippedWeapon  = weapon;
		equippedWeapon.setRotationCenter(0, equippedWeapon.getHeight());
		equippedWeapon.rotate(this.direction);
		this.attachChild(weapon);
	}
	
	
	
	public void moveRight() {
		if(direction != Direction.RIGHT || isAnimationRunning() == false) {
			direction = Direction.RIGHT;
			equippedWeapon.rotate(direction);
			animate(stepDurations, 3, 5, true);
		}
	}
	
	public void moveLeft() {
		if(direction != Direction.LEFT || isAnimationRunning() == false) {
			direction = Direction.LEFT;
			equippedWeapon.rotate(direction);
			animate(stepDurations, 9, 11, true);
		}
	}
	
	public void moveDown() {
		if(direction != Direction.DOWN || isAnimationRunning() == false) {
			direction = Direction.DOWN;
			equippedWeapon.rotate(direction);
			animate(stepDurations, 6, 8, true);
		}
	}
	
	public void moveUp() {
		if(direction != Direction.UP || isAnimationRunning() == false) {
			direction = Direction.UP;
			equippedWeapon.rotate(direction);
			animate(stepDurations, 0, 2, true);
		}
	}
	
	public void stopMovement() {
		stopAnimation();
	}

	public String[] toStringArray() {
		String[] string = {};
		return string;
	}

}
