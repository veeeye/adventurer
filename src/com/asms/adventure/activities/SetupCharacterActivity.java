package com.asms.adventure.activities;

import com.asms.adventure.CharacterXMLManager;
import com.asms.adventure.R;
import com.asms.adventure.R.array;
import com.asms.adventure.R.drawable;
import com.asms.adventure.R.id;
import com.asms.adventure.R.layout;
import com.asms.adventure.R.menu;
import com.asms.adventure.enums.CharacterType;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SetupCharacterActivity extends Activity {
	
	private CharacterType characterType;
	private String characterTypeString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_character);

		characterType = CharacterType.values()[this.getIntent().getFlags()];
		TextView characterDescription = (TextView) findViewById(R.id.characterDescription);
		ImageView characterImage = (ImageView) findViewById(R.id.characterImage);

		int characterInformationStringArrayResource;
		int characterDrawableResource;
		if(characterType == CharacterType.KNIGHT) {
			characterDrawableResource = R.drawable.knight;
			characterInformationStringArrayResource = R.array.character_knight_information;
		} else if(characterType == CharacterType.THIEF) {
			characterDrawableResource = R.drawable.thief;
			characterInformationStringArrayResource = R.array.character_thief_information;
		} else if(characterType == CharacterType.MAGE) {
			characterDrawableResource = R.drawable.mage;
			characterInformationStringArrayResource = R.array.character_mage_information;
		} else if(characterType == CharacterType.HEALER) {
			characterDrawableResource = R.drawable.healer;
			characterInformationStringArrayResource = R.array.character_healer_information;
		} else {
			return;
		}
		characterImage.setImageDrawable(getResources().getDrawable(characterDrawableResource));
		String[] characterInformationStringArray = getResources().getStringArray(characterInformationStringArrayResource);
		characterTypeString = characterInformationStringArray[0];
		setTitle(characterTypeString);
		characterDescription.setText(characterInformationStringArray[1]);

		Button selectCharacterButton = (Button) findViewById(R.id.selectCharacterButton);
		selectCharacterButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setup();
			}
		});
	}

	public void setup() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Choose a name");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String characterName = input.getText().toString();
				CharacterXMLManager.createNewCharacter(getApplicationContext(), characterType, characterName);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_character, menu);
		return true;
	}

}
