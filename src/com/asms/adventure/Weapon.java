package com.asms.adventure;

import org.andengine.entity.shape.IAreaShape;

import com.asms.adventure.enums.Direction;
import com.badlogic.gdx.physics.box2d.Body;

public interface Weapon extends IAreaShape {
	
	public void attack();
	public void registerBody(Body body);
	public void rotate(Direction direction);
}
