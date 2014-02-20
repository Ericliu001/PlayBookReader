package com.example.playbookreader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "empublite.db";
	private static final int SCHEMA_VERSION = 1;
	private static DatabaseHelper singleton = null;
	private Context mContext = null;

	synchronized static DatabaseHelper getInstance(Context context) {
		if (singleton == null) {
			singleton = new DatabaseHelper(context.getApplicationContext());
		}
		return singleton;
	}

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);

		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		TableNotes.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		TableNotes.onUpgrade(db);
	}

	public void getNoteAsync(int position, NoteListener listener) {
		new GetNoteTask(listener).execute(position);
	}

	public void saveNoteAsync(int position, String note) {
		new SaveNoteTask(position, note).execute();
	}
	
	public void deleteNoteAsync(int position){
		new DeleteNoteTask().execute(position);
	}

	interface NoteListener {
		void setNote(String note);
	}

	private class GetNoteTask extends AsyncTask<Integer, Void, String> {
		private NoteListener listener = null;

		public GetNoteTask(NoteListener listener) {
			this.listener = listener;
		}

		@Override
		protected String doInBackground(Integer... params) {
			String[] args = { params[0].toString() };
			String selectSql = "select " + TableNotes.COLUMN_PROSE + " from "
					+ TableNotes.TABLE_NOTES + " where "
					+ TableNotes.COLUMN_POSITION + " =  ?";
			Cursor cursor = getReadableDatabase().rawQuery(selectSql, args);

			cursor.moveToFirst();
			if (cursor.isAfterLast()) {
				cursor.close();
				return null;
			}
			String result = cursor.getString(cursor
					.getColumnIndexOrThrow(TableNotes.COLUMN_PROSE));
			return result;
		}

		@Override
		protected void onPostExecute(String prose) {
			listener.setNote(prose);
			Log.i("eric", "bloody: " + prose);
		}

	}

	private class SaveNoteTask extends AsyncTask<Void, Void, Void> {

		private int position;
		private String note = null;

		public SaveNoteTask(int position, String note) {
			this.position = position;
			this.note = note;
		}

		@Override
		protected Void doInBackground(Void... params) {
			String[] args = { String.valueOf(position), note };

			String insertSql = "insert or replace into "
					+ TableNotes.TABLE_NOTES + "(" + TableNotes.COLUMN_POSITION
					+ " , " + TableNotes.COLUMN_PROSE + ")" + " values (?,?)";
			getWritableDatabase().execSQL(insertSql, args);

			return null;
		}

	}

	private class DeleteNoteTask extends AsyncTask<Integer, Void, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			String[] args = { params[0].toString() };

			String deleteSql = "delete from " + TableNotes.TABLE_NOTES
					+ " where " + TableNotes.COLUMN_POSITION + " = ?";
			
			getWritableDatabase().execSQL(deleteSql, args);
			return null;
		}
	}
}
