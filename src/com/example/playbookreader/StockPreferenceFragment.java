package com.example.playbookreader;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class StockPreferenceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		int res = getActivity().getResources().getIdentifier(
				getArguments().getString("resource"), "xml",
				getActivity().getPackageName());
		addPreferencesFromResource(R.xml.pref_display);
	}

}
