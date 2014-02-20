package com.example.playbookreader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ContentsAdapter extends FragmentStatePagerAdapter {
	private BookContents contents = null;

	public ContentsAdapter(FragmentActivity activity, BookContents contents) {
		super(activity.getSupportFragmentManager());
		// TODO Auto-generated constructor stub
		this.contents = contents;
	}

	@Override
	public Fragment getItem(int position) {
//		String path = contents.getChapterFile(position);
//		return SimpleContentFragment.newInstance("file:///android_asset/book/" + path);
		return ContentFragment.newInstance(contents.getChapterFile(position));
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contents.getChapterCount();
	}

}
