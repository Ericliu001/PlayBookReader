package com.example.playbookreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractContentFragment extends WebViewFragment {
	abstract String getPage();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View result =  super.onCreateView(inflater, container, savedInstanceState);
	
		getWebView().loadUrl(getPage());
		
		return result;
	}

}
