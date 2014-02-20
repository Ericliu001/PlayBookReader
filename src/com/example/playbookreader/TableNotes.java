package com.example.playbookreader;

import android.database.sqlite.SQLiteDatabase;

public class TableNotes {

	public static final String TABLE_NOTES = "notes";
	public static final String COLUMN_POSITION = "position";
	public static final String COLUMN_PROSE = "prose";
	
	private static final String CREATE_TABLE = "create table "
			+ TABLE_NOTES + " ( "
			+ COLUMN_POSITION + " integer primary key, "
			+ COLUMN_PROSE + " text "
			+ " ); ";
	
	public static void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_TABLE);
	}
	
	public static void onUpgrade(SQLiteDatabase db){
		db.execSQL("drop table is exists " + TABLE_NOTES);
		onCreate(db);
	}
}
