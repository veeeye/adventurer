package com.asms.adventure;

import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.asms.adventure.activities.GameActivity;

import android.opengl.GLES20;

public class MagicParticleSystem extends SpriteParticleSystem {
	
	public MagicParticleSystem(TextureRegion textureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
		super(new PointParticleEmitter(0, GameActivity.CAMERA_HEIGHT), 6, 10, 200, textureRegion, vertexBufferObjectManager);
		addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		addParticleInitializer(new VelocityParticleInitializer<Sprite>(15, 22, -60, -90));
		addParticleInitializer(new AccelerationParticleInitializer<Sprite>(5, 15));
		addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 0.0f, 0.0f));
		addParticleInitializer(new ExpireParticleInitializer<Sprite>(11.5f));

		addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 0.5f, 2.0f));
		addParticleModifier(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
		addParticleModifier(new AlphaParticleModifier<Sprite>(3.5f, 4.5f, 0.0f, 1.0f));
		addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 11.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
		addParticleModifier(new AlphaParticleModifier<Sprite>(4.5f, 11.5f, 1.0f, 0.0f));
	}
}
