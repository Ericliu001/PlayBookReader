package com.example.playbookreader;


import com.example.playbookreader.NoteFragment.OnEditNoteListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class NoteActivity extends FragmentActivity implements OnEditNoteListener {
	
	public static final String EXTRA_POSITION = "extraposition";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
			int position = getIntent().getIntExtra(EXTRA_POSITION,
					-1);

			if (position >= 0) {
				Fragment frag = NoteFragment.newInstance(position);

				getSupportFragmentManager().beginTransaction()
						.add(android.R.id.content, frag).commit();
			}
		}
	}
	

	
	public void closeNote(){
		finish();
	}
	
	public void sendNotes(String prose){
		Intent i = new Intent(Intent.ACTION_SEND);
		
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_TEXT, prose);
		startActivity(Intent.createChooser(i, "Nothing is important here"));
	}




}
