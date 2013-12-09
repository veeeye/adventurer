package com.asms.adventure.weapons;

import org.andengine.entity.primitive.Vector2;

import com.asms.adventure.Projectile;

public class Arrow extends Projectile {
	
	public Arrow(Vector2 position, float angle, float maxDistance) {
		this.position = position;
		this.angle = angle;
		this.maxDistance = maxDistance;
	}
}
