package com.asms.adventure;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.EntityModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBackOut;
import org.andengine.util.modifier.ease.EaseCircularInOut;
import org.andengine.util.modifier.ease.IEaseFunction;

import com.asms.adventure.enums.Direction;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;

public class SwingWeapon extends Sprite implements Weapon {

	final private float[] rotationAngles = new float[4];
	final Vector2 nullVector = new Vector2();
	private Body physicsBody;
	private Direction direction;
	private boolean isAnimating = false;

	public SwingWeapon(float pX, float pY, TextureRegion textureRegion, VertexBufferObjectManager vbo) {
		super(pX, pY, textureRegion, vbo);
		rotationAngles[Direction.UP.ordinal()] = -22.5f;// * (float)(Math.PI / 180);
		rotationAngles[Direction.LEFT.ordinal()] = 0;// * (float)(Math.PI / 180);
		rotationAngles[Direction.RIGHT.ordinal()] = -90f;
		rotationAngles[Direction.DOWN.ordinal()] = -67.5f;// * (float)(Math.PI / 180);

	}
	@Override
	public void attack() {
		animatedRotation();
	}

	public void registerBody(Body body) {
		this.physicsBody = body;
		this.physicsBody.setAngularDamping(5f);
	}

	@Override
	public void rotate(Direction direction) {
		if(this.direction != direction) {
			this.direction = direction;
			this.setRotation(rotationAngles[this.direction.ordinal()]);
		}
	}

	protected void animatedRotation() {
		if(!isAnimating) {
			// Note: animates backwards
			final float duration = 0.25f;
			final float fromRotation = rotationAngles[this.direction.ordinal()];
			float deltaTheta;
			if(this.direction == Direction.UP) {
				deltaTheta =  -180;
			} else if(this.direction == Direction.LEFT) {
				deltaTheta =  -180;
			} else if(this.direction == Direction.DOWN){
				deltaTheta = 180;
			} else if(this.direction == Direction.RIGHT){
				deltaTheta = 180;
			} else {
				deltaTheta = 0;
			}
			final float toRotation = fromRotation + deltaTheta;
			this.registerEntityModifier(new RotationModifier(duration * 0.5f, fromRotation, toRotation, EaseBackIn.getInstance()) {
				@Override
				protected void onModifierStarted(IEntity pItem)
				{
					super.onModifierStarted(pItem);
					isAnimating = true;
				}

				@Override
				protected void onModifierFinished(IEntity pItem)
				{
			
					registerEntityModifier(new RotationModifier(duration, toRotation, fromRotation, EaseBackOut.getInstance()) {
						@Override
						protected void onModifierStarted(IEntity pItem)
						{
							super.onModifierStarted(pItem);
						}

						@Override
						protected void onModifierFinished(IEntity pItem)
						{
							super.onModifierFinished(pItem);
							isAnimating = false;
						}
					});
				}
			});
		}
	}

}
