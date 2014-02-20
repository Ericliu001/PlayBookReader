package com.example.playbookreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ContentFragment extends Fragment {
	private static final String KEY_FILE = "file";
	private WebView mWebView;
	private boolean mIsWebViewAvailable;
	
	protected static ContentFragment newInstance(String file){
		ContentFragment frag = new ContentFragment();
		
		Bundle args = new Bundle();
		args.putString(KEY_FILE, file);
		frag.setArguments(args);
		
		return frag;
	}
	
	public ContentFragment(){}
	
	
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
		
		if (mWebView != null) {
			mWebView.destroy();
		}
		
		mWebView = new WebView(getActivity());
		mIsWebViewAvailable = true;
		
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		
		mWebView.loadUrl(getPage());
		
		return mWebView;
	}

	private String getPage() {
		// TODO Auto-generated method stub
		return getArguments().getString(KEY_FILE);
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
		super.onDestroyView();
		mIsWebViewAvailable = false;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mWebView != null) {
			mWebView.destroy();
			mWebView = null;
		}
	}
}
