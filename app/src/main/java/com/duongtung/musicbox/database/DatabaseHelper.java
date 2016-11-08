package com.duongtung.musicbox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.duongtung.musicbox.utils.DatabaseStatic;

import java.util.Map;

public class DatabaseHelper {
	private DatabaseHandler databaseHelper;
	private Context context;
	private DatabaseHandler db;
	private static SQLiteDatabase data;
	private static DatabaseHelper instance;

	public static DatabaseHelper getInstance(Context context){
		if (instance==null){
			instance = new DatabaseHelper(context);
		}
		return instance;
	}

	public DatabaseHelper(Context context) {
		this.context = context;
		openData();

	}

	public DatabaseHelper openData() {
		db = new DatabaseHandler(context);
		data = db.getWritableDatabase();
		return this;
	}

	public synchronized void close() {
		db.close();
	}

	public static void insertFavorite(ContentValues favorite){
		data.insert(DatabaseStatic.FAVORITE,null,favorite);
	}

//	public long Insert(EqualizerObject data) {
//		ContentValues content = new ContentValues();
//		content.put(DatabaseStatic.name, data.getName());
//		content.put(DatabaseStatic.type, data.getType());
//		content.put(DatabaseStatic.data, data.getData());
//		return this.data.insert(DatabaseStatic.tableName, null, content);
//
//	}
//
//	public EqualizerObject getEqualizerObject(int id) {
//		EqualizerObject databand = new EqualizerObject();
//		Cursor cur = data.query(DatabaseStatic.tableName, new String[] {
//				DatabaseStatic.id, DatabaseStatic.name, DatabaseStatic.type,
//				DatabaseStatic.data }, DatabaseStatic.id + "='" + id + "'",
//				null, null, null, null);
//		if (cur.moveToFirst()) {
//			do {
//				databand.setName(cur.getString(1));
//				databand.setId(cur.getInt(0));
//				databand.setType(cur.getInt(2));
//				databand.setData(cur.getString(3));
//			} while (cur.moveToNext());
//		}
//		return databand;
//	}
//
//	public EqualizerObject getEqualizerObject(String name) {
//		EqualizerObject databand = new EqualizerObject();
//		Cursor cur = data.query(DatabaseStatic.tableName, new String[] {
//				DatabaseStatic.id, DatabaseStatic.name, DatabaseStatic.type,
//				DatabaseStatic.data }, DatabaseStatic.name + "='" + name + "'",
//				null, null, null, null);
//		if (cur.moveToFirst()) {
//			do {
//				databand.setName(cur.getString(1));
//				databand.setId(cur.getInt(0));
//				databand.setType(cur.getInt(2));
//				databand.setData(cur.getString(3));
//			} while (cur.moveToNext());
//		}
//		return databand;
//	}
//
//	public ArrayList<EqualizerObject> getListEqualizerObject(int type) {
//		ArrayList<EqualizerObject> list = new ArrayList<EqualizerObject>();
//
//		Cursor cur = data.query(DatabaseStatic.tableName, new String[] {
//				DatabaseStatic.id, DatabaseStatic.name, DatabaseStatic.type,
//				DatabaseStatic.data }, DatabaseStatic.type + "='" + type + "'",
//				null, null, null, null);
//		if (cur.moveToFirst()) {
//			do {
//				EqualizerObject databand = new EqualizerObject();
//				databand.setName(cur.getString(1));
//				databand.setId(cur.getInt(0));
//				databand.setType(cur.getInt(2));
//				databand.setData(cur.getString(3));
//				list.add(databand);
//			} while (cur.moveToNext());
//		}
//		return list;
//	}
}
