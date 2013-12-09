package com.asms.adventure.activities;

import com.asms.adventure.R;
import com.asms.adventure.R.id;
import com.asms.adventure.R.layout;
import com.asms.adventure.R.menu;
import com.asms.adventure.enums.CharacterType;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CreateNewCharacterActivity extends Activity {
	
	private CharacterType characterType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_character);

		ImageView knightImage = (ImageView) findViewById(R.id.knightImage);
		ImageView thiefImage = (ImageView) findViewById(R.id.thiefImage);
		ImageView mageImage = (ImageView) findViewById(R.id.mageImage);
		ImageView healerImage = (ImageView) findViewById(R.id.healerImage);

		knightImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				characterType = CharacterType.KNIGHT;
				setup();
			}
		});
		thiefImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				characterType = CharacterType.THIEF;
				setup();
			}
		});
		mageImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				characterType = CharacterType.MAGE;
				setup();
			}
		});
		healerImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				characterType = CharacterType.HEALER;
				setup();
			}
		});
	}
	
	public void setup() {
		Intent setupCharacterIntent = new Intent(CreateNewCharacterActivity.this, SetupCharacterActivity.class);
		setupCharacterIntent.setFlags(characterType.ordinal());
		startActivity(setupCharacterIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_character, menu);
		return true;
	}

}
