package com.asms.adventure.activities;

import com.asms.adventure.R;
import com.asms.adventure.R.id;
import com.asms.adventure.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_menu);
		
		TextView tv = (TextView) findViewById(R.id.gameTitle);
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/8bit16.ttf");
		tv.setTypeface(tf);
		
		final Button playButton = (Button) findViewById(R.id.playButton);
		final Button settingsButton = (Button) findViewById(R.id.settingsButton);
		final Button exitButton = (Button) findViewById(R.id.exitButton);
		
		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent settingsIntent = new Intent(MainMenuActivity.this, GameLauncherActivity.class);
				startActivity(settingsIntent);
				
			}
		});
		
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent settingsIntent = new Intent(MainMenuActivity.this, SettingsActivity.class);
				startActivity(settingsIntent);
				
			}
		});
		
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}

