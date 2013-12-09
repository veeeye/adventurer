package com.asms.adventure.activities;

import java.util.ArrayList;
import java.util.List;

import org.andengine.util.debug.Debug;

import com.asms.adventure.R;
import com.asms.adventure.enums.CharacterType;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CharacterListAdapter extends ArrayAdapter {

	private List items;

	public CharacterListAdapter(Context context, int resource, ArrayList<String[]> items) {
		super(context, resource, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.character_list_item, null);

		}

		String[] item = (String[]) this.items.get(position);
		
		final String characterId = item[0];
		CharacterType characterType = CharacterType.valueOf(item[1]);
		String characterName = item[2];

		if (item != null) {

			ImageView characterImageView = (ImageView) v.findViewById(R.id.characterImage);
			TextView characterNameView = (TextView) v.findViewById(R.id.characterName);


			if (characterImageView != null) {
				int characterDrawableResource = 0;
				if(characterType == CharacterType.KNIGHT) {
					characterDrawableResource = R.drawable.knight;
				} else if(characterType == CharacterType.THIEF) {
					characterDrawableResource = R.drawable.thief;
				} else if(characterType == CharacterType.MAGE) {
					characterDrawableResource = R.drawable.mage;
				} else if(characterType == CharacterType.HEALER) {
					characterDrawableResource = R.drawable.healer;
				}
				Drawable drawable = getContext().getResources().getDrawable(characterDrawableResource);
				characterImageView.setImageDrawable(drawable);

			}
			if (characterNameView != null) {
				characterNameView.setText(characterName);
			}
			
			v.setTag((Object) characterId);
			
		}

		return v;
	}

}
