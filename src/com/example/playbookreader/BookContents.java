package com.example.playbookreader;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;

public class BookContents {
	JSONObject raw = null;
	JSONArray chapters;
	 File updateDir = null;
	
	public BookContents(JSONObject raw) {
		// TODO Auto-generated constructor stub
		this.raw = raw;
		chapters = raw.optJSONArray("chapters");
	}
	
	public BookContents(JSONObject raw, File updateDir) {
		this.raw = raw;
		this.updateDir  = updateDir;
		chapters = raw.optJSONArray("chapters");
	}

	public int getChapterCount(){
		return chapters.length();
	}
	
	public String getChapterFile(int position) {
		JSONObject chapter = chapters.optJSONObject(position);
		
		
		if (updateDir != null) {
			return Uri.fromFile(new File(updateDir, chapter.optString("file"))).toString();
		}
		
		return "file:///android_asset/book/" + chapter.optString("file");
	}
	
	public String getTitle() {
		return raw.optString("title");
	}

}
