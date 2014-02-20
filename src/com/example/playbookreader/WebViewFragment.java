package com.example.playbookreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WebViewFragment extends Fragment {
	
	private WebView mWebView;
	private boolean mIsWebViewAvailable;
	
	public WebViewFragment() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	if (mWebView != null) {
		mWebView.destroy();
	}
	
	mWebView = new WebView(getActivity());
	mIsWebViewAvailable = true;
		
		return mWebView;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mWebView.onPause();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mWebView.onResume();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub

		mIsWebViewAvailable = false;
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (mWebView != null) {
			mWebView.destroy();
			mWebView = null;
		}
		
		super.onDestroy();
	}
	
	public WebView getWebView() {
		return mIsWebViewAvailable? mWebView : null;
	}

}
