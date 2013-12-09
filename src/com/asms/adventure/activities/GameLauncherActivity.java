package com.asms.adventure.activities;

import java.util.ArrayList;
import com.asms.adventure.CharacterXMLManager;
import com.asms.adventure.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class GameLauncherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_launcher);
		
		final Button newCharacterButton = (Button) findViewById(R.id.newCharacterButton);
		final ListView characterListView = (ListView) findViewById(R.id.characterList);
		
		ArrayList<String[]> characterList = CharacterXMLManager.getCharacterList(getApplicationContext());
		
		ListAdapter characterListAdapter = new CharacterListAdapter(this, R.layout.character_list_item, characterList);
		characterListView.setAdapter(characterListAdapter);
		
		characterListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String characterId = (String) view.getTag();
				startGame(characterId);
			}
		
		});
		
		characterListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
		
		newCharacterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent settingsIntent = new Intent(GameLauncherActivity.this, CreateNewCharacterActivity.class);
				startActivity(settingsIntent);
			}
		});
		
		
	}
	
	public void startGame(String characterId) {
		Intent gameIntent = new Intent(GameLauncherActivity.this, GameActivity.class);
		gameIntent.putExtra("id", characterId);
		startActivity(gameIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_character, menu);
		return true;
	}

}
