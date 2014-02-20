package com.example.playbookreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class NoteFragment extends Fragment implements
		DatabaseHelper.NoteListener {

	public static final String KEY_POSITION = "position";
	private EditText editor = null;
	private boolean isDeleted = false;

	static NoteFragment newInstance(int position) {
		NoteFragment frag = new NoteFragment();

		Bundle args = new Bundle();
		args.putInt(KEY_POSITION, position);
		frag.setArguments(args);

		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		setHasOptionsMenu(true);
		setRetainInstance(true);
		
		View result = inflater.inflate(R.layout.editor, container, false);

		int position = getArguments().getInt(KEY_POSITION, -1);
		editor = (EditText) result.findViewById(R.id.editor);

		DatabaseHelper.getInstance(getActivity()).getNoteAsync(position, this);

		return result;
	}
	
	public interface OnEditNoteListener{
		void closeNote();
		void sendNotes(String str);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (!isDeleted) {
			int position = getArguments().getInt(KEY_POSITION, -1);
			DatabaseHelper.getInstance(getActivity()).saveNoteAsync(position,
					editor.getText().toString());
		}
	}

	@Override
	public void setNote(String note) {
		editor.setText(note);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.note, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (! (getActivity() instanceof OnEditNoteListener)) {
			throw new ClassCastException("The activity must implement interface OnEditNoteListener");
		}
		
		switch (item.getItemId()) {
		case R.id.delete:
			int position = getArguments().getInt(KEY_POSITION, -1);
			
			isDeleted = true;
			DatabaseHelper.getInstance(getActivity()).deleteNoteAsync(position);
			((OnEditNoteListener)getActivity()).closeNote();
			return true;
		
		case R.id.share:
			((OnEditNoteListener)getActivity()).sendNotes(editor.getText().toString());
			return true;

		}
		
		
		return super.onOptionsItemSelected(item);
	}
}
