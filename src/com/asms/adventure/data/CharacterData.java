package com.asms.adventure.data;

import com.asms.adventure.enums.CharacterType;
import com.asms.adventure.enums.Direction;

public class CharacterData {
	public CharacterType type;
	public String name;
	
	public int maxHealth;
	public int maxMana;
	public int maxExperience;
	public int maxRage;
	public int maxSprint;
	
	public int level;
	public int gold;
	
	public float health;
	public float mana;
	public float experience;
	public float rage;
	public float sprint;
	
	public int[] equipment = new int[6]; //weapon, helmet, chest, legs, feet, accessory
	public int[] inventory = new int[32]; // 32 slots
	
	public int map;
	public float[] position;
	public Direction direction;
	
	
}
