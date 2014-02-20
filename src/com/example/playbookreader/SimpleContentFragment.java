package com.example.playbookreader;

import android.app.Fragment;
import android.os.Bundle;

public class SimpleContentFragment extends AbstractContentFragment {
	private static final String KEY_FILE = "file";
	
	protected static SimpleContentFragment newInstance(String file){
		SimpleContentFragment frag = new SimpleContentFragment();
		
		Bundle args = new Bundle();
		args.putString(KEY_FILE, file);
		
		frag.setArguments(args);
		
		return frag;
	}
	
	
	
	@Override
	String getPage() {
		// TODO Auto-generated method stub
		return getArguments().getString(KEY_FILE);
	}

}
